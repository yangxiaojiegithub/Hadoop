# Sqoop导数

sqoop安装参考 23Sqoop/Sqoop.md。

以 ads_uv_count 为例

```sql
drop table if exists ads_uv_count;
create table ads_uv_count( 
    `dt`            varchar(256) COMMENT '统计日期',
    `day_count`     bigint       COMMENT '当日用户数量',
    `wk_count`      bigint       COMMENT '当周用户数量',
    `mn_count`      bigint       COMMENT '当月用户数量',
    `is_weekend`    varchar(256) COMMENT 'Y,N是否是周末,用于得到本周最终结果',
    `is_monthend`   varchar(256) COMMENT 'Y,N是否是月末,用于得到本月最终结果' 
) COMMENT '活跃设备数';
```

## 导出

从集群导出到关系型数据库

```shell
[root@node04 ~]# sqoop export --connect jdbc:mysql://node01:3306/gmall --username root --password root --table ads_uv_count --num-mappers 1 --export-dir /warehouse/gmall/ads/ads_uv_count --input-fields-terminated-by "\t"
```

```shell
Warning: /opt/stanlong/sqoop/sqoop/../hcatalog does not exist! HCatalog jobs will fail.
Please set $HCAT_HOME to the root of your HCatalog installation.
Warning: /opt/stanlong/sqoop/sqoop/../accumulo does not exist! Accumulo imports will fail.
Please set $ACCUMULO_HOME to the root of your Accumulo installation.
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/opt/stanlong/hadoop-ha/hadoop-2.9.2/share/hadoop/common/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/opt/stanlong/hbase/hbase-1.3.6/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
21/02/26 06:41:42 INFO sqoop.Sqoop: Running Sqoop version: 1.4.7
21/02/26 06:41:42 WARN tool.BaseSqoopTool: Setting your password on the command-line is insecure. Consider using -P instead.
21/02/26 06:41:43 INFO manager.MySQLManager: Preparing to use a MySQL streaming resultset.
21/02/26 06:41:43 INFO tool.CodeGenTool: Beginning code generation
21/02/26 06:41:44 INFO manager.SqlManager: Executing SQL statement: SELECT t.* FROM `ads_uv_count` AS t LIMIT 1
21/02/26 06:41:44 INFO manager.SqlManager: Executing SQL statement: SELECT t.* FROM `ads_uv_count` AS t LIMIT 1
21/02/26 06:41:44 INFO orm.CompilationManager: HADOOP_MAPRED_HOME is /opt/stanlong/hadoop-ha/hadoop-2.9.2
Note: /tmp/sqoop-root/compile/3004c2dc90339d564645a2706225c686/ads_uv_count.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
21/02/26 06:41:50 INFO orm.CompilationManager: Writing jar file: /tmp/sqoop-root/compile/3004c2dc90339d564645a2706225c686/ads_uv_count.jar
21/02/26 06:41:50 INFO mapreduce.ExportJobBase: Beginning export of ads_uv_count
21/02/26 06:41:50 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
21/02/26 06:41:54 INFO Configuration.deprecation: mapred.reduce.tasks.speculative.execution is deprecated. Instead, use mapreduce.reduce.speculative
21/02/26 06:41:54 INFO Configuration.deprecation: mapred.map.tasks.speculative.execution is deprecated. Instead, use mapreduce.map.speculative
21/02/26 06:41:54 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
21/02/26 06:42:04 INFO input.FileInputFormat: Total input files to process : 2
21/02/26 06:42:04 INFO input.FileInputFormat: Total input files to process : 2
21/02/26 06:42:04 INFO mapreduce.JobSubmitter: number of splits:1
21/02/26 06:42:04 INFO Configuration.deprecation: yarn.resourcemanager.zk-address is deprecated. Instead, use hadoop.zk.address
21/02/26 06:42:04 INFO Configuration.deprecation: mapred.map.tasks.speculative.execution is deprecated. Instead, use mapreduce.map.speculative
21/02/26 06:42:04 INFO Configuration.deprecation: yarn.resourcemanager.system-metrics-publisher.enabled is deprecated. Instead, use yarn.system-metrics-publisher.enabled
21/02/26 06:42:05 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1614288702906_0003
21/02/26 06:42:07 INFO impl.YarnClientImpl: Submitted application application_1614288702906_0003
21/02/26 06:42:07 INFO mapreduce.Job: The url to track the job: http://node02:8088/proxy/application_1614288702906_0003/
21/02/26 06:42:07 INFO mapreduce.Job: Running job: job_1614288702906_0003
21/02/26 06:42:51 INFO mapreduce.Job: Job job_1614288702906_0003 running in uber mode : false
21/02/26 06:42:51 INFO mapreduce.Job:  map 0% reduce 0%
21/02/26 06:43:06 INFO mapreduce.Job:  map 100% reduce 0%
21/02/26 06:43:07 INFO mapreduce.Job: Job job_1614288702906_0003 completed successfully
21/02/26 06:43:08 INFO mapreduce.Job: Counters: 30
	File System Counters
		FILE: Number of bytes read=0
		FILE: Number of bytes written=212036
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=271
		HDFS: Number of bytes written=0
		HDFS: Number of read operations=7
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=0
	Job Counters 
		Launched map tasks=1
		Rack-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=11226
		Total time spent by all reduces in occupied slots (ms)=0
		Total time spent by all map tasks (ms)=11226
		Total vcore-milliseconds taken by all map tasks=11226
		Total megabyte-milliseconds taken by all map tasks=11495424
	Map-Reduce Framework
		Map input records=2
		Map output records=2
		Input split bytes=221
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=151
		CPU time spent (ms)=1650
		Physical memory (bytes) snapshot=141242368
		Virtual memory (bytes) snapshot=4032622592
		Total committed heap usage (bytes)=30474240
	File Input Format Counters 
		Bytes Read=0
	File Output Format Counters 
		Bytes Written=0
21/02/26 06:43:08 INFO mapreduce.ExportJobBase: Transferred 271 bytes in 74.1677 seconds (3.6539 bytes/sec)
21/02/26 06:43:08 INFO mapreduce.ExportJobBase: Exported 2 records.
```

## 导入

从关系型数据库导入集群

```shell
[root@node04 ~]# sqoop import --connect jdbc:mysql://node01:3306/gmall --username root --password root --table ads_uv_count --num-mappers 1 --hive-import --input-fields-terminated-by "\t" --hive-overwrite --hive-table ads_uv_count
```

导入报错

Exception in thread "main" java.lang.RuntimeException: java.io.IOException: Previous writer likely failed to write hdfs://hacluster/tmp/hive/root/_tez_session_dir/6555be9a-18f8-43ac-a330-b6a9daa0d3ab/hadoop-lzo-0.4.21.jar. Failing because I am unlikely to write too.

