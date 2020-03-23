package com.hanger.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author hanger
 * 2020-01-28 21:13
 */
public class ServerChannel {
    //服务线程的服务连接
    private ServerSocket serverSocket;
    //当前的客户端连接
    private Socket socket;
    /*
    信道字符编码默认为UTF-8，UTF-8编码下每个汉字占3个字节，每
    个英文符号和数字占一个字节
     */
    private Charset charset = StandardCharsets.UTF_8;


    public ServerChannel(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }



    //获取当前信道的当前客户端连接
    public void getSocket() {
        try {
            //获取新客户端连接
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("获取新客户端连接失败");
            e.printStackTrace();
            this.socket = null;
        }
    }


    //获取当前信道的输入流
    private InputStream getInputStream() {
        InputStream inputStream = null;
        if (socket != null) {
            try {
                //获取客户端输入流
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                System.out.println("服务端获取输入流失败");
                e.printStackTrace();
            }
        }
        //不管有没有成功都返回
        return inputStream;
    }


    //获取当前信道的输出流
    private OutputStream getOutputStream() {
        OutputStream outputStream = null;
        if (socket != null) {
            try {
                //获取新客户端输出流
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                System.out.println("获取新客户端输出流失败");
                e.printStackTrace();
            }
        }
        //不管有没有成功都返回
        return outputStream;
    }


    //获取当前信道的客户端连接的公网IP地址
    public String getClientAddress() {
        SocketAddress remoteSocketAddress = null;
        if (socket != null) {
            remoteSocketAddress = socket.getRemoteSocketAddress();
        }
        //不管有没有成功都返回
        return "[" + remoteSocketAddress + "]";
    }


    //读数据
    public String read() {
        InputStream is = getInputStream();
        String read = null;
        if (is != null) {
            byte[] bytes = new byte[1024];
            int length;
            try {
                length = is.read(bytes);
                read = new String(bytes, 0, length);
            } catch (IOException e) {
                System.out.println("读取数据失败");
                e.printStackTrace();
            }
        }
        //不管有没有成功都返回
        return read;
    }


    //写数据
    public void write(String message) {
        OutputStream os = getOutputStream();
        if (os != null) {
            try {
                os.write(message.getBytes(charset));
                os.flush();
            } catch (IOException e) {
                System.out.println("回写数据失败");
                e.printStackTrace();
            }
        }
    }


    //关闭信道连接当前对应的客户端连接
    public void closeSocket() {
        if (!socket.isClosed()) {
            try {
                socket.close();
//                System.out.println("信道连接当前对应的客户端连接关闭成功！");
            } catch (IOException e) {
                System.out.println("信道连接当前对应的客户端连接关闭失败！");
                e.printStackTrace();
            }
        }
    }


    //判断信道连接是否关闭
    public Boolean isClosed() {
        return serverSocket.isClosed();
    }


    //关闭信道连接
    public void closeServerSocket() {
        try {
            serverSocket.close();
            System.out.println("信道连接关闭成功！");
        } catch (IOException e) {
            System.out.println("信道连接关闭失败！");
            e.printStackTrace();
        }
    }


}
