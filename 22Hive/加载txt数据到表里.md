## DML数据操作

### 向表中装载数据

语法：hive> load data [local] inpath '/opt/module/datas/student.txt' overwrite | into table student [partition (partcol1=val1,…)];

```
（1）load data:表示加载数据
（2）local:表示从本地加载数据到hive表；否则从HDFS加载数据到hive表
（3）inpath:表示加载数据的路径
（4）overwrite:表示覆盖表中已有数据，否则表示追加
（5）into table:表示加载到哪张表
（6）student:表示具体的表
（7）partition:表示上传到指定分区
```

#### 加载数据文件到表里面

- 创建一张表

```sql
hive (default)> create table student(id string, name string) row format delimited fields terminated by '\t' STORED AS TEXTFILE;
```

方式一：加载本地文件到hive

```sql
hive (default)> load data local inpath '/opt/module/datas/student.txt' into table default.student;
```

方式一加载数据到hive集群多半会报错： Invalid path： No files matching path file。

```
hive导入数据语句  load data [local] inpath ，是一个服务器端的指令，它是在服务器端执行。因此指定local时表明加载的文件为本地文件，但是这里的local，在hive中指的是 hiveserver 服务所在的机器，而不是hivecli 或 beeline客户端所在的机器（生产环境大都是 hiveserver 和 hivecli不在同一个机器
```

推荐方法二

方式二：加载HDFS文件到hive中

- 上传文件到HDFS

```sql
hive (default)> dfs -put /opt/module/datas/student.txt /user/atguigu/hive;
```

- 加载HDFS文件到hive表

```sql
hive (default)> load data inpath '/user/atguigu/hive/student.txt' into table default.student;
```

- 加载HDFS文件覆盖表中已有的数据

```sql
hive (default)> load data inpath '/user/atguigu/hive/student.txt' overwrite into table default.student;
```

#### 通过查询语句向表中插入数据

- 创建一张分区表

```sql
hive (default)> create table student(id int, name string) partitioned by (month string) row format delimited fields terminated by '\t';
```

- 基本插入数据

```sql
hive (default)> insert overwrite table student partition(month='201708')
             select id, name from student where month='201709';
```

- 多插入模式（根据多张表查询结果）

```sql
hive (default)> from student
              insert overwrite table student partition(month='201707')
              select id, name where month='201709'
              insert overwrite table student partition(month='201706')
              select id, name where month='201709';
```

- **查询语句中创建表并加载数据**

  - 根据查询结果创建表

  ```sql
  create table if not exists student3 as select id, name from student;
  ```

#### 创建表时通过Location指定加载数据路径

  - 创建表，并指定在hdfs上的位置

  ```sql
  hive (default)> create table if not exists student5(
                id int, name string
                )
                row format delimited fields terminated by '\t'
                location '/user/hive/warehouse/student5';
  ```

  - 上传数据到hdfs上

  ```sql
  hive (default)> dfs -put /opt/module/datas/student.txt
  /user/hive/warehouse/student5;
  ```

  - 查询数据

  ```sql
  hive (default)> select * from student5;
  ```

#### Import数据到指定Hive表中

```sql
hive (default)> import table student2 partition(month='201709') from
 '/user/hive/warehouse/export/student';
```

## 数据导出

### **Insert导出**

- 将查询的结果导出到本地

```sql
hive (default)> insert overwrite local directory '/opt/module/datas/export/student'
            select * from student;
```

- 将查询的结果格式化导出到本地

```sql
hive(default)>insert overwrite local directory '/opt/module/datas/export/student1'
           ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'             select * from student;
```

- 将查询的结果导出到HDFS上(没有local)

```sql
hive (default)> insert overwrite directory '/user/atguigu/student2'
             ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
             select * from student;
```

### Hadoop命令导出到本地

```sql
hive (default)> dfs -get /user/hive/warehouse/student/month=201709/000000_0
/opt/module/datas/export/student3.txt;
```

### Hive Shell 命令导出

```sql
[atguigu@hadoop102 hive]$ bin/hive -e 'select * from default.student;' >
 /opt/module/datas/export/student4.txt;
```

### Export导出到HDFS上

```sql
(defahiveult)> export table default.student to
 '/user/hive/warehouse/export/student';
```
