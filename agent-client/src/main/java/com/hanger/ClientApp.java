package com.hanger;

import com.hanger.config.ClientConfig;
import com.hanger.model.GUIModel;
import com.hanger.thread.ClientInitializer;

/**
 * @author hanger
 * 2020-01-28 21:09
 */
public class ClientApp {
    public static void main(String[] args) {
        //服务端公网IP   127.0.0.1  39.96.74.175  49.234.62.154
        String serverIp = "49.234.62.154";
        //服务端监听者线程端口
        Integer serverPort = 10000;
        //心跳间隔时间(毫秒)
//    private long heartbeatCycle = 3000;


        //显示logo
//        System.out.println(GUIModel.LOGO);
        System.out.println(GUIModel.VERSION);

        //装配配置项
        ClientConfig clientConfig = new ClientConfig(serverIp, serverPort);

        //根据配置生成初始化器
        ClientInitializer clientInitializer = new ClientInitializer(clientConfig);

        //想映射那个客户端端口，只需要start(clientPort)，其中clientPort是整数类型
        clientInitializer.start();

        //启动图形化界面
//        Application.launch(Console.class, args);
    }


}
