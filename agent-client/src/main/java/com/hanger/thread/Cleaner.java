package com.hanger.thread;

import com.hanger.data.MapTable;
import java.util.Date;
import java.util.Map;

/**
 * @author hanger
 * 2020-01-17 23:21
 */
public class Cleaner implements Runnable {
    //映射表
    private MapTable mapTable;
    //检查映射表的周期(毫秒)
    private long cleanCycle;

    Cleaner(MapTable mapTable, long cleanCycle) {
        this.mapTable = mapTable;
        this.cleanCycle = cleanCycle;
    }

    @Override
    public void run() {
        while (!mapTable.isCleared()) {
            //遍历映射池当前的Entry集合
            for (Map.Entry<ClientAgent, Date> next : mapTable.entrySet()) {
                mapTable.checkEntry(next);
            }
            //休眠开始
            try {
                Thread.sleep(cleanCycle);
            } catch (InterruptedException e) {
                System.out.println("清洁者休眠失败");
                e.printStackTrace();
            }
        }
    }

}
