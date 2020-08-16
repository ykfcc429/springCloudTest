package com.consumer81.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.MessageException;
import java.util.List;
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

    /**
     * 解决JSON俄罗斯套娃问题
     * @param jsonObject 你需要处理的JsonObject
     * @param keys 你需要取的key,请按顺序放
     * @return keys最右边的key的值
     */
    public static JSONObject convertSomethingIGuess(JSONObject jsonObject, List<String> keys){
        if(jsonObject==null){
            throw new MessageException("就这?");
        }
        if(keys.isEmpty()){
            throw new MessageException("不懂你什么意思");
        }
        if(jsonObject.containsKey(keys.get(0))){
            JSONObject jsonObject1 = jsonObject.getJSONObject(keys.get(0));
            if(jsonObject1==null){
                throw new MessageException(keys.get(0)+" 的值为空了,无法继续获取值了");
            }
            if(keys.size()==1){
                return jsonObject1;
            }
            keys.remove(0);
            return convertSomethingIGuess(jsonObject1,keys);
        }
        throw new MessageException("很抱歉,看起来你没有 "+keys.get(0));
    }

}
