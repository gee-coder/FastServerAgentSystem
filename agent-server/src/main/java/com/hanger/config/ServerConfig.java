package com.hanger.config;

/**
 * @author hanger
 * 2020-01-28 21:10
 */
public class ServerConfig {
    //服务线程的端口
    private Integer serverPort;
    /*
    端口队列端口范围(双闭区间)，如果有多个区间用英文‘,’分隔
    对于单个端口号，用port-port表示。比如8080-8080
     */
    private String portRange;
    //映射表的映射失效时间(毫秒),默认30秒
    private long mapInvalidTime = 30000;
    //检查映射表的周期(毫秒)
    private long cleanCycle = 30000;

    public ServerConfig(Integer serverPort, String portRange, long mapInvalidTime, long cleanCycle) {
        this.serverPort = serverPort;
        this.portRange = portRange;
        this.mapInvalidTime = mapInvalidTime;
        this.cleanCycle = cleanCycle;
    }

    public ServerConfig(Integer serverPort, String portRange) {
        this.serverPort = serverPort;
        this.portRange = portRange;
    }

    public long getMapInvalidTime() {
        return mapInvalidTime;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public String getPortRange() {
        return portRange;
    }

    public long getCleanCycle() {
        return cleanCycle;
    }
}
