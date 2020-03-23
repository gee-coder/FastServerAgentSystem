package com.hanger.channel;

import com.hanger.model.SignalModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author hanger
 * 2020-01-28 21:16
 */
public class ClientAgentChannel {
    /**
     * 服务代理ip地址
     */
    private String serverAgentIpv4;
    /**
     * 服务代理端口
     */
    private Integer serverAgentPort;
    /**
     * 客户端代理连接
     */
    private Socket clientAgentSocket;
    /**
     * 客户端代理的连接
     */
    private InputStream inputStream;
    /**
     * 客户端代理的输出流
     */
    private OutputStream outputStream;
    private Charset charset = StandardCharsets.UTF_8;


    public ClientAgentChannel(String serverAgentIpv4, Integer serverAgentPort) {
        this.serverAgentIpv4 = serverAgentIpv4;
        this.serverAgentPort = serverAgentPort;
        connect();
    }


    private void connect() {
        try {
            this.clientAgentSocket = new Socket(serverAgentIpv4, serverAgentPort);
            /*
            注意：服务端部署在windows并且客户端与服务端RFC协议版本不同时，客户端
            发送心跳数据包sendUrgentData()方法在第18次会发送失败的bug
            设置keepAlive时客户端socket会自动隔12分钟向服务端发送心跳数据
            如果发送失败，客户端会等11分钟再发一次，如果12分钟内没得到响应
            客户端socket会自动断开
             */
//            this.clientAgentSocket.setKeepAlive(true);
        } catch (IOException e) {
            //连接服务端失败
            System.out.println("连接服务端失败");
            e.printStackTrace();
        }
    }


    public void applyMapperPort(String key, long heartbeatCycle) throws IOException {
        write(key + ":" + heartbeatCycle);
        String read = read();
        System.out.println("映射地址[http://" + serverAgentIpv4 + ":" + read + "]");
        write(SignalModel.MAPPING_SUCCESS.getDescribe());
    }


    /**
     * 获取当前信道的输入流
     */
    private void getInputStream() {
        if (clientAgentSocket != null) {
            try {
                //获取新客户端输入流
                this.inputStream = clientAgentSocket.getInputStream();
            } catch (IOException e) {
                System.out.println("获取客户端输入流失败");
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取当前信道的输出流
     */
    private void getOutputStream() {
        if (clientAgentSocket != null) {
            try {
                //获取新客户端输出流
                this.outputStream = clientAgentSocket.getOutputStream();
            } catch (IOException e) {
                System.out.println("获取客户端输出流失败!");
//                e.printStackTrace();
            }
        }
    }


    public Socket getSource() {
        Socket socket = null;
        try {
            socket = new Socket(serverAgentIpv4, serverAgentPort);
        } catch (IOException e) {
            //连接服务端失败
            System.out.println("连接服务端失败");
            e.printStackTrace();
        }
        return socket;
    }


    //读数据
    public String read() {
        getInputStream();
        String read = null;
        byte[] bytes = new byte[32];
        int length;
        try {
            length = inputStream.read(bytes);
            read = new String(bytes, 0, length);
        } catch (IOException e) {
            System.out.println("读取数据失败");
//            e.printStackTrace();
        }

        //不管有没有成功都返回
        return read;
    }


    //写数据
    public void write(String message) throws IOException {
        getOutputStream();
        outputStream.write(message.getBytes(charset));
        outputStream.flush();
    }


    //关闭信道
    public Boolean isClosed() {
        return clientAgentSocket.isClosed();
    }


    //关闭信道
    public void close() {
        //关闭客户端代理连接的输入流
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println("客户端代理连接的输入流关闭失败！");
                e.printStackTrace();
            }
        }
        //关闭客户端代理连接的输出流
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                System.out.println("客户端代理连接的输出流关闭失败！");
                e.printStackTrace();
            }
        }
        //关闭客户端代理连接
        if (!clientAgentSocket.isClosed()) {
            try {
                clientAgentSocket.close();
//                System.out.println("信道关闭成功！");
            } catch (IOException e) {
                System.out.println("客户端代理连接关闭失败！");
                e.printStackTrace();
            }
        }
    }
}
