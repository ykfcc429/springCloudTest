package com.aFeng;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConsumerApplication {



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
    }
}
