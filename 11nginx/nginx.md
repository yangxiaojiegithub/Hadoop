# nginx

## nginx 配置文件
```
user  root;
worker_processes  1; # 值越大，可以支持的并发处理量也越多

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;
    keepalive_timeout  65;

    server {
        listen       80;
        server_name  localhost;

        # 用户请求/update_content?id=1, 将该请求交给 /root/lua/update_content.lua 处理
        location /update_content {
           # content_by_lua_file /root/lua/update_content.lua;
            root   html;
            index  index.html index.htm;
        }
    }
}

```
+ 全局块
	从配置文件开始到events块之间的内容，主要会设置一些影响nginx服务器整体运行的配置指令，主要包括配置运行nginx服务器的用户（组）、允许生成的 worker process数，进程pid存放路径、日志文件存放路径和类型以及配置文件的引入等
+ events 块
	events 块涉及的指令主要影响nginx服务器与用户的网络连接，常用的设置包括是否开启多对 work process下的网络连接时进行序列化，是否允许同时接收多个网络连接，选择哪种事件驱动模型来处理连接请求，每个work process可以同时支持的最大连接数等
+ http 块
	- http全局块
		http全局块配置的指令包括文件引入，MIME-TYPE定义，日志自定义，连接超时时间，单连接请求数上限等
	- server 块
		每一个server相当于一个虚拟主机
        1. 全局server块
        	虚拟主机监听端口，名称及ip配置
        2. location块
        	一个server块可以配置多个location块，这块的主要作用是基于nginx服务器收到的请求字符串进行匹配，对特定的请求进行处理。地址定向，数据缓存和应答控制等功能，还可以配置第三方模块

## location的配置规则
```
=  开头表示精确匹配

^~ 开头表示uri以某个常规字符串开头，理解为匹配 url路径即可。nginx不对url做编码，因此请求为/static/20%/aa，可以被规则^~ /static/ /aa匹配到（注意是空格）。

~  开头表示区分大小写的正则匹配

~*  开头表示不区分大小写的正则匹配

!~和!~*分别为区分大小写不匹配及不区分大小写不匹配 的正则

/ 通用匹配，任何请求都会匹配到。
```
