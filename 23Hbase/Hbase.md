# Hbase

## 节点规划（在 19YARN/YARN.md基础上规划）

|        | master | regionserver |
| ------ | ------ | ------------ |
| node01 | *      |              |
| node02 |        | *            |
| node03 |        | *            |
| node04 |        | *            |

## Hbase安装

```sql
[root@node01 ~]# tar zxf hbase-1.3.6-bin.tar.gz
[root@node01 ~]# mv hbase-1.3.6 /opt/stanlong/
[root@node01 ~]# cd /opt/stanlong/
[root@node01 stanlong]# mv hbase-1.3.6/ hbase
[root@node01 stanlong]# ll
total 0
drwxr-xr-x 10 root root 161 Jun 11 10:27 hadoop-2.9.2
drwxr-xr-x 10 root root 161 Jun 11 10:13 hadoop-2.9.2-full
drwxr-xr-x  7 root root 160 Jun 14 10:19 hbase
drwxr-xr-x 10 root root 208 Jun 12 03:59 hive
```

## Hbase 配置

```shell
[root@node01 conf]# pwd
/opt/stanlong/hbase/conf
[root@node01 conf]# ll
-rw-r--r-- 1 503 games 1811 Oct  5  2019 hadoop-metrics2-hbase.properties
-rw-r--r-- 1 503 games 4616 Oct 11  2019 hbase-env.cmd
-rw-r--r-- 1 503 games 7530 Oct 14  2019 hbase-env.sh
-rw-r--r-- 1 503 games 2257 Oct  5  2019 hbase-policy.xml
-rw-r--r-- 1 503 games  934 Oct  5  2019 hbase-site.xml
-rw-r--r-- 1 503 games 1169 Oct 11  2019 log4j-hbtop.properties
-rw-r--r-- 1 503 games 4722 Oct 14  2019 log4j.properties
-rw-r--r-- 1 503 games   10 Oct  5  2019 regionservers
```

- 配置 regionservers

```shell
[root@node01 conf]# vi regionservers 
node02
node03
node04
```

- 配置 hbase-env.sh

```shell
[root@node01 conf]# vi hbase-env.sh
27 export JAVA_HOME=/usr/java/jdk1.8.0_65

46 行和17号在 1jdk 1.8以上的版本用不到，可以注释起来
45 # Configure PermSize. Only needed in JDK7. You can safely remove it for JDK8+
     46 #export HBASE_MASTER_OPTS="$HBASE_MASTER_OPTS -XX:PermSize=128m -XX:MaxPermSize=128m -XX:ReservedCodeCacheSize=256m"
     47 #export HBASE_REGIONSERVER_OPTS="$HBASE_REGIONSERVER_OPTS -XX:PermSize=128m -XX:MaxPermSize=128m -XX:ReservedCodeCacheSize=256m"

# 不要用 Hbase 自带的 zk
127 # Tell HBase whether it should manage it's own instance of Zookeeper or not.
128 export HBASE_MANAGES_ZK=false
```

- 配置 hbase-site.xml

```shell
[root@node01 conf]# vi hbase-site.xml
```

```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
-->
<configuration>
	<!--配置为core-site.xml 中的fs.defaultFS -->
	<property>
		<name>hbase.rootdir</name>
		<value>hdfs://mycluster/hbase</value>
	</property>
	<!-- 本地文件系统的临时文件夹 -->
	<property>
        <name>hbase.tmp.dir</name>
        <value>/var/data/hbase</value>
    </property>
	<property>
		<name>hbase.cluster.distributed</name>
		<value>true</value>
	</property>
	<!-- hbase master 节点的端口 -->
	<property>
		<name>hbase.master.port</name>
		<value>16000</value>
	</property>
	<property>
		<name>hbase.zookeeper.quorum</name>
		<value>node02:2181,node03:2181,node04:2181</value>
	</property>
	<property>
		<name>hbase.zookeeper.property.dataDir</name>
		<value>/opt/stanlong/zookeeper-3.4.11/zkData</value>
	</property>
</configuration>
```

- 复制hadoop目录下的  core-site.xml 及hdfs-site.xml 到hbase/conf 目录

```shell
[root@node01 conf]# cp /opt/stanlong/hadoop-2.9.2/etc/hadoop/core-site.xml .
[root@node01 conf]# cp /opt/stanlong/hadoop-2.9.2/etc/hadoop/hdfs-site.xml .
[root@node01 conf]# ll
total 208
-rw-r--r-- 1 root root   1139 Mar 30  2017 beeline-log4j.properties.template
-rw-r--r-- 1 root root   1126 Jun 14 11:10 core-site.xml
-rw-r--r-- 1 root root   2774 Jun 14 11:10 hdfs-site.xml
-rw-r--r-- 1 root root 168715 Mar 31  2017 hive-default.xml.template
-rw-r--r-- 1 root root   2411 Jun 12 00:54 hive-env.sh
-rw-r--r-- 1 root root   2378 Mar 30  2017 hive-env.sh.template
-rw-r--r-- 1 root root   2662 Mar 30  2017 hive-exec-log4j.properties.template
-rw-r--r-- 1 root root   3043 Jun 12 03:59 hive-log4j.properties
-rw-r--r-- 1 root root   3050 Mar 30  2017 hive-log4j.properties.template
-rw-r--r-- 1 root root   1550 Jun 14 11:09 hive-site.xml
-rw-r--r-- 1 root root   1593 Mar 31  2017 ivysettings.xml
[root@node01 conf]# 
```

- 将Hbase分发到 node02, node02, node04 上

```shell
[root@node01 stanlong]# scp -r hbase/ node02:`pwd`
[root@node01 stanlong]# scp -r hbase/ node03:`pwd`
[root@node01 stanlong]# scp -r hbase/ node04:`pwd`
```

- 配置Hbase环境变量

```shell
[root@node01 hbase]# vi /etc/profile
export HBASE_HOME=/opt/stanlong/hbase
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$HIVE_HOME/bin:$HBASE_HOME/bin
[root@node01 hbase]# source /etc/profile
```

## 启动Hbase

- 启动 master

```shell
[root@node01 hbase]# start-hbase.sh
[root@node01 hbase]# hbase-daemon.sh start master
starting master, logging to /opt/stanlong/hbase/logs/hbase-root-master-node01.out
[root@node01 hbase]# 
```

![](./doc/01.png)

- 启动 regionserver

```sql
[root@node02 hbase]# hbase-daemon.sh start regionserver
[root@node03 hbase]# hbase-daemon.sh start regionserver
[root@node04 hbase]# hbase-daemon.sh start regionserver
```

![](./doc/02.png)

# Hbase Shell操作

```shell
[root@node01 ~]# hbase shell
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/opt/stanlong/hbase/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/opt/stanlong/hadoop-2.9.2/share/hadoop/common/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
HBase Shell; enter 'help<RETURN>' for list of supported commands.
Type "exit<RETURN>" to leave the HBase Shell
Version 1.3.6, r806dc3625c96fe2cfc03048f3c54a0b38bc9e984, Tue Oct 15 01:55:41 PDT 2019

hbase(main):001:0> 
```

- 查看

```shell
hbase(main):001:0> list
TABLE                                                                                                                                                      
0 row(s) in 0.7900 seconds

=> []
hbase(main):002:0> 
```

- 建表

```shell
hbase(main):002:0> create 'student','info'
0 row(s) in 3.0810 seconds

=> Hbase::Table - student
hbase(main):003:0> list
TABLE                                                                                                                                                      
student                                                                                                                                                    
1 row(s) in 0.0250 seconds

=> ["student"]
hbase(main):004:0> 
```

- describe 描述表信息

```shell
hbase(main):005:0> describe 'student'
Table student is ENABLED                                                                                                                                   
student                                                                                                                                                    
COLUMN FAMILIES DESCRIPTION                                                                                                                                
{NAME => 'info', BLOOMFILTER => 'ROW', VERSIONS => '1', IN_MEMORY => 'false', KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER
', COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'}                                       
1 row(s) in 0.0590 seconds

hbase(main):006:0> 
```

- 变更表信息

```sql
将 info 列族中的数据存放3个版本
hbase(main):006:0> alter 'student',{NAME=>'info', VERSIONS=>3}
Updating all regions with the new schema...
0/1 regions updated.
1/1 regions updated.
Done.
0 row(s) in 3.8720 seconds

hbase(main):007:0> 
```

- 删除表

```sql
先把表下线
hbase(main):007:0> disable 'student'
0 row(s) in 2.3880 seconds
再执行删除命令
hbase(main):009:0> drop 'student'
0 row(s) in 1.3270 seconds
```

- 查看命名空间

```shell
hbase(main):010:0> list_namespace
NAMESPACE                                                                                                                                                  
default                                                                                                                                                    
hbase                                                                                                                                                      
2 row(s) in 0.4310 seconds

hbase(main):011:0> 
```

- 创建命名空间

```shell
hbase(main):011:0> create_namespace 'bigdata'
0 row(s) in 0.9160 seconds
```

- 指定命名空间创建表

```shell
hbase(main):013:0> create 'bigdata:stu','info'
0 row(s) in 1.2870 seconds

=> Hbase::Table - bigdata:stu
hbase(main):014:0> list
TABLE                                                                                                                                                      
bigdata:stu                                                                                                                                                
1 row(s) in 0.0150 seconds

=> ["bigdata:stu"]
hbase(main):015:0> 
```

- 删除命名空间

```shell
hbase(main):015:0> disable 'bigdata:stu'
0 row(s) in 2.3120 seconds

hbase(main):016:0> drop 'bigdata:stu'
0 row(s) in 1.2940 seconds

hbase(main):017:0> drop_namespace 'bigdata'
0 row(s) in 0.8930 seconds

hbase(main):018:0> 
```

- put 往表里插入数据

```shell
hbase(main):019:0> create 'stu','info'
0 row(s) in 2.3160 seconds

=> Hbase::Table - stu
hbase(main):020:0> put 'stu','1001','info:name','zhangsan'
0 row(s) in 0.3640 seconds
```

- scan 扫描表

```shell
hbase(main):021:0> scan 'stu'
ROW                                     COLUMN+CELL                                                                                                        
 1001                                   column=info:name, timestamp=1592195231009, value=zhangsan                                                          
1 row(s) in 0.1000 seconds
```

- get 查询

```sql
hbase(main):022:0> get 'stu','1001'
COLUMN                                  CELL                                                                                                               
 info:name                              timestamp=1592195231009, value=zhangsan                                                                            
1 row(s) in 0.0640 seconds
```

- put 修改表数据

```shell
hbase(main):023:0> put 'stu','1001','info:name','lisi'
0 row(s) in 0.0550 seconds
hbase(main):025:0> scan 'stu'
ROW                                     COLUMN+CELL                                                                                                        
 1001                                   column=info:name, timestamp=1592196452049, value=lisi                                                              
1 row(s) in 0.0290 seconds
```

- scan 全量扫描(10个版本内的数据都可以扫描到，包括已删除和已修改的)

```shell
hbase(main):026:0> scan 'stu',{RAW => true, VERSIONS =>10 }
ROW                                     COLUMN+CELL                                                                                                        
 1001                                   column=info:name, timestamp=1592196452049, value=lisi                                                              
 1001                                   column=info:name, timestamp=1592195231009, value=zhangsan                                                          
1 row(s) in 0.0470 seconds
```

- 删除表数据

```shell
删除列
hbase(main):027:0> delete 'stu','1001','info:name'
0 row(s) in 0.1830 seconds
删除rowkey
hbase(main):032:0> deleteall 'stu','1001'
0 row(s) in 0.0360 seconds
删除表
hbase(main):034:0> truncate 'stu'

```

- flush 刷新内存数据到hdfs

```sql
hbase(main):036:0> create 'stu3','info'
0 row(s) in 5.2790 seconds

=> Hbase::Table - stu3
hbase(main):037:0> put 'stu3','1001','info:name','zhangsan'
0 row(s) in 0.2110 seconds

hbase(main):038:0> scan 'stu3'
ROW                                     COLUMN+CELL                                                                                                        
 1001                                   column=info:name, timestamp=1592211676841, value=zhangsan                                                          
1 row(s) in 0.0290 seconds

hbase(main):039:0> flush 'stu3'
0 row(s) in 2.2230 seconds
```

![](./doc/03.png)

![](./doc/04.png)