package com.stanlong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 矢量
 * @date 2020/1/27-15:11
 */
@SpringBootApplication //标注一个主程序类，说明这是一个SpringBoot应用
public class HelloWorldMainApplication {
    public static void main(String[] args) {
        // SpringBoot 应用启动起来
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}
