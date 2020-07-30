package com.aFeng.util;

import java.util.Map;

public class MapUtil {

    /**
     * 事实上我不太明白为什么这样做,因为看起来似乎毫无意义,把String转成String,但其实是必然的
     * @param map 需要转换的map
     */
    public static void convertValueToString(Map<String,String> map){
        for(String key:map.keySet()){
            map.putIfAbsent(key,"");
            map.put(key, String.valueOf(map.get(key)));
        }
    }

}
