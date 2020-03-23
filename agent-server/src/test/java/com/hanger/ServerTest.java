package com.hanger;

import com.hanger.util.TokenUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author hanger
 * 2020-01-29 15:06
 */
public class ServerTest {

    @Test
    public void objectTest() {
//        for (int i = 0; i < 10; i++) {
//            URI uri;
//            try {
//                uri = new URI("http://jktb.haedu.gov.cn/?ext=emplIi0xPi00eXt6Ng==&rn=363624432");
//                Desktop.getDesktop().browse(uri);
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//                System.out.println("打开网页失败！");
//            }
//        }


//        for (int i = 0; i < 10; i++) {
//            System.out.println(TokenUtil.createKey());
//        }
    }



    @Test
    public void strTest() {
        String[] s = new String[]{"sj28:da", "sjd a", "sj28da:", ":j28da", ":", "::"};
        for (String value : s) {
            String[] split = value.split(":");
            System.out.println(Arrays.toString(split));
            System.out.println(split.length);
        }
    }



    @Test
    public void numTest() {
        String[] s = new String[]{"sj28:da", "254 ", "", null, "12 3", "1.2", " 12", "1a2", "012"};
        for (String value : s) {
            System.out.println(value + "==" + TokenUtil.isNumeric(value));
//            System.out.println(value + "==" + value.isEmpty());
        }

        //0开头的数字字符串转具体数字时会被省略
        String a = "0123";
        System.out.println(Long.parseLong(a));

    }







}
