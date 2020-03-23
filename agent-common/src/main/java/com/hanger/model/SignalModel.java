package com.hanger.model;

import java.nio.charset.StandardCharsets;

/**
 * @author hanger
 * 2020-01-23 16:49
 */
public enum SignalModel {

    MESSAGE_SOCKET("消息连接"),
    DATA_SOCKET("数据连接"),
    NEW_CONNECTION("新来访"),
    MAPPING_SUCCESS("映射成功"),
    UNAVAILABLE_PORT("无可用端口"),
    MAPPING_BEGINNING("映射开始"),
    COPY_THAT("收到"),
    NEW_MAPPING("请求建立新映射"),
    HEARTBEAT("H");

    private String describe;

    SignalModel(String describe) {
        //交互信息统一用StandardCharsets.UTF_8
        this.describe = new String(describe.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }


    public static SignalModel getEnum(String key) {
        return SignalModel.valueOf(key);
    }


    public String getDescribe() {
        return describe;
    }
}
