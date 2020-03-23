package com.hanger.util;


import java.io.IOException;
import java.net.Socket;

/**
 * @author hanger
 * 2019-11-10 19:39
 */
public class HttpUtil {

    private static final Integer PORT_MIN_LENGTH = 1;
    private static final Integer PORT_MAX_LENGTH = 5;
    private static final Integer IPV4_MIN_LENGTH = 7;
    private static final Integer IPV4_MAX_LENGTH = 15;

    /**
     *判断字符串是否是ipv4地址
     *
     * @param str 要判断字符串
     * @return 是否是ipv4地址
     */
    public static boolean isIpv4(String str) {
        if ((str == null) || (str.length() < IPV4_MIN_LENGTH) || (str.length() > IPV4_MAX_LENGTH)) {
            return false;
        } else {
            String regex = "^([1-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))(\\.([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))){3}$";
            return str.matches(regex);
        }
    }


    /**
     *判断字符串是否是端口号
     *
     * @param str 要判断字符串
     * @return 是否是端口号
     */
    public static boolean isPort(String str) {
        if ((str == null) || (str.length() < PORT_MIN_LENGTH) || (str.length() > PORT_MAX_LENGTH)) {
            return false;
        } else {
            String regex = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
            return str.matches(regex);
        }
    }


    /**
     * 对目标服务端口进行ping测试
     *
     * @param ip 服务ipv4地址
     * @param port 服务端口号
     * @return 是否成功
     */
    public static boolean ping(String ip, Integer port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.sendUrgentData(0xff);
//            System.out.println("pong");
            return true;
        } catch (IOException e) {
            System.out.println("无法ping通目标服务端口");
//            e.printStackTrace();
            return false;
        }
    }



}
