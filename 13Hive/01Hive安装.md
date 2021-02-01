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

























  



















































































### 





















## 



# 函数

## 系统内置函数

- 查看系统自带的函数

```sql
hive> show functions;
```

- 显示自带的函数的用法

```sql
hive> desc function upper;
```

- 详细显示自带的函数的用法

```sql
hive> desc function extended upper;
```

# 企业级调优

## Fetch抓取

Fetch抓取是指，Hive中对某些情况的查询可以不必使用MapReduce计算。例如：SELECT * FROM employees;在这种情况下，Hive可以简单地读取employee对应的存储目录下的文件，然后输出查询结果到控制台。

在hive-default.xml.template文件中hive.fetch.task.conversion默认是more，老版本hive默认是minimal，该属性修改为more以后，在全局查找、字段查找、limit查找等都不走mapreduce。

```xml
<property>
    <name>hive.fetch.task.conversion</name>
    <value>more</value>
  </property>
```

## 本地模式

大多数的Hadoop Job是需要Hadoop提供的完整的可扩展性来处理大数据集的。不过，有时Hive的输入数据量是非常小的。在这种情况下，为查询触发执行任务消耗的时间可能会比实际job的执行时间要多的多。对于大多数这种情况，Hive可以通过本地模式在单台机器上处理所有的任务。对于小数据集，执行时间可以明显被缩短。

用户可以通过设置hive.exec.mode.local.auto的值为true，来让Hive在适当的时候自动启动这个优化

```sql
set hive.exec.mode.local.auto=true;  //开启本地mr
//设置local mr的最大输入数据量，当输入数据量小于这个值时采用local  mr的方式，默认为134217728，即128M
set hive.exec.mode.local.auto.inputbytes.max=50000000;
//设置local mr的最大输入文件个数，当输入文件个数小于这个值时采用local mr的方式，默认为4
set hive.exec.mode.local.auto.input.files.max=10;
```

## 表的优化

### 小表、大表Join

将key相对分散，并且数据量小的表放在join的左边，这样可以有效减少内存溢出错误发生的几率；再进一步，可以使用map join让小的维度表（1000条以下的记录条数）先进内存。在map端完成reduce。

实际测试发现：新版的hive已经对小表JOIN大表和大表JOIN小表进行了优化。小表放在左边和右边已经没有明显区别。

### 大表Join大表

- 空KEY过滤

有时join超时是因为某些key对应的数据太多，而相同key对应的数据都会发送到相同的reducer上，从而导致内存不够。此时我们应该仔细分析这些异常的key，很多情况下，这些key对应的数据是异常数据，我们需要在SQL语句中进行过滤

- 空key转换

  有时虽然某个key为空对应的数据很多，但是相应的数据不是异常数据，必须要包含在join的结果中，此时我们可以表a中key为空的字段赋一个随机的值，使得数据随机均匀地分不到不同的reducer上

- MapJoin

  如果不指定MapJoin或者不符合MapJoin的条件，那么Hive解析器会将Join操作转换成Common Join，即：在Reduce阶段完成join。容易发生数据倾斜。可以用MapJoin把小表全部加载到内存在map端进行join，避免reducer处理

  - 开启MapJoin参数设置

  ```sql
  1）设置自动选择Mapjoin
  set hive.auto.convert.join = true; 默认为true
  （2）大表小表的阈值设置（默认25M一下认为是小表）：
  set hive.mapjoin.smalltable.filesize=25000000;
  ```

### Group By

默认情况下，Map阶段同一Key数据分发给一个reduce，当一个key数据过大时就倾斜了。

  并不是所有的聚合操作都需要在Reduce端完成，很多聚合操作都可以先在Map端进行部分聚合，最后在Reduce端得出最终结果

- 开启Map端聚合参数设置

```sql
（1）是否在Map端进行聚合，默认为True
hive.map.aggr = true
（2）在Map端进行聚合操作的条目数目
hive.groupby.mapaggr.checkinterval = 100000
（3）有数据倾斜的时候进行负载均衡（默认是false）
hive.groupby.skewindata = true
```

当选项设定为 true，生成的查询计划会有两个MR Job。第一个MR Job中，Map的输出结果会随机分布到Reduce中，每个Reduce做部分聚合操作，并输出结果，这样处理的结果是相同的Group By Key有可能被分发到不同的Reduce中，从而达到负载均衡的目的；第二个MR Job再根据预处理的数据结果按照Group By Key分布到Reduce中（这个过程可以保证相同的Group By Key被分布到同一个Reduce中），最后完成最终的聚合操作

###  Count(Distinct) 去重统计

数据量小的时候无所谓，数据量大的情况下，由于COUNT DISTINCT操作需要用一个Reduce Task来完成，这一个Reduce需要处理的数据量太大，就会导致整个Job很难完成，一般COUNT DISTINCT使用先GROUP BY再COUNT的方式替换

### 避免笛卡尔积

尽量避免笛卡尔积，join的时候不加on条件，或者无效的on条件，Hive只能使用1个reducer来完成笛卡尔积

### 行列过滤

列处理：在SELECT中，只拿需要的列，如果有，尽量使用分区过滤，少用SELECT *。

行处理：在分区剪裁中，当使用外关联时，如果将副表的过滤条件写在Where后面，那么就会先全表关联，之后再过滤

 ### 动态分区调整

  关系型数据库中，对分区表Insert数据时候，数据库自动会根据分区字段的值，将数据插入到相应的分区中，Hive中也提供了类似的机制，即动态分区(Dynamic Partition)，只不过，使用Hive的动态分区，需要进行相应的配置

```sql
开启动态分区参数设置
（1）开启动态分区功能（默认true，开启）
hive.exec.dynamic.partition=true
（2）设置为非严格模式（动态分区的模式，默认strict，表示必须指定至少一个分区为静态分区，nonstrict模式表示允许所有的分区字段都可以使用动态分区。）
hive.exec.dynamic.partition.mode=nonstrict
（3）在所有执行MR的节点上，最大一共可以创建多少个动态分区。
hive.exec.max.dynamic.partitions=1000
	（4）在每个执行MR的节点上，最大可以创建多少个动态分区。该参数需要根据实际的数据来设定。比如：源数据中包含了一年的数据，即day字段有365个值，那么该参数就需要设置成大于365，如果使用默认值100，则会报错。
hive.exec.max.dynamic.partitions.pernode=100
（5）整个MR Job中，最大可以创建多少个HDFS文件。
hive.exec.max.created.files=100000
（6）当有空分区生成时，是否抛出异常。一般不需要设置。
hive.error.on.empty.partition=false
```

### 分桶

### 分区

## 避免数据倾斜

### 合理设置Map数

1）通常情况下，作业会通过input的目录产生一个或者多个map任务。
主要的决定因素有：input的文件总个数，input的文件大小，集群设置的文件块大小。
2）是不是map数越多越好？
答案是否定的。如果一个任务有很多小文件（远远小于块大小128m），则每个小文件也会被当做一个块，用一个map任务来完成，而一个map任务启动和初始化的时间远远大于逻辑处理的时间，就会造成很大的资源浪费。而且，同时可执行的map数是受限的。
3）是不是保证每个map处理接近128m的文件块，就高枕无忧了？
答案也是不一定。比如有一个127m的文件，正常会用一个map去完成，但这个文件只有一个或者两个小字段，却有几千万的记录，如果map处理的逻辑比较复杂，用一个map任务去做，肯定也比较耗时。
针对上面的问题2和3，我们需要采取两种方式来解决：即减少map数和增加map数；

### 小文件进行合并

在map执行前合并小文件，减少map数：CombineHiveInputFormat具有对小文件进行合并的功能（系统默认的格式）。HiveInputFormat没有对小文件合并功能。

```sql
set hive.input.format= org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;
```

### 复杂文件增加Map数

当input的文件都很大，任务逻辑复杂，map执行非常慢的时候，可以考虑增加Map数，来使得每个map处理的数据量减少，从而提高任务的执行效率。

增加map的方法为：根据computeSliteSize(Math.max(minSize,Math.min(maxSize,blocksize)))=blocksize=128M公式，调整maxSize最大值。让maxSize最大值低于blocksize就可以增加map的个数。

### 合理设置Reduce数

```sql
1．调整reduce个数方法一
（1）每个Reduce处理的数据量默认是256MB
hive.exec.reducers.bytes.per.reducer=256000000
（2）每个任务最大的reduce数，默认为1009
hive.exec.reducers.max=1009
（3）计算reducer数的公式
N=min(参数2，总输入数据量/参数1)
2．调整reduce个数方法二
在hadoop的mapred-default.xml文件中修改
设置每个job的Reduce个数
set mapreduce.job.reduces = 15;
3．reduce个数并不是越多越好
1）过多的启动和初始化reduce也会消耗时间和资源；
2）另外，有多少个reduce，就会有多少个输出文件，如果生成了很多个小文件，那么如果这些小文件作为下一个任务的输入，则也会出现小文件过多的问题；
在设置reduce个数的时候也需要考虑这两个原则：处理大数据量利用合适的reduce数；使单个reduce任务处理数据量大小要合适
```

## 并行执行

Hive会将一个查询转化成一个或者多个阶段。这样的阶段可以是MapReduce阶段、抽样阶段、合并阶段、limit阶段。或者Hive执行过程中可能需要的其他阶段。默认情况下，Hive一次只会执行一个阶段。不过，某个特定的job可能包含众多的阶段，而这些阶段可能并非完全互相依赖的，也就是说有些阶段是可以并行执行的，这样可能使得整个job的执行时间缩短。不过，如果有更多的阶段可以并行执行，那么job可能就越快完成。

​    通过设置参数hive.exec.parallel值为true，就可以开启并发执行。不过，在共享集群中，需要注意下，如果job中并行阶段增多，那么集群利用率就会增加。

`set hive.exec.parallel=true;       //打开任务并行执行`

`set hive.exec.parallel.thread.number=16; //同一个sql允许最大并行度，默认为8。`

当然，得是在系统资源比较空闲的时候才有优势，否则，没资源，并行也起不来

  ## 严格模式

Hive提供了一个严格模式，可以防止用户执行那些可能意想不到的不好的影响的查询。

​    通过设置属性hive.mapred.mode值为默认是非严格模式nonstrict 。开启严格模式需要修改hive.mapred.mode值为strict，开启严格模式可以禁止3种类型的查询

```xml
<property>
    <name>hive.mapred.mode</name>
    <value>strict</value>
</property>
```

1)    对于分区表，除非where语句中含有分区字段过滤条件来限制范围，否则不允许执行。换句话说，就是用户不允许扫描所有分区。进行这个限制的原因是，通常分区表都拥有非常大的数据集，而且数据增加迅速。没有进行分区限制的查询可能会消耗令人不可接受的巨大资源来处理这个表。

2)    对于使用了order by语句的查询，要求必须使用limit语句。因为order by为了执行排序过程会将所有的结果数据分发到同一个Reducer中进行处理，强制要求用户增加这个LIMIT语句可以防止Reducer额外执行很长一段时间。

3)    限制笛卡尔积的查询。对关系型数据库非常了解的用户可能期望在执行JOIN查询的时候不使用ON语句而是使用where语句，这样关系数据库的执行优化器就可以高效地将WHERE语句转化成那个ON语句。不幸的是，Hive并不会执行这种优化，因此，如果表足够大，那么这个查询就会出现不可控的情况。

## JVM重用

JVM重用是Hadoop调优参数的内容，其对Hive的性能具有非常大的影响，特别是对于很难避免小文件的场景或task特别多的场景，这类场景大多数执行时间都很短。

Hadoop的默认配置通常是使用派生JVM来执行map和Reduce任务的。这时JVM的启动过程可能会造成相当大的开销，尤其是执行的job包含有成百上千task任务的情况。JVM重用可以使得JVM实例在同一个job中重新使用N次。N的值可以在Hadoop的mapred-site.xml文件中进行配置。通常在10-20之间，具体多少需要根据具体业务场景测试得出。

```xml
<property>
  <name>mapreduce.job.jvm.numtasks</name>
  <value>10</value>
</property>
```

这个功能的缺点是，开启JVM重用将一直占用使用到的task插槽，以便进行重用，直到任务完成后才能释放。如果某个“不平衡的”job中有某几个reduce task执行的时间要比其他Reduce task消耗的时间多的多的话，那么保留的插槽就会一直空闲着却无法被其他的job使用，直到所有的task都结束了才会释放

## 推测执行

在分布式集群环境下，因为程序Bug（包括Hadoop本身的bug），负载不均衡或者资源分布不均等原因，会造成同一个作业的多个任务之间运行速度不一致，有些任务的运行速度可能明显慢于其他任务（比如一个作业的某个任务进度只有50%，而其他所有任务已经运行完毕），则这些任务会拖慢作业的整体执行进度。为了避免这种情况发生，Hadoop采用了推测执行（Speculative Execution）机制，它根据一定的法则推测出“拖后腿”的任务，并为这样的任务启动一个备份任务，让该任务与原始任务同时处理同一份数据，并最终选用最先成功运行完成任务的计算结果作为最终结果。

设置开启推测执行参数：Hadoop的mapred-site.xml文件中进行配置

```xml
<property>
  <name>mapreduce.map.speculative</name>
  <value>true</value>
</property>

<property>
  <name>mapreduce.reduce.speculative</name>
  <value>true</value>
</property>
```

hive本身也提供了配置项来控制reduce-side的推测执行

```xml
<property>
    <name>hive.mapred.reduce.tasks.speculative.execution</name>
    <value>true</value>
  </property>
```

关于调优这些推测执行变量，还很难给一个具体的建议。如果用户对于运行时的偏差非常敏感的话，那么可以将这些功能关闭掉。如果用户因为输入数据量很大而需要执行长时间的map或者Reduce task的话，那么启动推测执行造成的浪费是非常巨大大

## 压缩

## 执行计划

（1）查看下面这条语句的执行计划

```sql
hive (default)> explain select * from emp;
hive (default)> explain select deptno, avg(sal) avg_sal from emp group by deptno;
```

（2）查看详细执行计划

```sql
hive (default)> explain extended select * from emp;
hive (default)> explain extended select deptno, avg(sal) avg_sal from emp group by deptno;
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





















































































