# Hive运行Tez引擎

没成功，待续....

Tez是一个Hive的运行引擎，性能优于MR。为什么优于MR呢？看下图

![](./doc/08.png)

用Hive直接编写MR程序，假设有四个有依赖关系的MR作业，上图中，绿色是Reduce Task，云状表示写屏蔽，需要将中间结果持久化写到HDFS。

Tez可以将多个有依赖的作业转换为一个作业，这样只需写一次HDFS，且中间节点较少，从而大大提升作业的计算性能。

## 节点规划

| node01           |
| ---------------- |
| apache-tez-0.9.1 |

## 安装

```shell
[root@node01 ~]# tar -zxf apache-tez-0.9.1-bin.tar.gz -C /opt/stanlong/hive/
```

## 在Hive中配置Tez

```shell
[root@node01 apache-tez-0.9.1-bin]# pwd
/opt/stanlong/hive/apache-tez-0.9.1-bin
```

```shell
[root@node01 conf]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
[root@node01 conf]# vi hive-env.sh

# Folder containing extra ibraries required for hive compilation/execution can be controlled by:
export TEZ_HOME=/opt/stanlong/hive/apache-tez-0.9.1-bin    #tez的解压目录
export TEZ_JARS=""
for jar in `ls $TEZ_HOME |grep jar`; do
    export TEZ_JARS=$TEZ_JARS:$TEZ_HOME/$jar
done
for jar in `ls $TEZ_HOME/lib`; do
    export TEZ_JARS=$TEZ_JARS:$TEZ_HOME/lib/$jar
done

export HIVE_AUX_JARS_PATH=/opt/stanlong/hadoop-ha/hadoop-2.9.2/share/hadoop/common/hadoop-lzo-0.4.21.jar$TEZ_JARS
```

```shell
在hive-site.xml文件中添加如下配置，更改hive计算引擎
<property>
    <name>hive.execution.engine</name>
    <value>tez</value>
</property>
```

## 配置Tez

```shell
[root@node01 conf]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
[root@node01 conf]# vi tez-site.xml
```

```xml
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

```shell
[root@node01 ~]# hdfs dfs -mkdir /tez
[root@node01 ~]# hdfs dfs -put /opt/stanlong/hive/apache-tez-0.9.1-bin/ /tez
[root@node01 ~]# hdfs dfs -ls /tez
```

## 测试

```shell
show databases;
+----------------+--+
| database_name  |
+----------------+--+
| default        |
| gmall          |
| homework       |
+----------------+--+

use default;

create table stu_tez(
id int,
name string);

insert into stu_tez values(1,"zhangsan");
```



