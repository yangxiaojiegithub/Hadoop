# 1、基本语法
k:(空格)v : 表示一对键值对（空格必须有）
以空格缩进表示层级关系，只要是左对齐的一列数据，都是同一个层级
~~~
server:
 port: 8081
 path: /hello
~~~
属性和值也是大小写敏感
# 2、值的写法
#### 字面量：普通的值（数字，字符串，布尔）
+ k: v :字面量直接来写，字符串默认不加上单引号或者双引号
 - "":双引号不会转义字符串里面的特殊字符，特殊字符会作为本身想表示的意思。
 ~~~
    例如：name: "zhangsan\n lisi" : 输出 zhangsan 换行 lisi
~~~   
 - '':单引号 会转义特殊字符，特殊字符只是一个普通的字符串数据。
 ~~~
    例如：name: 'zhangsan\n lisi' : 输出 zhangsan\n lisi
 ~~~

#### 对象、map(属性和值，键值对)
k:v : 在下一行来写对象的属性和值的关系
 对象还是k：v的方式
 ~~~
 friends:
    lastName: zhangsan
    age: 20
 ~~~
 行内写法:
 ~~~
 friends: {lastName: zhangsan, age: 18}
 ~~~
#### 数组(List、Set)
 ~~~
 pets:
  - cat
  - dog
  - pig
 ~~~
 行内写法:
 ~~~
 pets: [cat,dog,pig]
 ~~~
# 3、配置文件值注入
 配置文件
 ~~~
 person:
   lastName: zhangsan
   age: 18
   boss: false
   birth: 2019/01/28
   maps: {k1: v1, k2: v2}
   lists:
     - lisi
     - zhaoliu
   dog:
     name: 豆豆
     age: 2
 ~~~
 javaBean:
 ~~~
 @Component
 @ConfigurationProperties(prefix = "person")
 public class Person {
     private String lastName;
     private Integer age;
     private Boolean boss;
     private Date birth;
 
     private Map<String, Object> maps;
     private List<Object> lists;
     private Dog dog;
 ~~~
 导入配置文件处理器，以后编写配置文件会有提示
 ~~~
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-configuration-processor</artifactId>
     <optional>true</optional>
 </dependency>
 ~~~
 # 4、两个注解 @PropertySource 和 @ImportResource
 @PropertySource 加载指定的配置文件
 配置文件  person.properties
 ~~~
 person.last-name=李四
 person.age=18
 person.birth=2019/01/28
 person.boss=false
 person.maps.k1=v1
 person.maps.k2=v2
 person.lists=a,b,c
 person.dog.name=豆豆
 person.dog.age=8
 ~~~
 注解的使用
 ~~~
 @Component
 @ConfigurationProperties(prefix = "person")
 @PropertySource(value = {"classpath:person.properties"})
 public class Person {
     private String lastName;
     private Integer age;
     private Boolean boss;
     private Date birth;
 
     private Map<String, Object> maps;
     private List<Object> lists;
     private Dog dog;
     省略getter、setter、toString 方法
 }
 ~~~
 
 @ImportResource,导入spring的配置文件，让配置文件里面的内容生效
 ~~~
 @ImportResource(locations = {"classpath:beans.xml"})
 @SpringBootApplication
 public class DemoApplication {
 
     public static void main(String[] args) {
         SpringApplication.run(DemoApplication.class, args);
     }
 
 }
 ~~~
 springboot推荐给容器中添加组件的方式
 1、 配置类===配置文件
 2、 使用@Bean给容器中添加组件
 ~~~
 @Configuration
 public class MyAppConfig {
 
     @Bean // 将方法的返回值添加到容器中：容器中这个组件的默认id就是方法名
     public HelloService helloService(){
         System.out.println("配置类bean给容器中添加组件了...");
         return new HelloService();
 
     }
 }
 ~~~
 # 5、配置文件占位符
 占位符获取之前配置的值，如果没有可以使用 : 指定默认值
 ~~~
 person.last-name=张三${random.uuid}
 person.age=${random.int}
 person.birth=2019/01/28
 person.boss=false
 person.maps.k1=v1
 person.maps.k2=v2
 person.lists=a,b,c
 person.dog.name=${person.last-name}_dog
 person.dog.age=${person.hello:10}
 
 ~~~