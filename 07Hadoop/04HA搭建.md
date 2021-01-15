# Hadoop HA搭建

## 节点规划

|                               | NN-1 | NN-2 | DN   | ZK   | ZKFC | JNN  |
| ----------------------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| <font color=red>node01</font> | *    |      |      |      | *    | *    |
| <font color=red>node02</font> |      | *    | *    | *    | *    | *    |
| node03                        |      |      | *    | *    |      | *    |
| node04                        |      |      | *    | *    |      |      |

## 配置 zookeeeper集群

- 上传 zookeeper 包到 node2节点
- 解压并移到压缩包到 /opt/stanlong目录下

```shell
[root@node02 ~]# ll
total 192168
-rw-------. 1 root root      1551 Jun  8 14:55 anaconda-ks.cfg
-rw-r--r--  1 root root 160102255 Jun 10 20:32 jdk-8u65-linux-x64.rpm
-rw-r--r--  1 root root  36668066 Mar 27  2018 zookeeper-3.4.11.tar.gz
[root@node02 ~]# tar -zxf zookeeper-3.4.11.tar.gz 
[root@node02 ~]# ll
total 192172
-rw-------.  1 root root       1551 Jun  8 14:55 anaconda-ks.cfg
-rw-r--r--   1 root root  160102255 Jun 10 20:32 jdk-8u65-linux-x64.rpm
drwxr-xr-x  10  502 games      4096 Nov  1  2017 zookeeper-3.4.11
-rw-r--r--   1 root root   36668066 Mar 27  2018 zookeeper-3.4.11.tar.gz
[root@node02 ~]# mv zookeeper-3.4.11 /opt/stanlong/
```

- 配置zookeeper环境变量

```shell
[root@node02 zookeeper-3.4.11]# vi /etc/profile
export ZOOKEEPER_HOME=/opt/stanlong/zookeeper-3.4.11
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$ZOOKEEPER_HOME/bin

[root@node02 zookeeper-3.4.11]# source /etc/profile
```

- 修改zookeeper的配置

```shell
[root@node02 conf]# pwd
/opt/stanlong/zookeeper-3.4.11/conf
[root@node02 conf]# ll
total 12
-rw-r--r-- 1 502 games  535 Nov  1  2017 configuration.xsl
-rw-r--r-- 1 502 games 2161 Nov  1  2017 log4j.properties
-rw-r--r-- 1 502 games  922 Nov  1  2017 zoo_sample.cfg
[root@node02 conf]# cp zoo_sample.cfg zoo.cfg 
[root@node02 conf]# vi zoo.cfg 
dataDir=/var/data/hadoop/zookeeper 修改数据存放目录
12 dataDir=/var/data/zookeeper
29 server.1=192.168.235.12:2888:3888 这三行是自己加的
30 server.2=192.168.235.13:2888:3888
31 server.3=192.168.235.14:2888:3888
```

- 创建目录

```shell
[root@node02 hadoop]# mkdir /var/data/hadoop/zookeeper
```

- 创建myid文件追加1

```shell
[root@node02 hadoop]# echo 1 > /var/data/hadoop/zookeeper/myid
```

- 配置完成之后将 zookeeper 分发到 node03 和 node04 上

```shell
[root@node02 stanlong]# scp -r ./zookeeper-3.4.11/ node03:/opt/stanlong/
[root@node02 stanlong]# scp -r ./zookeeper-3.4.11/ node04:/opt/stanlong/
```

- 更改 node03 和 node04 节点的myid

```shell
[root@node03 conf]# echo 2 > /var/data/hadoop/zookeeper/myid
```

```shell
[root@node04 hadoop]# echo 3 > /var/data/hadoop/zookeeper/myid
```

- 在 node03 和 node04 上配置ZOOKEEPER的环境变量

- **测试启动**

在node02上执行启动命令

```shell
[root@node02 ~]# zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /opt/stanlong/zookeeper-3.4.11/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
[root@node02 ~]# jps
8999 QuorumPeerMain
9081 Jps
```

在node03上执行启动命令

```shell
[root@node03 conf]# zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /opt/stanlong/zookeeper-3.4.11/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
[root@node03 conf]# zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /opt/stanlong/zookeeper-3.4.11/bin/../conf/zoo.cfg
Mode: leader
[root@node03 conf]# 
```

在node04上执行启动命令

```shell
[root@node04 hadoop]# zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /opt/stanlong/zookeeper-3.4.11/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
[root@node04 hadoop]# zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /opt/stanlong/zookeeper-3.4.11/bin/../conf/zoo.cfg
Mode: follower
[root@node04 hadoop]# 
```

## 配置node02与node01免密钥（参考 02Linux基本命令/免密钥配置.md）

## 配置zookeeper HA

- 进入node01节点，备份hadoop完全分布式

```shell
[root@node01 stanlong]# cp -r hadoop-2.9.2 hadoop-2.9.2-full
```

- 配置 hdfs-site.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
    <property>
        <name>dfs.replication</name>
        <value>2</value>
    </property>
    <!--第一大步，逻辑到物理的映射 -->
    <!--逻辑名称 -->
    <property>
        <name>dfs.nameservices</name>
        <value>mycluster</value>
    </property>
    
    <!-- mycluster 映射两个物理主机 -->
    <property>
        <name>dfs.ha.namenodes.mycluster</name>
        <value>nn1,nn2</value>
    </property>
    
    <!-- 物理主机对应的物理地址 rpc-address -->
    <property>
        <name>dfs.namenode.rpc-address.mycluster.nn1</name>
        <value>node01:8020</value>
    </property>
    <property>
        <name>dfs.namenode.rpc-address.mycluster.nn2</name>
        <value>node02:8020</value>
    </property>
    
    <!-- 物理主机对应的物理地址 http-address-->
    <property>
        <name>dfs.namenode.http-address.mycluster.nn1</name>
        <value>node01:50070</value>
    </property>
    <property>
        <name>dfs.namenode.http-address.mycluster.nn2</name>
        <value>node02:50070</value>
    </property>
    
    <!--第二大步  JournalNode 配置-->
    <property>
        <name>dfs.namenode.shared.edits.dir</name>
        <value>qjournal://node01:8485;node02:8485;node03:8485/mycluster</value>
    </property>
    
    <property>
        <name>dfs.journalnode.edits.dir</name>
        <value>/var/hadoop/ha/jn</value>
    </property>
    
    <!-- 故障切换的实现和代理 -->
    <property>
        <name>dfs.client.failover.proxy.provider.mycluster</name>
        <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>
    </property>
    <property>
        <name>dfs.ha.fencing.methods</name>
        <value>sshfence</value>
    </property>
    <property>
        <name>dfs.ha.fencing.ssh.private-key-files</name>
        <value>/root/.ssh/id_rsa</value>
    </property>
    <property>
        <name>dfs.ha.automatic-failover.enabled</name>
        <value>true</value>
    </property>
</configuration>
```

- 配置core-site.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://mycluster</value>
    </property>

    <property>
        <name>hadoop.tmp.dir</name>
        <value>/var/data/hadoop/ha</value>
    </property>
    
    <property>
        <name>ha.zookeeper.quorum</name>
        <value>node02:2181,node03:2181,node04:2181</value>
    </property>
</configuration>
```

- 将hdfs-site.xml，core-site.xml 分发到 node02,node03,node04上

```shell
[root@node01 hadoop]# scp hdfs-site.xml core-site.xml node02:`pwd`
[root@node01 hadoop]# scp hdfs-site.xml core-site.xml node03:`pwd`
[root@node01 hadoop]# scp hdfs-site.xml core-site.xml node04:`pwd`
```

- 在node01上启动 journalnode

```shell
[root@node01 .ssh]# hadoop-daemon.sh start journalnode
starting journalnode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-journalnode-node01.out
```

- 在 node02 上启动  journalnode

```shell
[root@node02 .ssh]# hadoop-daemon.sh start journalnode
starting journalnode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-journalnode-node02.out
```

- 在 node03 上启动  journalnode

```shell
[root@node03 ha]# hadoop-daemon.sh start journalnode
starting journalnode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-journalnode-node03.out
```

- 在 node01上格式化 namenode

```shell
[root@node01 .ssh]# hdfs namenode -format
看到 20/06/11 10:45:59 INFO common.Storage: Storage directory /var/data/hadoop/ha/dfs/name has been successfully formatted  说明格式化成功
```

- 在node01上启动namenode

```shell
[root@node01 .ssh]# hadoop-daemon.sh start namenode
starting namenode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-namenode-node01.out
[root@node01 .ssh]# jps
5851 JournalNode
6075 Jps
5998 NameNode
[root@node01 .ssh]# 
```

- 在 node02 上启动 namenode

```shell
[root@node02 .ssh]# hdfs namenode -bootstrapStandby
```

- 在 node01上格式化 zkfc

```shell
[root@node01 .ssh]# hdfs zkfc -formatZK
命令执行完成后注意看  20/06/11 10:51:05 INFO ha.ActiveStandbyElector: Successfully created /hadoop-ha/mycluster in ZK.
```

- 在node04上启动 zookeeper

```shell
[zk: localhost:2181(CONNECTED) 0] ls /
[zookeeper, hadoop-ha]
[zk: localhost:2181(CONNECTED) 1] ls /hadoop-ha
[mycluster]
[zk: localhost:2181(CONNECTED) 3] ls /hadoop-ha/mycluster
[] 这个时候啥都没有
```

- 在 node01上启动 dfs

```shell
[root@node01 .ssh]# start-dfs.sh 
Starting namenodes on [node01 node02]
node01: namenode running as process 5998. Stop it first.
node02: starting namenode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-namenode-node02.out
node03: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node03.out
node04: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node04.out
node02: starting datanode, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-datanode-node02.out
Starting journal nodes [node01 node02 node03]
node01: journalnode running as process 5851. Stop it first.
node03: journalnode running as process 5630. Stop it first.
node02: journalnode running as process 10893. Stop it first.
Starting ZK Failover Controllers on NN hosts [node01 node02]
node01: starting zkfc, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-zkfc-node01.out
node02: starting zkfc, logging to /opt/stanlong/hadoop-2.9.2/logs/hadoop-root-zkfc-node02.out
[root@node01 .ssh]# 
```

- 再到node04上查看 zookeeper 

```shell
[zk: localhost:2181(CONNECTED) 4] ls /hadoop-ha/mycluster
[ActiveBreadCrumb, ActiveStandbyElectorLock]
[zk: localhost:2181(CONNECTED) 5] get /hadoop-ha/mycluster/ActiveStandbyElectorLock

	myclusternn1node01 �>(�>
cZxid = 0x100000007
ctime = Thu Jun 11 18:55:18 EDT 2020
mZxid = 0x100000007
mtime = Thu Jun 11 18:55:18 EDT 2020
pZxid = 0x100000007
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x200003176fd0001
dataLength = 30
numChildren = 0
[zk: localhost:2181(CONNECTED) 6] 
```

可知node01节点抢到了锁

![](./doc/01.png)

![](./doc/02.png)

## 测试(测试结果与预期不符)





## Hadoop-HA启动顺序

先启动zookeeper

zkServer.sh start

启动journalnode

hadoop-daemon.sh start journalnode

启动主namenode

hadoop-daemon.sh start namenode

启动备namenode（第一次需要启动，后面就不需要了）

hdfs namenode -bootstrapStandby

在主节点上启动datanode
start-dfs.sh