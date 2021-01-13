# zookeeper
分布式应用提供协调服务的Apache项目

## 特点
1. zookeeper：一个领导者，多个跟随者组成的集群
2. 集群中只要有半数以上的节点存活，zookeeper集群就能正常服务
3. 全局数据一致，每个server保存一份相同的数据副本，clinet无论连接到哪个server，数据都是一致的
4. 更新请求顺序进行，来自同一个clinet的更新请求按其发送顺序依次进行
5. 数据更新原子性，一次数据要么更新成功，要么更新失败
6. 实时性，在一定时间范围内，clinet能读到最新数据

# 本地模式安装部署
1. 安装前准备
	+ 安装jdk
	+ 上传zookeeper到linux系统
	+ 解压到指定目录

2. 配置修改
	+ 将 zookeeper/conf路径下的 zoo_sample.cfg修改为zoo.cfg
	+ 打开 zoo.cnf 文件， 修改 dataDir 路径
	```
	dataDir=/opt/zookeeper-3.4.11/zkData
	```
3. 在/opt/zookeeper-3.4.11/下创建zkData目录

# 操作zookeeper
1. 启动zookeeper
```shell
[root@gmall bin]# ll
total 36
-rwxr-xr-x 1 502 games  232 Nov  2  2017 README.txt
-rwxr-xr-x 1 502 games 1937 Nov  2  2017 zkCleanup.sh
-rwxr-xr-x 1 502 games 1056 Nov  2  2017 zkCli.cmd
-rwxr-xr-x 1 502 games 1534 Nov  2  2017 zkCli.sh
-rwxr-xr-x 1 502 games 1628 Nov  2  2017 zkEnv.cmd
-rwxr-xr-x 1 502 games 2696 Nov  2  2017 zkEnv.sh
-rwxr-xr-x 1 502 games 1089 Nov  2  2017 zkServer.cmd
-rwxr-xr-x 1 502 games 6773 Nov  2  2017 zkServer.sh
[root@gmall bin]# pwd
/opt/zookeeper-3.4.11/bin
[root@gmall bin]# ./zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper-3.4.11/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
```
2. 查看进程是否启动
```shell
[root@gmall bin]# jps
3203 QuorumPeerMain
3246 Jps
```
3. 查看状态
```shell
[root@gmall bin]# ./zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper-3.4.11/bin/../conf/zoo.cfg
Mode: standalone
```
4. 启动客户端
```shell
[root@gmall bin]# ./zkCli.sh
...
WATCHER::
WatchedEvent state:SyncConnected type:None path:null
[zk: localhost:2181(CONNECTED) 0]
```
5. 创建节点
```shell
[zk: localhost:2181(CONNECTED) 1] get /
cZxid = 0x0
ctime = Thu Jan 01 08:00:00 CST 1970
mZxid = 0x0
mtime = Thu Jan 01 08:00:00 CST 1970
pZxid = 0x0
cversion = -1
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 0
numChildren = 1
[zk: localhost:2181(CONNECTED) 2] create -e /stanlong 123456 --根节点下创建一个stanlong节点，值为123456
Created /stanlong
[zk: localhost:2181(CONNECTED) 3] ls /
[stanlong, zookeeper]
[zk: localhost:2181(CONNECTED) 4] get /stanlong    -- 查看创建的节点
123456
cZxid = 0x4
ctime = Wed Feb 05 17:59:27 CST 2020
mZxid = 0x4
mtime = Wed Feb 05 17:59:27 CST 2020
pZxid = 0x4
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x1000057e5030000
dataLength = 6
numChildren = 0
```
6. 退出客户端
```
[zk: localhost:2181(CONNECTED) 0] quit
```
7. 停止客户端
```shell
[root@gmall bin]# ./zkServer.sh stop
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper-3.4.11/bin/../conf/zoo.cfg
Stopping zookeeper ... STOPPED
```

# zookeeper配置参数解读
```shell
# The number of milliseconds of each tick
tickTime=2000  --2s心跳一次
# The number of ticks that the initial
# synchronization phase can take
initLimit=10   -- 10 个心跳桢，一桢2秒。即20s之后认为主从连不上了
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5    --5 个心跳桢， 
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
dataDir=/opt/zookeeper-3.4.11/zkData
# the port at which the clients will connect
clientPort=2181 -- 客户端端口号
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

```
























