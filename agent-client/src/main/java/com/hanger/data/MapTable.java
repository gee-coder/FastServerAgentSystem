package com.hanger.data;

import com.hanger.thread.ClientAgent;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanger
 * 2020-01-16 17:54
 */
public class MapTable {
    //映射表：存放所有客户端连接对应端口的线程安全的哈希表
    private ConcurrentHashMap<ClientAgent, Date> mapTable = new ConcurrentHashMap<>();
    private long mapInvalidTime;


    public MapTable(long mapInvalidTime) {
        this.mapInvalidTime = mapInvalidTime;
    }


    //添加新映射
    public void putMap(ClientAgent serverAgent) {
        mapTable.put(serverAgent, new Date());
    }


    //返回当前映射表的所有Entry
    public Set<Map.Entry<ClientAgent, Date>> entrySet() {
        return mapTable.entrySet();
    }


    //判断当前映射记录有没有失效，如果失效就返回失效的端口数组并删除映射，否则返回null
    public void checkEntry(Map.Entry<ClientAgent, Date> entry) {
        Date value = entry.getValue();
        //计算时间差，注意getTime()返回的是毫秒
        long time = new Date().getTime() - value.getTime();
        //如果达到失效时间就进行回收操作
//        System.out.println("时间差" + time);
        if (time >= mapInvalidTime) {
            System.out.println("发现失效映射记录");
            del(entry);
        }
    }


    //删除映射表指定映射
    private void del(Map.Entry<ClientAgent, Date> entry) {
        ClientAgent key = entry.getKey();
        Date value = entry.getValue();
        key.close();
        if (mapTable.remove(key, value)) {
            System.out.println("映射" + key + " 删除成功");
        } else {
            System.out.println("映射" + key + " 删除失败");
        }
    }


    //根据key删除
    public void delByKey(String key) {
        //遍历映射池当前的Entry集合
        for (Map.Entry<ClientAgent, Date> next : mapTable.entrySet()) {
            ClientAgent clientAgent = next.getKey();
            if (key.equals(clientAgent.getKey())) {
                del(next);
            }
        }
    }


    //删除映射表所有映射
    public void flushTable() {
        //遍历映射池当前的Entry集合
        for (Map.Entry<ClientAgent, Date> next : mapTable.entrySet()) {
            del(next);
        }
        mapInvalidTime = -1;
    }

    //映射表是否被删除
    public Boolean isCleared() {
        return mapInvalidTime == -1;
    }

    @Override
    public String toString() {
        return "当前所有映射记录如下：\n" + mapTable;
    }
}
