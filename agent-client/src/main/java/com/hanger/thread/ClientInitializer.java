package com.hanger.thread;

import com.hanger.config.ClientConfig;
import com.hanger.data.MapTable;


/**
 * @author hanger
 * 2020-01-01 16:51
 */
public class ClientInitializer {
    //客户端配置
    private ClientConfig clientConfig;


    public ClientInitializer(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }


    public void start() {
        //初始化映射表
        MapTable mapTable = new MapTable(clientConfig.getMapInvalidTime());
        //创建并启动客户端线程
        new Thread(
                new Client(
                        mapTable,
                        clientConfig.getServerIp(),
                        clientConfig.getServerPort(),
                        clientConfig.getHeartbeatCycle()
                )
        ).start();
        //创建并启动清洁者线程
        new Thread(new Cleaner(mapTable, clientConfig.getCleanCycle())).start();
    }


}