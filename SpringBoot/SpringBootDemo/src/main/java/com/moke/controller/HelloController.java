package com.moke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @RequestMapping("/test")
    public String testThymeleaf(Map<String,Object> map){
        map.put("user","moke");
        return "test";//test.html
    }

//    @RequestMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }
}
