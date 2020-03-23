package com.hanger;

import com.hanger.util.HttpUtil;
import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author hanger
 * 2020-01-28 21:26
 */
public class ClientTest {

    @Test
    public void sad() {
//        String s = "3Js20M05933";
//
//        String key = s.substring(0, 6);
//        System.out.println(key);
//        String replace = s.replace(key, "");
//        System.out.println(replace);



//        String string = GUIModel.LOGO;
//        String r1 = string.replace(' ', 'h');
//        System.out.println(r1);
//
//        String r2 = r1.replace('@', ' ');
//        System.out.println(r2);
//
//        String r3 = r2.replace('h', '@');
//        System.out.println(r3);
//
//        char[] chars = {'/', '`', ',', '\\', '[', ']', '=', '^'};
//        for (char aChar : chars) {
//            r3 = r3.replace(aChar, ' ');
//        }
//
//        System.out.println(r3);


        System.out.println(HttpUtil.ping("49.234.62.154", 10001));

    }

    @Test
    public void ping() {
        String ip = "127.0.0.1";
        Integer port = 8080;

        try {
            Socket socket = new Socket(ip, port);
            socket.sendUrgentData(0xff);
            System.out.println("pong");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("目标服务端口无法ping通");
        }
    }


    @Test
    public void timerTest() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                /**
//                 * 考虑加失败重试次数机制的配置
//                 */
//                //对服务器进行ping测试
//                System.out.println("aa");
//            }
//        }, 0, 300);
//
//        System.out.println("ll");

//        String s = null;
//        System.out.println(s.equals(""));

        for (int i = 0; i < 10; i++) {
            System.out.println(System.currentTimeMillis());
        }

    }


}
