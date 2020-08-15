package com.aFeng;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.MessageException;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConsumerApplication {

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


    @Test
    public void testJSONCvt(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectA = new JSONObject();
        JSONObject jsonObjectB = new JSONObject();
        JSONObject jsonObjectC = new JSONObject();
        jsonObject.put("A",jsonObjectA);
        jsonObjectA.put("B",jsonObjectB);
        jsonObjectB.put("C",jsonObjectC);
        jsonObjectC.put("key","Surprise Mother Fucker");

        //key的排序非常讲究,最后取得的一定是个JSONObject,如果是其他类型也会报错
        String[] keys = {"A","B","C"};
        List<String> arrList = new ArrayList<> (Arrays.asList(keys));

        /**
         * 最后你会获得一个jsonObject,再做相应的处理
         * 比如最后一层是
         * {
         *    status:1,
         *    code:200,
         *    users:[
         *          {
         *              name:阿锋,
         *              age:23
         *          },{
         *              name:张三,
         *              age:25
         *          }
         *    ]
         * }
         * 那你就最多取到这一层再判断有没有users了,到了真正的数据就不要再取了,因为可能是一个数组或者其他的什么鬼,getJsonObject会报错的
         */
        JSONObject o = convertSomethingIGuess(jsonObject, arrList);
        for (String s : o.keySet()) {
            System.out.println(s+":\t"+o.get(s));
        }
    }
}
