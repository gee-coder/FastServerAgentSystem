package com.hanger.data;

import com.hanger.thread.ServerAgent;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanger
 * 2020-01-29 13:39
 */
public class MapTable {
    //映射表：存放所有客户端连接对应端口的线程安全的哈希表
    private ConcurrentHashMap<ServerAgent, Date> mapTable= new ConcurrentHashMap<>();
    private long mapInvalidTime;


    public MapTable(long mapInvalidTime) {
        this.mapInvalidTime = mapInvalidTime;
    }


    //添加新映射
    public void putMap(ServerAgent serverAgent) {
        mapTable.put(serverAgent, new Date());
    }


    //返回当前映射表的所有Entry
    public Set<Map.Entry<ServerAgent, Date>> entrySet() {
        return mapTable.entrySet();
    }


    //判断当前映射记录有没有失效，如果失效就返回失效的端口数组并删除映射，否则返回null
    public Integer[] checkEntry(Map.Entry<ServerAgent, Date> entry) {
        ServerAgent key = entry.getKey();
        Date value = entry.getValue();
        //计算时间差，注意getTime()返回的是毫秒
        long time = new Date().getTime() - value.getTime();
        //如果达到失效时间就进行回收操作
//        System.out.println("时间差" + time);
        if (time >= mapInvalidTime) {
            System.out.println("发现失效映射记录");
            key.close();
            if (mapTable.remove(key, value)) {
                System.out.println("映射" + key + " 删除成功");
                Integer[] ports = new Integer[2];
                ports[0] = key.getServerAgentPort();
                ports[1] = key.getMapperPort();
                return ports;
            } else {
                System.out.println("映射" + key + " 删除失败");
                return null;
            }
        } else {
//            System.out.println("未失效");
            return null;
        }
    }

    @Override
    public String toString() {
        return "当前所有映射记录如下：\n" + mapTable;
    }
}


