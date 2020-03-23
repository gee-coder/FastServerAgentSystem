package com.hanger.thread;

import com.hanger.channel.ServerChannel;
import com.hanger.config.ServerConfig;
import com.hanger.data.MapTable;
import com.hanger.data.PortQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author hanger
 * 2020-01-28 21:17
 */
public class ServerInitializer {
    //服务端配置
    private ServerConfig serverConfig;
    //端口队列
    private PortQueue portQueue;
    //映射表
    private MapTable mapTable;


    public ServerInitializer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


    //启动初始化程序
    public void start() {
        //初始化端口队列
        this.portQueue = new PortQueue(serverConfig.getPortRange());
        //初始化映射表
        this.mapTable = new MapTable(serverConfig.getMapInvalidTime());
        //初始化服务线程实例并启动服务线程
        createServer(serverConfig.getServerPort());
        //初始化清洁者实例并启动清洁者线程
        createCleaner(serverConfig.getCleanCycle());
    }


    //创建并启动服务线程
    private void createServer(Integer serverPort) {
        //生成服务端信道
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.out.println("初始化监听信道失败");
            e.printStackTrace();
            return;
        }
        //生成服务线程实例
        Server server = new Server(portQueue, mapTable, new ServerChannel(serverSocket));
        //启动服务线程
        new Thread(server).start();
    }


    /**
     * 考虑到优雅关闭服务器，将来会启用Timer改用线程
     *
     * @param cleanCycle 清理周期
     */
    //创建并启动定期清理线程
    private void createCleaner(long cleanCycle) {
        //timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //遍历映射池当前的Entry集合
                for (Map.Entry<ServerAgent, Date> next : mapTable.entrySet()) {
                    Integer[] ports = mapTable.checkEntry(next);
                    if (ports != null) {
                        portQueue.addTails(ports);
                    }
                }

                //展示当前可用端口队列与映射表所有映射
//                System.out.println(portQueue.toString());
//                System.out.println(mapTable.toString());
            }
        }, 0, cleanCycle);
    }




}
