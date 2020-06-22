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

