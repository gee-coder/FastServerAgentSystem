package com.hanger.config;

/**
 * @author hanger
 * 2020-01-28 21:12
 */
public class ClientConfig {
    //服务端公网IP
    private String serverIp;
    //服务端监听者线程端口
    private Integer serverPort;
    //映射表的映射失效时间(毫秒),默认30秒
    private long mapInvalidTime = 30000;
    //心跳间隔时间(毫秒)
    private long heartbeatCycle = 30000;
    //心跳复苏次数
    private Integer heartbeatRecovery = 3;
    //检查映射表的周期(毫秒)
    private long cleanCycle = 15000;


    //保留全参构造方法


    public ClientConfig(String serverIp, Integer serverPort, long mapInvalidTime, long heartbeatCycle, Integer heartbeatRecovery, long cleanCycle) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.mapInvalidTime = mapInvalidTime;
        this.heartbeatCycle = heartbeatCycle;
        this.heartbeatRecovery = heartbeatRecovery;
        this.cleanCycle = cleanCycle;
    }

    public ClientConfig(String serverIp, Integer serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    //生成所有的属性的get方法
    public String getServerIp() {
        return serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public long getMapInvalidTime() {
        return mapInvalidTime;
    }

    public long getHeartbeatCycle() {
        return heartbeatCycle;
    }

    public Integer getHeartbeatRecovery() {
        return heartbeatRecovery;
    }

    public long getCleanCycle() {
        return cleanCycle;
    }
}
