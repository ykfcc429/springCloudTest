package com.aFeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/")
public class ApiController {

    @RequestMapping("/buy")
    @ResponseBody
    public synchronized String buy(){
        Jedis jedis = new Jedis();
        String mouse = jedis.get("mouse");
        String sales_ = jedis.get("salesVolume");
        Long result = 0L,sales = 0L;
        if(Integer.valueOf(mouse).equals(0)){
            return "已经卖完了!";
        }
        result = jedis.decr("mouse");
        sales = jedis.incr("salesVolume");
        return "you got No."+result+" mouse! your num is "+sales;
    }
}
