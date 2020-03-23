package com.hanger;

import com.hanger.config.ServerConfig;
import com.hanger.model.GUIModel;
import com.hanger.thread.ServerInitializer;

/**
 * @author hanger
 * 2020-01-28 21:07
 */
public class ServerApp {
    //服务线程的端口
    private static final Integer serverPort = 10000;
    /*
    端口队列端口范围(双闭区间)，如果有多个区间用英文‘,’分隔
    对于单个端口号，用port-port表示。比如8080-8080
     */
    private static final String portRange = "10001-10003,10004-10004,10005-10010";
    //映射表的映射失效时间(毫秒)
//    private static final long mapInvalidTime = 30000;
    //清洁者检查映射池的周期(毫秒)
//    private static final long cleanCycle = 30000;


    public static void main(String[] args) {
        //打印logo
        System.out.println(GUIModel.ANSI_BLUE + GUIModel.LOGO + GUIModel.ANSI_RESET);
        //打印版本
        System.out.println(GUIModel.ANSI_PURPLE + GUIModel.VERSION + GUIModel.ANSI_RESET);

        //装配配置项
        ServerConfig serverConfig = new ServerConfig(serverPort, portRange);

        //根据配置生成初始化器
        ServerInitializer serverInitializer = new ServerInitializer(serverConfig);

        //启动初始化器
        serverInitializer.start();
    }
}
