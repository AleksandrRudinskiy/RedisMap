package org.redis;

import org.redis.service.MapIntoRedis;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        MapIntoRedis mapIntoRedis = new MapIntoRedis();
        mapIntoRedis.put("k1", "val1");
        mapIntoRedis.put("k2", "val2");
        mapIntoRedis.put("k3", "val3");
        System.out.println(mapIntoRedis.containsKey("k1"));
        System.out.println(mapIntoRedis.containsKey("k10"));

//        String value = mapIntoRedis.get("k1");
//        System.out.println("Value for key = k1 : " + value);
//        System.out.println("Значения: " + mapIntoRedis.values());
//        Set<String> keys = mapIntoRedis.keySet();
//        System.out.println("All the keys : " + keys);
//        System.out.println("Map is contain the value = \"val2\" : " + mapIntoRedis.containsValue("val2"));
//        System.out.println("Map is contain the value = \"val45\" : " + mapIntoRedis.containsValue("val45"));
//        //Очистка мапы
//        mapIntoRedis.clear();
//        System.out.println("All the keys after clear() : " + mapIntoRedis.keySet());
    }
}