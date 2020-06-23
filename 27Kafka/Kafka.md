# Kafka

## 节点规划

|       | node01 | node02 | node03 | node04 |
| ----- | ------ | ------ | ------ | ------ |
| kafka | *      | *      | *      | *      |

## Kafka架构

![](./doc/01.png)

![](./doc/02.png)

- Producer ：消息生产者，就是向 kafka broker 发消息的客户端；
- Consumer ：消息消费者，向 kafka broker 取消息的客户端；
- Topic ：可以理解为一个队列；
- Consumer Group （CG）：这是kafka用来实现一个topic消息的广播（发给所有的consumer）
  和单播（发给任意一个 consumer）的手段。一个 topic 可以有多个 CG。topic 的消息会复制
  （不是真的复制，是概念上的）到所有的 CG，但每个 partion 只会把消息发给该 CG 中的一
  个 consumer。如果需要实现广播，只要每个 consumer 有一个独立的 CG 就可以了。要实现
  单播只要所有的 consumer 在同一个 CG。用 CG 还可以将 consumer 进行自由的分组而不需
  要多次发送消息到不同的 topic；
- Broker ：一台 kafka 服务器就是一个 broker。一个集群由多个 broker 组成。一个 broker
  可以容纳多个 topic；
- Partition：为了实现扩展性，一个非常大的 topic 可以分布到多个 broker（即服务器）上，
  一个 topic 可以分为多个 partition，每个 partition 是一个有序的队列。partition 中的每条消息
  都会被分配一个有序的 id（offset）。kafka 只保证按一个 partition 中的顺序将消息发给
  consumer，不保证一个 topic 的整体（多个 partition 间）的顺序；
- Offset：kafka 的存储文件都是按照 offset.kafka 来命名，用 offset 做名字的好处是方便查
  找。例如你想找位于 2049 的位置，只要找到 2048.kafka 的文件即可。当然 the first offset 就
  是 00000000000.kafka。

## kafka 安装

- 上传kafka并重命名

```shell
[root@node01 ~]# tar -zxf kafka_2.13-2.5.0.tgz 
[root@node01 ~]# mv kafka_2.13-2.5.0 /opt/stanlong/
[root@node01 ~]# cd /opt/stanlong/
[root@node01 stanlong]# mv kafka_2.13-2.5.0/ kafka
[root@node01 stanlong]# ll
total 0
drwxr-xr-x  9 root root 210 Jun 20 11:12 flume
drwxr-xr-x 10 root root 161 Jun 11 10:27 hadoop-2.9.2
drwxr-xr-x 10 root root 161 Jun 11 10:13 hadoop-2.9.2-full
drwxr-xr-x  8 root root 172 Jun 14 11:28 hbase
drwxr-xr-x 10 root root 208 Jun 12 03:59 hive
drwxr-xr-x  6 root root  89 Apr  7 21:18 kafka
[root@node01 stanlong]# 
```

- 修改配置文件

```shell
[root@node01 config]# ll
total 72
-rw-r--r-- 1 root root  906 Apr  7 21:13 connect-console-sink.properties
-rw-r--r-- 1 root root  909 Apr  7 21:13 connect-console-source.properties
-rw-r--r-- 1 root root 5321 Apr  7 21:13 connect-distributed.properties
-rw-r--r-- 1 root root  883 Apr  7 21:13 connect-file-sink.properties
-rw-r--r-- 1 root root  881 Apr  7 21:13 connect-file-source.properties
-rw-r--r-- 1 root root 2247 Apr  7 21:13 connect-log4j.properties
-rw-r--r-- 1 root root 2540 Apr  7 21:13 connect-mirror-maker.properties
-rw-r--r-- 1 root root 2262 Apr  7 21:13 connect-standalone.properties
-rw-r--r-- 1 root root 1221 Apr  7 21:13 consumer.properties
-rw-r--r-- 1 root root 4675 Apr  7 21:13 log4j.properties
-rw-r--r-- 1 root root 1925 Apr  7 21:13 producer.properties
-rw-r--r-- 1 root root 6849 Apr  7 21:13 server.properties
-rw-r--r-- 1 root root 1032 Apr  7 21:13 tools-log4j.properties
-rw-r--r-- 1 root root 1169 Apr  7 21:13 trogdor.conf
-rw-r--r-- 1 root root 1205 Apr  7 21:13 zookeeper.properties
[root@node01 config]# vi server.properties 
20 # The id of the broker. This must be set to a unique integer for each broker.
21 broker.id=1 # broker.id 是唯一的整数，集群里每台机器都不一样

59 # A comma separated list of directories under which to store log files
60 log.dirs=/opt/stanlong/kafka/logs # 这里的日志目录该成自己定义的

123 zookeeper.connect=node02:2181,node03:2181,node04:2181
```

- 分发kafka到node02，node03，node04上去

```shell
[root@node01 stanlong]# xsync.sh kafka/
```

- 到node02，node03，node04上去，改 server.properties 里broker.id的值分别为 2，3，4

## 启动kafka

```shell
kafka依赖zookeeper，需要先启动 zookeeper
[root@node01 config]# zk.sh start
[root@node01 kafka]# bin/kafka-server-start.sh -daemon config/server.properties 
[root@node01 kafka]# jps
9092 Jps
9054 Kafka
[root@node01 kafka]# 
```

## kafka 群启脚本

```shell
#!/bin/bash

case $1 in
"start"){
	for i in node01 node02 node03 node04
	do
		echo "***********$i***********"
		ssh $i "/opt/stanlong/kafka/bin/kafka-server-start.sh -daemon /opt/stanlong/kafka/config/server.properties"
	done
};;
"stop"){
	for i in node01 node02 node03 node04
	do
		echo "***********$i***********"
		ssh $i "/opt/stanlong/kafka/bin/kafka-server-stop.sh"
	done
};;
esac
```

## kafka 常用命令

- 创建topic

```shell
[root@node01 kafka]# bin/kafka-topics.sh --zookeeper node02:2181 --create --replication-factor 3 --partitions 1 --topic first-topic
Created topic first-topic.
```

- 查看当前服务器中的所有 topic

```shell
[root@node01 kafka]# bin/kafka-topics.sh --zookeeper node02:2181 --list
first
first-topic
[root@node01 kafka]# 
```

- 删除topic

```shell
[root@node01 kafka]# bin/kafka-topics.sh --zookeeper node02:2181 --delete --topic first
Topic first is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.
[root@node01 kafka]# 
```

需要 server.properties 中设置 delete.topic.enable=true 否则只是标记删除或者直接重启

- 查看某个 Topic 的详情

```shell
[root@node01 kafka]# bin/kafka-topics.sh --zookeeper node02:2181 --describe --topic first-topic
Topic: first-topic	PartitionCount: 1	ReplicationFactor: 3	Configs: 
	Topic: first-topic	Partition: 0	Leader: 2	Replicas: 2,1,3	Isr: 2,1,3
[root@node01 kafka]# 
```

- 生产者发送消息

```shell
[root@node01 kafka]# bin/kafka-console-producer.sh --broker-list node02:9092 --topic first-topic
>Hello
>
```

- 消费者消费消息

```shell
[root@node02 kafka]# bin/kafka-console-consumer.sh --bootstrap-server node02:9092 --topic first-topic
Hello
```

# kafka manager

## 安装kafka manager

```shell
[root@node01 ~]# unzip kafka-manager-1.3.3.22.zip 
[root@node01 ~]# mv kafka-manager-1.3.3.22 /opt/stanlong/
[root@node01 ~]# cd /opt/stanlong/
[root@node01 stanlong]# mv kafka-manager-1.3.3.22/ kafka-manager
```

## 配置 application.conf

```shell
[root@node01 conf]# pwd
/opt/stanlong/kafka-manager/conf
[root@node01 conf]# ll
total 24
-rw-r--r-- 1 root root 1600 Dec 22  2018 application.conf
-rw-r--r-- 1 root root  184 Dec 22  2018 consumer.properties
-rw-r--r-- 1 root root 2108 Dec 22  2018 logback.xml
-rw-r--r-- 1 root root 1367 Dec 22  2018 logger.xml
-rw-r--r-- 1 root root 7423 Dec 22  2018 routes
[root@node01 conf]# vi application.conf 
24 kafka-manager.zkhosts="node02:2181,node03:2181,node04:2181"
```

## 启动 kafkamanager

```shell
[root@node01 kafka-manager]# mkdir logs
[root@node01 kafka-manager]# nohup bin/kafka-manager -Dhttp.port=7456 >/opt/stanlong/kafka-manager/logs/start.log 2>&1 &
```

![](./doc/03.png)

点保存之后看到的界面

![](./doc/04.png)

点 clusters

![](./doc/05.png)

## kafka manager 的使用

https://blog.csdn.net/u011089412/article/details/87895652

# kafka 压测

1. Kafka压测

   用Kafka官方自带的脚本，对Kafka进行压测。Kafka压测时，可以查看到哪个地方出现了瓶颈（CPU，内存，网络IO）。一般都是网络IO达到瓶颈。 

   kafka-consumer-perf-test.sh

   kafka-producer-perf-test.sh

2. Kafka Producer压力测试

   （1）在/opt/module/kafka/bin目录下面有这两个文件。我们来测试一下

   ```shell
   [root@node01 kafka]# bin/kafka-producer-perf-test.sh --topic test --record-size 100 --num-records 100000 --throughput 1000 --producer-props bootstrap.servers=node02:9092,node03:9092,node04:9092
   ```

   说明：record-size是一条信息有多大，单位是字节。num-records是总共发送多少条信息。throughput 是每秒多少条信息。

   （2）Kafka会打印下面的信息

   ```shell
   5000 records sent, 999.4 records/sec (0.10 MB/sec), 1.9 ms avg latency, 254.0 max latency.
   
   5002 records sent, 1000.4 records/sec (0.10 MB/sec), 0.7 ms avg latency, 12.0 max latency.
   
   5001 records sent, 1000.0 records/sec (0.10 MB/sec), 0.8 ms avg latency, 4.0 max latency.
   
   5000 records sent, 1000.0 records/sec (0.10 MB/sec), 0.7 ms avg latency, 3.0 max latency.
   
   5000 records sent, 1000.0 records/sec (0.10 MB/sec), 0.8 ms avg latency, 5.0 max latency.
   ```

   参数解析：本例中一共写入10w条消息，每秒向Kafka写入了0.10MB的数据，平均是1000条消息/秒，每次写入的平均延迟为0.8毫秒，最大的延迟为254毫秒。

   3）Kafka Consumer压力测试

   Consumer的测试，如果这四个指标（IO，CPU，内存，网络）都不能改变，考虑增加分区数来提升性能。

   ```shell
   [root@node01 kafka]# bin/kafka-consumer-perf-test.sh --bootstrap-server node02:9092 --topic test --fetch-size 10000 --messages 10000000 --threads 1
   ```

   参数说明：

   --zookeeper 指定zookeeper的链接信息

   --topic 指定topic的名称

   --fetch-size 指定每次fetch的数据的大小

   --messages 总共要消费的消息个数

   测试结果说明：

   ```shell
   start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec
   
   2019-02-19 20:29:07:566, 2019-02-19 20:29:12:170, 9.5368, 2.0714, 100010, 21722.4153
   ```

   开始测试时间，测试结束数据，最大吞吐率9.5368MB/s，平均每秒消费2.0714MB/s，最大每秒消费100010条，平均每秒消费21722.4153条。

# Kafka机器数量计算

Kafka机器数量（经验公式）=2*（峰值生产速度*副本数/100）+1

先要预估一天大概产生多少数据，然后用Kafka自带的生产压测（只测试Kafka的写入速度，保证数据不积压），计算出峰值生产速度。再根据设定的副本数，就能预估出需要部署Kafka的数量。

比如我们采用压力测试测出写入的速度是10M/s一台，峰值的业务数据的速度是50M/s。副本数为2。

Kafka机器数量=2*（50*2/100）+ 1=3台