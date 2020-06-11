# Hadoop完全分布式搭建

- **参考伪分布式检查系统环境**
- 集群规划

|        | NN   | SNN  | DN   |      |
| ------ | ---- | ---- | ---- | ---- |
| node01 | *    |      |      |      |
| node02 |      | *    | *    |      |
| node03 |      |      | *    |      |
| node04 |      |      | *    |      |

- 在node02，node03，node04节点上准备目录

```shell
[root@node03 ~]# mkdir -p /opt/stanlong/
```

- core-site.xml配置

```xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://node01:9000</value> node01作为namenode
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/var/data/hadoop/full</value> 数据存路径
    </property>
</configuration>
```

- slaves配置，在文件中添加datanode节点

```shell
node02
node03
node04
```

- hdfs-site.xml配置，现在有三个节点，副本数改成2

```xml
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>2</value>
    </property>
</configuration>
```

- 把 hadoop 配置分发到 node02，node03，node04 上去

```shell
[root@node01 ~]# scp -r /opt/stanlong/hadoop-2.9.2/ node02:/opt/stanlong/
```

- node01 (namenode)格式化

```shell
[root@node01 hadoop]# hdfs namenode -format
注意看这句话，有这句话才算格式化成功
20/06/11 15:06:40 INFO common.Storage: Storage directory /var/data/hadoop/full/dfs/name has been successfully formatted.
```

- 启动

```shell
[root@node01 hadoop]# start-dfs.sh 
Starting namenodes on [node01]
node01: starting namenode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-namenode-node01.out
node03: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node03.out
node04: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node04.out
node02: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node02.out
Starting secondary namenodes [0.0.0.0]
0.0.0.0: starting secondarynamenode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-secondarynamenode-node01.out
[root@node01 hadoop]# 
```

![](./doc/01.png)

![](./doc/02.png)



