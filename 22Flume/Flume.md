# Flume

## 节点规划

|        | flume 采集日志 | flume（消费kafka） |
| ------ | -------------- | ------------------ |
| node01 | *              |                    |
| node02 | *              |                    |
| node03 |                | *                  |
| node04 |                | *                  |

## flume 官方配置指导手册

 http://flume.apache.org/FlumeUserGuide.html

## flume架构图

![](./doc/01.png)

## flume组件

1）Source
（1）Taildir Source相比Exec Source、Spooling Directory Source的优势
TailDir Source：断点续传、多目录。Flume1.6以前需要自己自定义Source记录每次读取文件位置，实现断点续传。
Exec Source可以实时搜集数据，但是在Flume不运行或者Shell命令出错的情况下，数据将会丢失。
Spooling Directory Source监控目录，不支持断点续传。
（2）batchSize大小如何设置？
答：Event 1K左右时，500-1000合适（默认为100）
2）Channel
采用Kafka Channel，省去了Sink，提高了效率



## flume安装

```shell
[root@node01 ~]# tar -zxf apache-flume-1.9.0-bin.tar.gz 
[root@node01 ~]# mv apache-flume-1.9.0-bin /opt/stanlong/
[root@node01 ~]# cd /opt/stanlong/
[root@node01 stanlong]# mv apache-flume-1.9.0-bin/ flume
[root@node01 stanlong]# ll
total 0
drwxr-xr-x  7 root root 187 Jun 19 22:18 flume
drwxr-xr-x 10 root root 161 Jun 11 10:27 hadoop-2.9.2
drwxr-xr-x 10 root root 161 Jun 11 10:13 hadoop-2.9.2-full
drwxr-xr-x  8 root root 172 Jun 14 11:28 hbase
drwxr-xr-x 10 root root 208 Jun 12 03:59 hive
[root@node01 stanlong]# 
```

## flume配置 java环境变量

```shell
[root@node01 conf]# pwd
/opt/stanlong/flume/conf
[root@node01 conf]# ll
total 16
-rw-r--r-- 1 1000 1000 1661 Nov 16  2017 flume-conf.properties.template
-rw-r--r-- 1 1000 1000 1455 Nov 16  2017 flume-env.ps1.template
-rw-r--r-- 1 1000 1000 1568 Aug 30  2018 flume-env.sh.template
-rw-rw-r-- 1 1000 1000 3107 Dec  9  2018 log4j.properties
[root@node01 conf]# cp flume-env.sh.template flume-env.sh
[root@node01 conf]# vi flume-env.sh
export JAVA_HOME=/usr/java/jdk1.8.0_65
```

## 官方案例-监控端口

- 案例需求：使用flume监听一个端口，收集该端口数据，并打印到控制台

- 案例分析：

  - 通过 netcat 工具向本机的44444端口发送数据

- 实现步骤

  - 安装 netcat 工具

  ```shell
  [root@node01 conf]# yum install -y nc
  ```

  - 判断44444端口是否被占用

  ```shell
  [root@node01 conf]# netstat -tunlp | grep 44444
  ```

  - 创建 flume agent 配置文件 flume-netcat-logger.conf

  ```shell
  [root@node01 flume]# mkdir job
  [root@node01 flume]# cd job/
  [root@node01 job]# vi flume-netcat-logger.conf
  
  # Name the components on this agent
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  # Describe/configure the source
  a1.sources.r1.type = netcat
  a1.sources.r1.bind = localhost
  a1.sources.r1.port = 44444
  
  # Describe the sink
  a1.sinks.k1.type = logger
  
  # Use a channel which buffers events in memory
  a1.channels.c1.type = memory
  a1.channels.c1.capacity = 1000
  a1.channels.c1.transactionCapacity = 100
  
  # Bind the source and sink to the channel
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

  配置文件解析

  ```shell
  # Name the components on this agent
  a1.sources = r1          a1:表示agent的名称, r1:表示a1的输入源
  a1.sinks = k1            k1:表示a1的输出目的地
  a1.channels = c1         c1:表示a1的缓冲区
  
  # Describe/configure the source
  a1.sources.r1.type = netcat            表示a1的输入源类型为netcat端口类型
  a1.sources.r1.bind = localhost         表示a1的监听的主机
  a1.sources.r1.port = 44444             表示a1的监听的端口号
  
  # Describe the sink
  a1.sinks.k1.type = logger              表示a1的输出目的地是控制台logger类型
  
  # Use a channel which buffers events in memory
  a1.channels.c1.type = memory              表示a1的channel类型是memory内存型
  a1.channels.c1.capacity = 1000            表示a1的channel总容量1000个event
  a1.channels.c1.transactionCapacity = 100  表示a1的channel传输时收集到了100 条event以后再去提交事务
  
  # Bind the source and sink to the channel
  a1.sources.r1.channels = c1                         表示将r1和c1连接起来
  a1.sinks.k1.channel = c1                            表示将k1和c1连接起来
  ```

  - 开启flume监听端口

  ```shell
  [root@node01 flume]# bin/flume-ng agent --conf conf/ --name a1 --conf-file job/flume-netcat-logger.conf -Dflume.root.logger=INFO,console
  出现从一下几行日志代表flume启动成功
   CHANNEL, name: c1: Successfully registered new MBean.
   Starting Sink k1
   Starting Source r1
   Source starting
   Created serverSocket:sun.nio.ch.ServerSocketChannelImpl[/127.0.0.1:44444]
  ```

  - 重新开一个窗口，使用telnet向44444端口发送数据

  ```shell
  [root@node01 ~]# nc localhost 44444
  Hello Flume
  OK
  
  flume会接收到数据
  Event: { headers:{} body: 48 65 6C 6C 6F 20 46 6C 75 6D 65                Hello Flume }
  ```

- 将 flume 分发到 node02，node03， node04 上



