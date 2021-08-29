package com.example.demo.com.stanlong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 矢量
 * @date 2020/4/25-20:17
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping("/success")
    public String success(Map<String, Object> map){
        // classpath:/templates/success.html
        map.put("hello", "你好"); //查出一些数据在页面中展示
        map.put("uhello","<h1>你好</h1>");
        map.put("user", Arrays.asList("zhangsan", "lisi", "wangwu"));
        return "success";
    }
}
