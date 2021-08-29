package com.example.demo.config;

import com.example.demo.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 矢量
 * @date 2020/1/28-19:46
 */
@Configuration
public class MyAppConfig {

    @Bean // 将方法的返回值添加到容器中：容器中这个组件的默认id就是方法名
    public HelloService helloService(){
        System.out.println("配置类bean给容器中添加组件了...");
        return new HelloService();

    }
}
