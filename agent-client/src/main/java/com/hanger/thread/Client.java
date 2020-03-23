package com.hanger.thread;

import com.hanger.channel.ClientChannel;
import com.hanger.data.MapTable;
import com.hanger.model.GUIModel;
import com.hanger.model.SignalModel;
import com.hanger.util.HttpUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author hanger
 * 2020-01-16 17:14
 */
public class Client implements Runnable {
    /**
     * 映射表
     */
    private MapTable mapTable;
    /**
     * 服务代理ip
     */
    private String serverIp;
    /**
     *服务代理端口
     */
    private Integer serverPort;
    /**
     *心跳间隔时间(毫秒)
     */
    private long heartbeatCycle;
    /**
     *客户端信道
     */
    private ClientChannel clientChannel = new ClientChannel();


    public Client(MapTable mapTable, String serverIp, Integer serverPort, long heartbeatCycle) {
        this.mapTable = mapTable;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.heartbeatCycle = heartbeatCycle;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        label:
        while (true) {
            System.out.print(GUIModel.ITEM);
            String inputs = readInputs(bufferedReader);
            if (inputs != null) {
                //回车直接跳过
                if (inputs.length() == 0) {
                    continue;
                }
                switch (inputs) {
                    case "1":
                        //打印表信息
                        System.out.println(mapTable.toString());
                        break;
                    case "2":
                        //添加新映射
                        System.out.print(GUIModel.TIP1);
                        String[] reads = checkInputs(readInputs(bufferedReader));
                        if (reads == null) {
                            System.out.println(GUIModel.WARNING);
                            continue;
                        }
                        startService(reads[0], Integer.parseInt(reads[1]));
                        break;
                    case "3":
                        //打印表信息
                        System.out.println(mapTable.toString());
                        //删除对应映射
                        System.out.print(GUIModel.TIP2);
                        String key = readInputs(bufferedReader);
                        if (key.length() != 20) {
                            System.out.println(GUIModel.WARNING);
                        } else {
                            mapTable.delByKey(key);
                            System.out.println("删除完毕！");
                        }
                        break;
                    case "4":
                        //优雅退出
                        System.out.print(GUIModel.EXIT);
                        //删除所有映射并关闭所有连接
                        mapTable.flushTable();
                        break label;
                    default:
                        System.out.println(GUIModel.WARNING);
                        break;
                }
            }
        }
        //删除所有映射并关闭所有连接
        mapTable.flushTable();
    }


    //获取控制台输入
    private String readInputs(BufferedReader bufferedReader) {
        //获取控制台输入数据
        String input = null;
        try {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("获取输入异常！");
            e.printStackTrace();
        }
        return input;
    }


    //检查输入的ip和port是否合法
    private String[] checkInputs(String inputs) {
        String[] split = inputs.split(":");
        if (HttpUtil.isIpv4(split[0]) && HttpUtil.isPort(split[1])) {
            return split;
        } else {
            return null;
        }
    }


    //启动服务
    private void startService(String ip, Integer port) {
        //1、对本地要映射的服务进行ping测试
        if (HttpUtil.ping(ip, port)) {
            //2、申请服务端映射服务代理
            String s = applyServerAgentPort();
            //3、创建客户端映射服务代理
            if (s != null) {
                createClientAgent(s, ip, port);
            }
        }
    }


    //申请服务代理端口
    private String applyServerAgentPort() {
        Socket connect = clientChannel.connect(serverIp, serverPort);
        if (connect != null) {
            clientChannel.getInputStream();
            clientChannel.getOutputStream();
            clientChannel.write(SignalModel.NEW_MAPPING.getDescribe());
            String read = clientChannel.read();
            if (read != null) {
                if (read.equals(SignalModel.UNAVAILABLE_PORT.getDescribe())) {
                    System.out.println("服务器暂时无可用端口，请稍后再试");
                    return null;
                }
                clientChannel.write(SignalModel.COPY_THAT.getDescribe());
                if (clientChannel.read().equals(SignalModel.MAPPING_BEGINNING.getDescribe())) {
                    return read;
                } else {
                    /*
                    此时服务端已经删除端口并启动代理服务了，防止服务代理长期等待
                    继续返回，但是这种情况几乎不会出现
                     */
                    System.out.println("服务器回复信息异常");
                    return read;
                }
            }
        }
        return null;
    }


    /**
     *创建客户端代理服务线程并启动
     */
    private void createClientAgent(String s, String ip, Integer port) {
        String[] split = s.split(":");
        //获取key
        String key = split[0];
        //获取serverAgentPort
        Integer serverAgentPort = Integer.parseInt(split[1]);
        ClientAgent clientAgent = new ClientAgent(mapTable, serverIp, serverAgentPort, key, ip, port, heartbeatCycle);
        new Thread(clientAgent).start();
    }








}
