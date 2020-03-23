package com.hanger.channel;

import com.hanger.model.SignalModel;
import com.hanger.util.TokenUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author hanger
 * 2020-01-28 21:14
 */
public class ServerAgentChannel {
    //服务端代理的信道连接
    private ServerSocket serverAgentSocket;
    //当前的客户端连接
    private Socket clientAgentSocket;
    //客户端代理的连接
    private InputStream inputStream;
    //客户端代理的输出流
    private OutputStream outputStream;
    private Charset charset = StandardCharsets.UTF_8;

    public ServerAgentChannel(ServerSocket serverAgentSocket) {
        this.serverAgentSocket = serverAgentSocket;
    }


    //获取信道的当前客户端连接
    private Socket getSocket() {
        Socket socket = null;
        try {
            //获取新客户端连接
            socket = serverAgentSocket.accept();
        } catch (IOException e) {
            System.out.println("获取新客户端连接失败");
            e.printStackTrace();
        }
        return socket;
    }


    public long connect(String key, Integer mapperPort) {
        while (!serverAgentSocket.isClosed()) {
            Socket socket = getSocket();
            if (socket != null) {
                getInputStream(socket);
                getOutputStream(socket);
                String[] split = read().split(":");
                if (split.length == 2 && split[0].equals(key) && TokenUtil.isNumeric(split[1])) {
                    write(String.valueOf(mapperPort));
                    if (read().equals(SignalModel.MAPPING_SUCCESS.getDescribe())) {
                        this.clientAgentSocket = socket;
                        return Long.parseLong(split[1]);
                    }
                }
                //能执行到此处的必定是不符合要求的连接
                closeSocket(socket);
            }
        }
        return -1;
    }


    public void sendMessage() {
        write(SignalModel.NEW_CONNECTION.getDescribe());
        System.out.println("告知成功！");
    }


    public Socket connectDataSocket() {
        return getSocket();
    }


    //判断服务端代理的信道连接是否关闭
    public Boolean isClosed() {
        //判断数据服务连接与消息连接是否可用
        return serverAgentSocket.isClosed() || clientAgentSocket.isClosed();
    }


    //获取当前信道的输入流
    private void getInputStream(Socket clientAgentSocket) {
        if (clientAgentSocket != null) {
            try {
                //获取输入流
                this.inputStream = clientAgentSocket.getInputStream();
            } catch (IOException e) {
                System.out.println("服务代理获取输入流失败");
                e.printStackTrace();
            }
        }
    }


    //获取当前信道的输出流
    private void getOutputStream(Socket clientAgentSocket) {
        if (clientAgentSocket != null) {
            try {
                //获取新客户端输出流
                this.outputStream = clientAgentSocket.getOutputStream();
            } catch (IOException e) {
                System.out.println("服务代理获取输出流失败!");
                e.printStackTrace();
            }
        }
    }


    //读数据
    public String read() {
        String read = null;
        byte[] bytes = new byte[32];
        int length;
        try {
            length = inputStream.read(bytes);
            read = new String(bytes, 0, length);
        } catch (IOException e) {
            System.out.println("读取客户端代理数据失败!");
//            e.printStackTrace();
        }
        //不管有没有成功都返回
        return read;
    }


    //写数据
    private void write(String message) {
        try {
            outputStream.write(message.getBytes(charset));
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("回写数据失败!");
            e.printStackTrace();
        }
    }

    private void closeSocket(Socket socket) {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.out.println("信道连接当前对应的客户端连接关闭成功！");
            } catch (IOException e) {
                System.out.println("信道连接当前对应的客户端连接关闭失败！");
                e.printStackTrace();
            }
        }
    }


    //关闭信道
    public void close() {
        //关闭服务监听端口连接
        if (!serverAgentSocket.isClosed()) {
            try {
                //关闭服务端代理信道的连接
                serverAgentSocket.close();
                System.out.println("服务端代理信道的连接关闭成功");
            } catch (IOException e) {
                System.out.println("服务端代理信道的连接关闭失败");
                e.printStackTrace();
            }
        }
        //关闭消息连接
        closeSocket(clientAgentSocket);
    }
}
