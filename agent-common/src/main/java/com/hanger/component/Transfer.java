package com.hanger.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 转发器
 *
 * @author hanger
 * 2020-01-15 0:09
 */
public class Transfer implements Runnable {
    private Socket source;
    private Socket dest;
    private InputStream inputStream;
    private OutputStream outputStream;


    public Transfer(Socket source, Socket dest, InputStream inputStream, OutputStream outputStream) {
        this.source = source;
        this.dest = dest;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }


    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int length;
            while (!source.isClosed() && !dest.isClosed() && (length = inputStream.read(buffer)) > 0) {
                try {
                    outputStream.write(buffer, 0, length);
                } catch (IOException e) {
                    System.out.println("数据流转发失败");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
//            System.out.println("转发异常");
//                e.printStackTrace();
        } finally {
            closeSocket(source);
            closeSocket(dest);
        }
    }


    //关闭指定Socket连接
    private void closeSocket(Socket socket) {
        if (!socket.isClosed()) {
            try {
                socket.close();
//                System.out.println("连接关闭成功");
            } catch (IOException e) {
                System.out.println("连接关闭失败");
//                e.printStackTrace();
            }
        }
    }
}
