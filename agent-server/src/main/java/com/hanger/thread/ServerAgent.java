package com.hanger.thread;

import com.hanger.channel.ServerAgentChannel;
import com.hanger.component.Transfer;
import com.hanger.data.MapTable;
import com.hanger.model.SignalModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author hanger
 * 2020-01-28 23:14
 */
public class ServerAgent implements Runnable {
    //映射表
    private MapTable mapTable;
    //服务代理端口
    private Integer serverAgentPort;
    //映射端口
    private Integer mapperPort;
    //服务密码
    private String key;
    //服务代理信道
    private ServerAgentChannel serverAgentChannel;
    //映射服务连接
    private ServerSocket mapperSocket;


    ServerAgent(MapTable mapTable, Integer serverAgentPort, Integer mapperPort, String key) {
        this.mapTable = mapTable;
        this.serverAgentPort = serverAgentPort;
        this.mapperPort = mapperPort;
        this.key = key;
    }


    @Override
    public void run() {
        //创建服务端代理信道
        createServerAgentChannel();
        if (serverAgentChannel != null) {
            //等待客户端代理连接
            long heartbeatCycle = serverAgentChannel.connect(key, mapperPort);
            if (heartbeatCycle != -1) {
                //创建映射服务连接并启动线程
                try {
                    this.mapperSocket = new ServerSocket(mapperPort);
                } catch (IOException e) {
                    System.out.println("初始化映射服务连接失败！");
                    e.printStackTrace();
                    return;
                }
                Mapper mapper = new Mapper();
                new Thread(mapper).start();
                //监听心跳
                while (!serverAgentChannel.isClosed()) {
                    //读取心跳数据
                    String read = serverAgentChannel.read();
                    if (read == null) {
                        System.out.println("客户端心跳失败");
                        break;
                    } else if (read.equals(SignalModel.HEARTBEAT.getDescribe())) {
                        mapTable.putMap(this);
                    } else {
                        //收到的数据与心跳数据不同
                        System.out.println("心跳数据异常");
                    }

                    //开始休眠
                    try {
                        //比客户端提前醒3秒
                        Thread.sleep(heartbeatCycle - 3000);
                    } catch (InterruptedException e) {
                        System.out.println("心跳休眠失败");
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void createServerAgentChannel() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverAgentPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (serverSocket != null) {
            this.serverAgentChannel = new ServerAgentChannel(serverSocket);
        }
    }


    //获取服务代理端口
    public Integer getServerAgentPort() {
        return serverAgentPort;
    }


    //获取映射端口
    public Integer getMapperPort() {
        return mapperPort;
    }


    //关闭服务代理及其相关进程
    public void close() {
        //关闭映射服务连接
        if (!mapperSocket.isClosed()) {
            try {
                //关闭映射信道的连接
                mapperSocket.close();
            } catch (IOException e) {
                System.out.println("映射信道的连接关闭失败");
                e.printStackTrace();
            }
        }
        //关闭客户端代理连接
        serverAgentChannel.close();
    }


    @Override
    public String toString() {
        return "ServerAgent{" +
                "serverAgentPort=" + serverAgentPort +
                ", mapperPort=" + mapperPort +
                ", key='" + key + '\'' +
                '}';
    }



    public class Mapper implements Runnable {
        Mapper() {
        }

        @Override
        public void run() {
            while (!serverAgentChannel.isClosed() && !mapperSocket.isClosed()) {
                //获取访问的源连接
                Socket source = getSocket();
                if (source != null) {
                    System.out.println("侦测到" + getSourceSocketAddress(source) + "来访");
                    serverAgentChannel.sendMessage();
                    Socket dest = serverAgentChannel.connectDataSocket();
                    if (dest != null) {
                        System.out.println("连接到客户端");
                        new Thread(new Transfer(source, dest, openInputStream(source), openOutputStream(dest))).start();
                        new Thread(new Transfer(dest, source, openInputStream(dest), openOutputStream(source))).start();
                    }
                }
            }
        }

        //获取当前访问映射端口的连接
        Socket getSocket() {
            Socket sourceSocket = null;
            try {
                //获取新客户端连接
                sourceSocket = mapperSocket.accept();
            } catch (IOException e) {
                System.out.println("获取新客户端连接失败");
//                e.printStackTrace();
            }
            return sourceSocket;
        }

        //获取当前信道的客户端连接的地址
        String getSourceSocketAddress(Socket sourceSocket) {
            return "[" + sourceSocket.getRemoteSocketAddress() + "]";
        }

        //打开指定连接的输入流
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

        //打开指定连接的输出流
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
