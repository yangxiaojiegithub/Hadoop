# Hive运行Tez引擎

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

[root@node01 conf]# pwd
/opt/stanlong/hive/apache-hive-1.2.2-bin/conf
```

待续...