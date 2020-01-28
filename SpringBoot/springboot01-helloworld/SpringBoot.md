#环境约束
+ jdk1.8
+ maven 3.x
+ IntelliJ IDEA 2018.3.5 x64
+ SpringBoot 1.5.9.RELEASE

# 第一个maven工程

1、新建maven工程
2、导入maven依赖
```
<!-- 指定Spring Boot的版本 2.0.4.RELEASE -->
<!-- spring-boot-starter-parent springboot 版本仲裁中心 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.4.RELEASE</version>
</parent>

<dependencies>
   <!-- 导入Spirng Boot  web 所需的jar包 -->
   <!-- spring-boot-starter springboot场景启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
3、编写一个主程序，启动SpringBoot应用
```
@SpringBootApplication //标注一个主程序类，说明这是一个SpringBoot应用
public class HelloWorldMainApplication {
    public static void main(String[] args) {
        // SpringBoot 应用启动起来
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}

```

4、编写相关的业务逻辑
```
@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }
}

```
5、 运行主程序

6、简化部署
```
<!-- 这个插件，可以将应用打包成一个可执行的jar包；-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

```
插件将这个应用打成jar包，直接用 java -jar的命令进行执行
