package com.hanger.data;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 端口队列：存放所有可用端口
 *
 * @author hanger
 * 2020-01-29 13:36
 */
public class PortQueue {
    //服务代理端口队列
    private ConcurrentLinkedQueue<Integer> serverAgentQueue;

    //映射端口队列
    private ConcurrentLinkedQueue<Integer> mapperQueue;


    public PortQueue(String portRange) {
        create(portRange);
    }


    //创建端口队列
    private void create(String portRange) {
        this.serverAgentQueue = new ConcurrentLinkedQueue<>();
        this.mapperQueue = new ConcurrentLinkedQueue<>();
        //将不同区间的分割为数组
        String[] split = portRange.split(",");
        for (String ports:
                split) {
            //获取每个区间的起始值和结束值
            Integer start = Integer.parseInt(ports.split("-")[0]);
            Integer end = Integer.parseInt(ports.split("-")[1]);
            //将端口值加入端口池
            while (start <= end) {
                //先加mapperPort，所以mapperPort可能会比serverAgentPort多一个或者两者正好相等
                if (serverAgentQueue.size() > mapperQueue.size()) {
                    mapperQueue.add(start);
                } else {
                    serverAgentQueue.add(start);
                }
                ++start;
            }
        }
    }


    //从两个队首分别获取一个端口但不删除，如果没有可用的端口就返回null
    public Integer[] getHeads() {
        Integer[] ports = new Integer[2];

        ports[0] = serverAgentQueue.peek();
        ports[1] = mapperQueue.peek();
        /*
        正如create方法加端口的策略，mapperPort永远不会小于serverAgentPort。
        就像木桶的短板效应一样，只判断ports[0]等于null就可以了
         */
        if (ports[0] == null) {
            return null;
        } else {
            return ports;
        }
    }


    //从两个队首分别删除一个端口
    public void delHeads() {
        serverAgentQueue.poll();
        mapperQueue.poll();
    }


    //从两个队尾部插入指定的元素
    public void addTails(Integer[] ports) {
        serverAgentQueue.offer(ports[0]);
        mapperQueue.offer(ports[1]);
    }


    @Override
    public String toString() {
        return "PortQueue{" +
                "serverAgentQueue=" + serverAgentQueue +
                ", mapperQueue=" + mapperQueue +
                '}';
    }
}
