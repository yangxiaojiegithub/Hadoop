# Hive

Hive 是基于Hadoop的一个数据仓库工具，可以将结构化的数据文件映射成一张表，并提供类SQL的查询功能。

本质是：将HQL转化成MapReduce程序

1）Hive处理的数据存储在HDFS

2）Hive分析数据底层的实现是MapReduce

3）执行程序运行在Yarn上

![](./doc/01.png)

Hive中搭建分为三种方式 `内嵌Derby方式` 、`Local方式`、 `Remote方式` 三种方式归根到底就是元数据的存储位置不一样

## 节点规划

|                    |                              |
| ------------------ | ---------------------------- |
| node01（服务器端） | apache-hive-1.2.2-bin.tar.gz |
| node02 （客户端）  | apache-hive-1.2.2-bin.tar.gz |
| node03 （客户端）  | apache-hive-1.2.2-bin.tar.gz |

## Hive安装部署

### 启动hadoop集群

参考07Hadoop/04HA搭建启动hadoop集群

### 解压

```shell
[root@node01 ~]# tar -zxf apache-hive-1.2.2-bin.tar.gz -C /opt/stanlong/hive
```

### 配置hive环境变量

```shell
[root@node01 bin]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin
[root@node01 bin]# vi /etc/profile # 在文件最后添加
export HIVE_HOME=/opt/stanlong/hive/apache-hive-1.2.2-bin  # HIVE环境变量
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$HIVE_HOME/bin
[root@node01 bin]# source /etc/profile # 使配置文件生效
[root@node01 bin]# hi # 命令提示
history         hive            hive-config.sh  hiveserver2   
```

## 内嵌Derby方式

使用derby存储方式时，运行hive会在**当前目录**生成一个derby文件和一个metastore_db目录。这种存储方式的弊端是在同一个目录下同时只能有一个hive客户端能使用数据库

### 配置hive-env.sh

```shell
[root@node01 conf]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
[root@node01 conf]# cp hive-env.sh.template hive-env.sh
[root@node01 conf]# vi hive-env.sh
```

```properties
... 省略掉文件中的注释

# Set HADOOP_HOME to point to a specific hadoop install directory
# 配置Hadoop环境变量，在/etc/profile里配置过的话，这里也可以不用再配置
# export HADOOP_HOME=/opt/stanlong/hadoop-ha/hadoop-2.9.2

# Hive Configuration Directory can be controlled by:
# 配置Hive配置文件的路径
export HIVE_CONF_DIR=/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
```

### 更改hive 默认配置

默认情况：Hive内部表都属于缺省库default，在HDFS的目录为/user/hive/warehouse/

在HDFS上创建hive默认目录/tmp和/user/hive/warehouse两个目录并修改他们的同组权限可写

```shell
[root@node01 hive]# hdfs dfs -mkdir /tmp
[root@node01 hive]# hdfs dfs -mkdir -p /user/hive/warehouse
[root@node01 hive]# hdfs dfs -chmod g+w /tmp
[root@node01 hive]# hdfs dfs -chmod g+w /user/hive/warehouse
```

### 启动hive

```shell
[root@node01 ~]# hive
Logging initialized using configuration in jar:file:/opt/stanlong/hive/apache-hive-1.2.2-bin/lib/hive-common-1.2.2.jar!/hive-log4j.properties
hive> 
```

使用derby存储方式时，运行hive会在**当前目录**生成一个derby文件和一个metastore_db目录

![](./doc/02.png)

### 退出hive

```sql
hive> quit;
```

### derby 方式简单测试

```sql
hive> create table hehe(id String); # 建表
OK
Time taken: 0.941 seconds
hive> insert into hehe values ("Hello Hive"); # 插入数据
Query ID = root_20210122062951_5288a120-7ce8-496e-bd8f-f21742b18bdc
Total jobs = 3
Launching Job 1 out of 3
Number of reduce tasks is set to 0 since there's no reduce operator
Starting Job = job_1611264698467_0002, Tracking URL = http://node02:8088/proxy/application_1611264698467_0002/
Kill Command = /opt/stanlong/hadoop-ha/hadoop-2.9.2/bin/hadoop job  -kill job_1611264698467_0002
Hadoop job information for Stage-1: number of mappers: 1; number of reducers: 0
2021-01-22 06:30:43,298 Stage-1 map = 0%,  reduce = 0%
2021-01-22 06:31:28,861 Stage-1 map = 100%,  reduce = 0%, Cumulative CPU 8.8 sec
MapReduce Total cumulative CPU time: 8 seconds 800 msec
Ended Job = job_1611264698467_0002
Stage-4 is selected by condition resolver.
Stage-3 is filtered out by condition resolver.
Stage-5 is filtered out by condition resolver.
Moving data to: hdfs://hacluster/user/hive/warehouse/hehe/.hive-staging_hive_2021-01-22_06-29-51_792_7409469211417145821-1/-ext-10000
Loading data to table default.hehe
Table default.hehe stats: [numFiles=1, numRows=1, totalSize=11, rawDataSize=10]
MapReduce Jobs Launched: 
Stage-Stage-1: Map: 1   Cumulative CPU: 8.8 sec   HDFS Read: 3097 HDFS Write: 79 SUCCESS
Total MapReduce CPU Time Spent: 8 seconds 800 msec
OK
Time taken: 102.511 seconds
hive> select * from hehe; # 查询数据
OK
Hello Hive
Time taken: 0.544 seconds, Fetched: 1 row(s)
hive> 
```

## Local方式安装

这种存储方式需要在本地运行一个mysql服务器，元数据存储在mysql里

### 安装mysql

参考 DBA/mysql/01mysql安装.md

### Hive元数据配置到mysql

1. 上传mysql驱动jar包

   ```shell
   [root@node01 lib]# pwd
   /opt/stanlong/hive/apache-hive-1.2.2-bin/lib
   [root@node01 lib]# mv ~/mysql-connector-java-5.1.37.jar .
   ```

2. 配置 hive-site.xml文件

   官方文档路径 https://cwiki.apache.org/confluence/display/Hive/AdminManual+MetastoreAdmin

   ```shell
   [root@node01 conf]# pwd
   /opt/stanlong/hive/apache-hive-1.2.2-bin/conf
   [root@node01 conf]# vi hive-site.xml
   ```

   ```xml
   <?xml version="1.0" encoding="UTF-8" standalone="no"?>
   <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
   <configuration>
   
   	<!-- 配置hive文件在hdfs上的保存路径 -->
       <property>
           <name>hive.metastore.warehouse.dir</name>  
           <value>/user/hivedb/warehouse</value>
       </property>
       <property>
           <name>hive.metastore.local</name>
           <!-- 单用户模式下值为 false -->
           <value>false</value>
       </property>
       <property>
           <!-- 元数据库的链接地址 mysql, hive元数据库名称 hivedb -->
           <name>javax.jdo.option.ConnectionURL</name>  
           <value>jdbc:mysql://192.168.235.11:3306/hivedb?createDatabaseIfNotExist=true</value>
       </property>
       <property>
           <!-- 指定mysql驱动 -->
           <name>javax.jdo.option.ConnectionDriverName</name>
           <value>com.mysql.jdbc.Driver</value>
       </property>
       <property>
           <!-- 指定mysql用户名 -->
           <name>javax.jdo.option.ConnectionUserName</name>
           <value>root</value>
       </property>
       <property>
           <name>javax.jdo.option.ConnectionPassword</name>
           <value>root</value>
       </property>
   	
   	<!-- 表头信息配置 -->
   	<property>
   		<name>hive.cli.print.header</name>
   		<value>true</value>
   	</property>
   
   	<!-- 显示当前数据库 -->
   	<property>
   		<name>hive.cli.print.current.db</name>
   		<value>true</value>
   	</property>
   
   </configuration>
   ```

3. 启动hive

   ```shell
   [root@node01 ~]# hive
   21/01/23 18:36:46 WARN conf.HiveConf: HiveConf of name hive.metastore.local does not exist
   
   Logging initialized using configuration in jar:file:/opt/stanlong/hive/apache-hive-1.2.2-bin/lib/hive-common-1.2.2.jar!/hive-log4j.properties
   hive (default)> 
   ```

4. 查看MySQL数据库

   hivedb库已成功创建， 表TBLS和DBS保存了hive表和相关的数据库信息

   ```sql
   mysql> show databases;
   +--------------------+
   | Database           |
   +--------------------+
   | information_schema |
   | hivedb             |
   | mysql              |
   | performance_schema |
   | sys                |
   +--------------------+
   5 rows in set (0.00 sec)
   ```

   ```sql
   mysql> use hivedb;
   mysql> select * from TBLS;
   +--------+-------------+-------+------------------+-------+-----------+-------+----------+---------------+--------------------+--------------------+
   | TBL_ID | CREATE_TIME | DB_ID | LAST_ACCESS_TIME | OWNER | RETENTION | SD_ID | TBL_NAME | TBL_TYPE      | VIEW_EXPANDED_TEXT | VIEW_ORIGINAL_TEXT |
   +--------+-------------+-------+------------------+-------+-----------+-------+----------+---------------+--------------------+--------------------+
   |      1 |  1611398538 |     1 |                0 | root  |         0 |     1 | hehe     | MANAGED_TABLE | NULL               | NULL               |
   +--------+-------------+-------+------------------+-------+-----------+-------+----------+---------------+--------------------+--------------------+
   1 row in set (0.00 sec)
   
   mysql> select * from DBS;
   +-------+-----------------------+----------------------------------------+---------+------------+------------+
   | DB_ID | DESC                  | DB_LOCATION_URI                        | NAME    | OWNER_NAME | OWNER_TYPE |
   +-------+-----------------------+----------------------------------------+---------+------------+------------+
   |     1 | Default Hive database | hdfs://hacluster/user/hivedb/warehouse | default | public     | ROLE       |
   +-------+-----------------------+----------------------------------------+---------+------------+------------+
   1 row in set (0.00 sec)
   ```

5. 基本测试及hive多窗口登录

   参考 derby 方式简单测试

6. 删除derby方式安装生成的 derby.log 和 metastore_db

## Remote方式安装

采取服务端和客户端分离的方式安装，按节点规划，服务端在node01，客户端在node02，node03上. 客户端与服务端之间通过 thrift 协议通信，端口号9083

1. 分发node01上的hive目录到node02，node03

   ```shell
   [root@node01 stanlong]# pwd
   /opt/stanlong
   [root@node01 stanlong]# scp -r hive/ node02:`pwd`
   [root@node01 stanlong]# scp -r hive/ node03:`pwd`
   ```

2. 分发 node01 上的 /etc/profile 文件到 node02，node03. 并使文件生效

   ```shell
   [root@node01 ~]# scp /etc/profile node02:/etc/profile
   [root@node01 ~]# scp /etc/profile node03:/etc/profile
   [root@node02 stanlong]# source /etc/profile
   [root@node02 stanlong]# hi
   history         hive/           hive-config.sh  hiveserver2 
   [root@node03 stanlong]# source /etc/profile
   [root@node03 stanlong]# hi
   history         hive/           hive-config.sh  hiveserver2 
   ```

3. node01 后台启动

   ```shell
   [root@node01 ~]# nohup hive --service metastore > /dev/null 2>&1 & # 后台启动
   [1] 12109
   [root@node01 ~]# jobs # 查看后台启动任务，任务编号 1
   [1]+  Running                 nohup hive --service metastore &
   [root@node01 ~]# ss -nal | grep 9083 # 监听9083端口是否启用
   Netid  State      Recv-Q Send-Q                         Local Address:Port                                        Peer Address:Port
   tcp    LISTEN     0      50                                        *:9083                                                  *:*
   
   [root@node01 ~]# fg 1 后台任务挪到前台
   nohup hive --service metastore
   ctrl+c 退出后台任务
   ```

4. 编辑node02，node03的 hive-site.xml 文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" standalone="no"?>
   <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
   <configuration>
   
   	<!-- 配置hive文件在hdfs上的保存路径 -->
       <property>
           <name>hive.metastore.warehouse.dir</name>  
           <value>/user/hivedb/warehouse</value>
       </property>
       <!-- 配置与服务端的通信 -->
       <property>
   	    <name>hive.metastore.uris</name>
   	    <value>thrift://node01:9083</value>
   	</property>
   </configuration>
   ```

5. 客户端启动hive

   ```shell
   [root@node02 ~]# hive
   
   Logging initialized using configuration in jar:file:/opt/stanlong/hive/apache-hive-1.2.2-bin/lib/hive-common-1.2.2.jar!/hive-log4j.properties
   hive> 
   ```
   
6. 基本测试

   参考 derby 方式简单测试

## JDBC访问hive

在服务端后台启动一个hiveserver进程

```shell
[root@node01 ~]# nohup hiveserver2 > /dev/null 2>&1 &
[root@node01 ~]# jobs
[1]-  Running                 nohup hive --service metastore &
[2]+  Running                 nohup hiveserver2 &
```

客户端执行beeline命令

```shell
[root@node02 conf]# beeline -u "jdbc:hive2://node01:10000"  -n root -p root
Connecting to jdbc:hive2://node01:10000
Connected to: Apache Hive (version 1.2.2)
Driver: Hive JDBC (version 1.2.2)
Transaction isolation: TRANSACTION_REPEATABLE_READ
Beeline version 1.2.2 by Apache Hive
0: jdbc:hive2://node01:10000> 
```

启动脚本参考 23自定义集群脚本/Hive启停脚本.md

## 查看Hive日志

默认文件存放路径

```shell
[root@node01 conf]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
[root@node01 conf]# vi hive-log4j.properties.template 
18 hive.log.threshold=ALL
19 hive.root.logger=INFO,DRFA
20 hive.log.dir=${java.io.tmpdir}/${user.name}
21 hive.log.file=hive.log
```

可知默认日志文件 /${java.io.tmpdir}/${user.name}/hive.log，在本例中也就是 /tmp/root/hive.log

```shell
[root@node01 conf]# cd /tmp/root/
[root@node01 root]# ll
-rw-r--r-- 1 root root 383198 Jan 25 06:06 hive.log
```









# 常见错误及解决方案

## JVM堆内存溢出

描述：java.lang.OutOfMemoryError: Java heap space

解决：在yarn-site.xml中加入如下代码

```xml
<property>
	<name>yarn.scheduler.maximum-allocation-mb</name>
	<value>2048</value>
</property>
<property>
  	<name>yarn.scheduler.minimum-allocation-mb</name>
  	<value>2048</value>
</property>
<property>
	<name>yarn.nodemanager.vmem-pmem-ratio</name>
	<value>2.1</value>
</property>
<property>
	<name>mapred.child.java.opts</name>
	<value>-Xmx1024m</value>
</property>
```

# Hive 运行引擎Tez

Tez是一个Hive的运行引擎，性能优于MR。为什么优于MR呢？看下图。

![](./doc/08.png)

用Hive直接编写MR程序，假设有四个有依赖关系的MR作业，上图中，绿色是Reduce Task，云状表示写屏蔽，需要将中间结果持久化写到HDFS。

Tez可以将多个有依赖的作业转换为一个作业，这样只需写一次HDFS，且中间节点较少，从而大大提升作业的计算性能。

## 安装包准备

1. 下载tez的依赖包：http://tez.apache.org
2. 拷贝，解压缩，修改名称

```shell
[root@node01 ~]# tar -zxf apache-tez-0.9.2-bin.tar.gz
[root@node01 ~]# mv apache-tez-0.9.2-bin /opt/stanlong/
[root@node01 ~]# cd /opt/stanlong/
[root@node01 stanlong]# ll
total 4
drwxr-xr-x  5  502 games 4096 Mar 19  2019 apache-tez-0.9.2-bin
drwxr-xr-x  9 root root   210 Jun 20 11:12 flume
drwxr-xr-x 10 root root   161 Jun 11 10:27 hadoop-2.9.2
drwxr-xr-x 10 root root   161 Jun 11 10:13 hadoop-2.9.2-full
drwxr-xr-x  8 root root   172 Jun 14 11:28 hbase
drwxr-xr-x 10 root root   208 Jun 12 03:59 hive
drwxr-xr-x  7 root root   101 Jun 22 01:17 kafka
drwxr-xr-x  8 root root   138 Jun 26 21:49 kafka-manager
[root@node01 stanlong]# mv apache-tez-0.9.2-bin/ tez-0.9.2
[root@node01 stanlong]# ll
total 4
drwxr-xr-x  9 root root   210 Jun 20 11:12 flume
drwxr-xr-x 10 root root   161 Jun 11 10:27 hadoop-2.9.2
drwxr-xr-x 10 root root   161 Jun 11 10:13 hadoop-2.9.2-full
drwxr-xr-x  8 root root   172 Jun 14 11:28 hbase
drwxr-xr-x 10 root root   208 Jun 12 03:59 hive
drwxr-xr-x  7 root root   101 Jun 22 01:17 kafka
drwxr-xr-x  8 root root   138 Jun 26 21:49 kafka-manager
drwxr-xr-x  5  502 games 4096 Mar 19  2019 tez-0.9.2
[root@node01 stanlong]# 
```

## 在Hive中配置Tez

1. 进入到Hive的配置目录：

```shell
[root@node01 stanlong]# cd /opt/stanlong/hive/conf/
```

2. 在hive-env.sh文件中添加tez环境变量配置和依赖包环境变量配置

```shell
[root@node01 conf]# vi hive-env.sh
export TEZ_HOME=/opt/stanlong/tez-0.9.2
export TEZ_JARS=""
for jar in `ls $TEZ_HOME |grep jar`; do
    export TEZ_JARS=$TEZ_JARS:$TEZ_HOME/$jar
done
for jar in `ls $TEZ_HOME/lib`; do
    export TEZ_JARS=$TEZ_JARS:$TEZ_HOME/lib/$jar
done

export HIVE_AUX_JARS_PATH=/opt/stanlong/hadoop-2.9.2/share/hadoop/common/hadoop-lzo-0.4.20.jar$TEZ_JARS
```

3. 在hive-site.xml文件中添加如下配置，更改hive计算引擎

```xml
[root@node01 conf]# vi core-site.xml 
<property>
    <name>hive.execution.engine</name>
    <value>tez</value>
</property>

```

## 配置Tez

- 在Hive的/opt/stanlong/hive/conf下面创建一个tez-site.xml文件

```xml
[root@node01 conf]# vi tez-site.xml

<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
	<property>
		<name>tez.lib.uris</name>    
		<value>${fs.defaultFS}/tez/tez-0.9.1,${fs.defaultFS}/tez/tez-0.9.1/lib</value>
	</property>
	<property>
		<name>tez.lib.uris.classpath</name>    	
		<value>${fs.defaultFS}/tez/tez-0.9.1,${fs.defaultFS}/tez/tez-0.9.1/lib</value>
	</property>
	<property>
		 <name>tez.use.cluster.hadoop-libs</name>
		 <value>true</value>
	</property>
	<property>
		 <name>tez.history.logging.service.class</name>        
		 <value>org.apache.tez.dag.history.logging.ats.ATSHistoryLoggingService</value>
	</property>
</configuration>

```

## 上传Tez到集群

将/opt/stanlong/tez-0.9.1上传到HDFS的/tez路径

```shell
[root@node01 conf]# hadoop dfs -mkdir /tez
[root@node01 conf]# hadoop dfs -put /opt/stanlong/tez-0.9.2 /tez
[root@node01 conf]# hadoop dfs -ls /tez
Found 1 items
drwxr-xr-x   - root supergroup          0 2020-06-26 22:24 /tez/tez-0.9.2

```

## 测试

```shell
[root@node01 hive]# hive

hive (default)> create table student(
id int,
name string);

hive (default)> insert into student values(1,"zhangsan");

如果没报错，就算成功了
hive (default)> select * from student;
1       zhangsan
```

## 小结

1. 运行Tez时检查到用过多内存而被NodeManager杀死进程问题：

```
Caused by: org.apache.tez.dag.api.SessionNotRunning: TezSession has already shutdown. Application application_1546781144082_0005 failed 2 times due to AM Container for appattempt_1546781144082_0005_000002 exited with  exitCode: -103
For more detailed output, check application tracking page:http://hadoop103:8088/cluster/app/application_1546781144082_0005Then, click on links to logs of each attempt.
Diagnostics: Container [pid=11116,containerID=container_1546781144082_0005_02_000001] is running beyond virtual memory limits. Current usage: 216.3 MB of 1 GB physical memory used; 2.6 GB of 2.1 GB virtual memory used. Killing container.
```

这种问题是从机上运行的Container试图使用过多的内存，而被NodeManager kill掉了。

```
[摘录] The NodeManager is killing your container. It sounds like you are trying to use hadoop streaming which is running as a child process of the map-reduce task. The NodeManager monitors the entire process tree of the task and if it eats up more memory than the maximum set in mapreduce.map.memory.mb or mapreduce.reduce.memory.mb respectively, we would expect the Nodemanager to kill the task, otherwise your task is stealing memory belonging to other containers, which you don't want.
```

解决方法：

方案一：或者是关掉虚拟内存检查。我们选这个，修改yarn-site.xml

```xml
<property>
<name>yarn.nodemanager.vmem-check-enabled</name>
<value>false</value>
</property>
```

方案二：mapred-site.xml中设置Map和Reduce任务的内存配置如下：(value中实际配置的内存需要根据自己机器内存大小及应用情况进行修改)

```xml
<property>
　　<name>mapreduce.map.memory.mb</name>
　　<value>1536</value>
</property>
<property>
　　<name>mapreduce.map.java.opts</name>
　　<value>-Xmx1024M</value>
</property>
<property>
　　<name>mapreduce.reduce.memory.mb</name>
　　<value>3072</value>
</property>
<property>
　　<name>mapreduce.reduce.java.opts</name>
　　<value>-Xmx2560M</value>
</property>
```





















































































