package com.hanger.thread;

import com.hanger.channel.ClientAgentChannel;
import com.hanger.data.MapTable;
import com.hanger.model.SignalModel;
import com.hanger.component.Transfer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author hanger
 * 2020-01-01 18:28
 */
public class ClientAgent implements Runnable {
    //映射表
    private MapTable mapTable;
    //服务代理ip
    private String serverAgentIP;
    //服务代理端口
    private Integer serverAgentPort;
    //服务密码
    private String key;
    //内网要映射的ip
    private String mapperIP;
    //内网要映射的端口
    private Integer mapperPort;
    //心跳间隔时间(毫秒)
    private long heartbeatCycle;
    //服务代理信道
    private ClientAgentChannel clientAgentChannel;


    ClientAgent(MapTable mapTable, String serverAgentIP, Integer serverAgentPort, String key, String mapperIP, Integer mapperPort, long heartbeatCycle) {
        this.mapTable = mapTable;
        this.serverAgentIP = serverAgentIP;
        this.serverAgentPort = serverAgentPort;
        this.key = key;
        this.mapperIP = mapperIP;
        this.mapperPort = mapperPort;
        this.heartbeatCycle = heartbeatCycle;
    }

    @Override
    public void run() {
        //创建服务端代理信道
        this.clientAgentChannel = new ClientAgentChannel(serverAgentIP, serverAgentPort);
        //确认key并获取映射端口
        try {
            clientAgentChannel.applyMapperPort(key, heartbeatCycle);
        } catch (IOException e) {
            System.out.println("确认key并获取映射端口失败");
            e.printStackTrace();
            close();
            return;
        }
        //创建本地端口映射线程并启动
        new Thread(new Mapper()).start();
        //心跳
        while (!clientAgentChannel.isClosed()) {
            //开始心跳
            try {
                clientAgentChannel.write(SignalModel.HEARTBEAT.getDescribe());
                //更改映射表
                mapTable.putMap(this);
            } catch (IOException e) {
                System.out.println("心跳失败");
//                e.printStackTrace();
                close();
                return;
            }

            //开始休眠
            try {
                Thread.sleep(heartbeatCycle);
            } catch (InterruptedException e) {
                System.out.println("心跳休眠失败");
                e.printStackTrace();
            }
        }
    }


    //关闭客户端代理
    public void close() {
        clientAgentChannel.close();
    }


    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "ClientAgent{" +
                "serverAgentIP=" + serverAgentIP +
                ", serverAgentPort=" + serverAgentPort +
                ", key='" + key + '\'' +
                ", mapperIP='" + mapperIP + '\'' +
                ", mapperPort=" + mapperPort +
                "}\n";
    }



    public class Mapper implements Runnable {
        Mapper() {
        }

        @Override
        public void run() {
            while (!clientAgentChannel.isClosed()) {
                if (clientAgentChannel.read().equals(SignalModel.NEW_CONNECTION.getDescribe())) {
                    //为了减少反应时间收到通知不回复，直接获取数据连接和内网要映射的端口的连接
                    Socket source = clientAgentChannel.getSource();
                    Socket dest = getDestSocket();
                    if (dest != null) {
                        new Thread(new Transfer(source, dest, openInputStream(source), openOutputStream(dest))).start();
                        new Thread(new Transfer(dest, source, openInputStream(dest), openOutputStream(source))).start();
                    }
                }
            }
        }

        //获取内网要映射的端口的连接
        Socket getDestSocket() {
            Socket destSocket = null;
            try {
                destSocket = new Socket(mapperIP, mapperPort);
            } catch (IOException e) {
                System.out.println("获取内网要映射的端口的连接失败");
                e.printStackTrace();
            }
            //不管有没有成功都返回
            return destSocket;
        }

        //打开本地映射端口的输入流
        InputStream openInputStream(Socket socket) {
            InputStream inputStream = null;
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                System.out.println("输入流打开失败");
                e.printStackTrace();
            }
            //不管是否成功都要返回
            return inputStream;
        }

        //打开本地映射端口的输出流
        OutputStream openOutputStream(Socket socket) {
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                System.out.println("输入流打开失败");
                e.printStackTrace();
            }
            //不管是否成功都要返回
            return outputStream;
        }
    }

}
