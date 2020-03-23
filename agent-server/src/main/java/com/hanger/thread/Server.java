package com.hanger.thread;

import com.hanger.channel.ServerChannel;
import com.hanger.data.MapTable;
import com.hanger.data.PortQueue;
import com.hanger.model.SignalModel;
import com.hanger.util.TokenUtil;


/**
 * 服务端：根据客户端的请求创建服务代理
 *
 * @author hanger
 * 2020-01-16 17:03
 */
public class  Server implements Runnable {
    //端口队列
    private PortQueue portQueue;
    //映射表
    private MapTable mapTable;
    //监听者对应的通信信道
    private ServerChannel serverChannel;

    public Server(PortQueue portQueue, MapTable mapTable, ServerChannel serverChannel) {
        this.portQueue = portQueue;
        this.mapTable = mapTable;
        this.serverChannel = serverChannel;
    }

    /**
     注意：通过信道调用对客户端的读写操作之前必须调用一次getSocket()方法这样
     才能保证后面的读写操作是当前socket，否则当前读写操作将是最近一次
     客户端会话的socket的。
     */
    @Override
    public void run() {
        while (!serverChannel.isClosed()) {
            serverChannel.getSocket();
            //以英文的冒号为分隔符读取信息
            String reads = serverChannel.read();
            System.out.print(serverChannel.getClientAddress());
            if (reads.equals(SignalModel.NEW_MAPPING.getDescribe())) {
                System.out.println("请求建立新映射");
                //从端口队列两个队首各取一个端口但不删除
                Integer[] ports = portQueue.getHeads();
                //如果获取端口成功
                if (ports != null) {
                    //创建口令
                    String key = TokenUtil.createKey();
                    //回复给客户端将要申请到的端口
                    serverChannel.write(key + ":" + ports[0]);
                    //如果得到回复确认
                    if (serverChannel.read().equals(SignalModel.COPY_THAT.getDescribe())) {
                        //创建并启动对应端口的服务代理线程
                        ServerAgent serverAgent = new ServerAgent(mapTable, ports[0], ports[1], key);
                        new Thread(serverAgent).start();
                        //删除队首端口
                        portQueue.delHeads();
                        //通知客户端映射开始
                        serverChannel.write(SignalModel.MAPPING_BEGINNING.getDescribe());
                    }
                } else {
                    serverChannel.write(SignalModel.UNAVAILABLE_PORT.getDescribe());
                }
            }
            //不管之前有没有成功都关闭当前连接开始处理下一个
            serverChannel.closeSocket();
        }
    }
}
