package com.hanger.util;


/**
 * @author hanger
 * 2019-08-27 11:37
 */
public class TokenUtil {

    /**
     *
     * @return 随机生成6位字符串(0-1,a-Z)
     */
    public static String createKey() {
        StringBuilder code = new StringBuilder();
        //随机字符池
        String model = "0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
        char[] m = model.toCharArray();
        for (int i = 0; i < 6; i++) {
            code.append(m[(int) (Math.random() * 62)]);
        }
        //连接符
        code.append("-");
        //时间戳
        code.append(System.currentTimeMillis());
        return code.toString();
    }


    /**
     * 判断是否为纯数字
     *
     * @param str 要判断的字符串
     * @return 是否
     */
    public static Boolean isNumeric(String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[0-9]*$");
    }








}


