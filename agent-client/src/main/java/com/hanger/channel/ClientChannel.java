package com.hanger.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author hanger
 * 2020-01-28 21:15
 */
public class ClientChannel {
    //客户端连接
    private Socket clientSocket;
    //客户端连接的输入流
    private InputStream inputStream;
    //客户端连接的输出流
    private OutputStream outputStream;

    /*
    信道字符编码默认为UTF-8，UTF-8编码下每个汉字占3个字节，每
    个英文符号和数字占一个字节
    */
    private Charset charset = StandardCharsets.UTF_8;


    public ClientChannel() {
    }


    //创建客户端服务信道
    public Socket connect(String serverIP, Integer serverPort) {
        //生成连接
        try {
            this.clientSocket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("信道连接初始化失败！");
        }
        return clientSocket;
    }


    //获取当前信道的输入流
    public void getInputStream() {
        try {
            //获取新客户端输入流
            this.inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("获取新客户端输入流失败");
            e.printStackTrace();
        }
    }


    //获取当前信道的输出流
    public void getOutputStream() {
        try {
            //获取新客户端输出流
            this.outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("获取新客户端输出流失败");
            e.printStackTrace();
        }
    }


    //读数据
    public String read() {
        String read = null;
        if (inputStream != null) {
            byte[] bytes = new byte[1024];
            int length;
            try {
                length = inputStream.read(bytes);
                read = new String(bytes, 0, length);
            } catch (IOException e) {
                System.out.println("数据读取失败");
                e.printStackTrace();
            }
        }
        //不管有没有成功都返回
        return read;
    }


    //写数据
    public void write(String message) {
        if (outputStream != null) {
            try {
                outputStream.write(message.getBytes(charset));
                outputStream.flush();
            } catch (IOException e) {
                System.out.println("数据回写失败");
                e.printStackTrace();
            }
        }
    }


    //关闭当前连接
    public void close() {
        //关闭输入流
        if (inputStream != null) try {
            inputStream.close();
            inputStream = null;
        } catch (IOException e) {
            System.out.println("关闭输入流失败");
            e.printStackTrace();
        }
        //关闭输出流
        if (outputStream != null) try {
                outputStream.close();
                outputStream = null;
        } catch (IOException e) {
            System.out.println("关闭输出流失败");
                e.printStackTrace();
        }
        //关闭客户端连接
        if (!clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("关闭连接失败");
                e.printStackTrace();
            }
        }
    }



}
