package com.aFeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController("/")
public class ApiController {

    @GetMapping("/buy")
    @ResponseBody
    public synchronized String buy(){
        Jedis jedis = new Jedis();
        String mouse = jedis.get("mouse");
        if(Integer.valueOf(mouse).equals(0)){
            return "已经卖完啦!";
        }
        jedis.decr("mouse");
        jedis.incr("salesVolume");
        return "<body style='background-color: black;font-size: 20;'>" +
                "<div style='height: 50%;padding-top: 40%;padding-left: 19%;padding-right: 8%;color: orange;'>" +
                "<h1 style='\n" +
                "    color: white;\n" +
                "    width: auto;\n" +
                "    display: inline;\n'>Awesome! </h1><h1 style='\n" +
                "    background-color: orange;\n" +
                "    color: black;\n" +
                "    width: auto;\n" +
                "    display: inline-block;\n'>" +
                "This is f**king crazy!</h1></div><body>";
    }
}
