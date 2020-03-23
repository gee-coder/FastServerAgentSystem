package com.hanger;

import com.hanger.model.SignalModel;
import org.junit.Test;

import java.nio.charset.StandardCharsets;


/**
 * @author hanger
 * 2020-02-28 21:25
 */
public class CommonTest {
    @Test
    public void TestEnum() {
        System.out.println(SignalModel.HEARTBEAT);
        System.out.println(SignalModel.HEARTBEAT.getDescribe());
        System.out.println(SignalModel.HEARTBEAT.getDescribe().equals("心跳"));
        System.out.println(SignalModel.getEnum("HEARTBEAT"));
//        System.out.println(SignalModel.HEARTBEAT.equals("HEARTBEAT"));
//        System.out.println(SignalModel.getEnum("HEARTBEAT").equals("HEARTBEAT"));
        System.out.println("请求建立新映射".getBytes(StandardCharsets.UTF_8).length);
    }


    @Test
    public void TestLock() {
        Integer[] integers = new Integer[9];
        System.out.println(integers);
        System.out.println(integers.length);

//        for (int i = 0; i < integers.length; i++) {
//            System.out.println(integers[i] == null);
//        }

        integers[5] = 1;
        integers[8] = 2;

        for (int i = 0; i < integers.length; i++) {
            System.out.println(integers[i]);
        }

        System.out.println();
        integers[5] = null;

        for (int i = 0; i < integers.length; i++) {
            System.out.println(integers[i]);
        }

        System.out.println();
    }

}
