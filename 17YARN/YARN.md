# YARN

## 节点规划

|        | NN-1 | NN-12 | DN   | ZK   | ZKFC | JNN  | RS   | NM   |
| ------ | ---- | ----- | ---- | ---- | ---- | ---- | ---- | ---- |
| node01 | *    |       |      |      | *    | *    |      |      |
| node02 |      | *     | *    | *    | *    | *    |      | *    |
| node03 |      |       | *    | *    |      | *    | *    | *    |
| node04 |      |       | *    | *    |      |      | *    | *    |

- 拷贝 mapred-site.xml.template，编辑 mapred-site.xml

```shell
[root@node01 hadoop]# pwd
/opt/stanlong/hadoop-2.9.2/etc/hadoop
[root@node01 hadoop]# cp mapred-site.xml.template mapred-site.xml
[root@node01 hadoop]# vi mapred-site.xml
```

```xml
<?xml version="1.0"?>
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
        <name>mapreduce.framework.name</name> 开启 mapreduce on yarn
        <value>yarn</value>
    </property>
</configuration>
```

- 编辑yarn-site.xml

```shell
[root@node01 hadoop]# vi yarn-site.xml
```

```xml
<?xml version="1.0"?>
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
<configuration>

<!-- Site specific YARN configuration properties -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
       <name>yarn.resourcemanager.ha.enabled</name>
       <value>true</value>
     </property>
     <property>
       <name>yarn.resourcemanager.cluster-id</name>
       <value>cluster1</value>
     </property>
     <property>
       <name>yarn.resourcemanager.ha.rm-ids</name>
       <value>rm1,rm2</value>
     </property>
     <property>
       <name>yarn.resourcemanager.hostname.rm1</name>
       <value>node03</value>
     </property>
     <property>
       <name>yarn.resourcemanager.hostname.rm2</name>
       <value>node04</value>
     </property>
     <property>
       <name>yarn.resourcemanager.zk-address</name>
       <value>node02:2181,node03:2181,node04:2181</value>
     </property>
</configuration>
```

- 将 mapred-site.xml 和 yarn-site.xml 分发到 node02, node03, node04上

```shell
[root@node01 hadoop]# scp mapred-site.xml yarn-site.xml node02:`pwd`
[root@node01 hadoop]# scp mapred-site.xml yarn-site.xml node03:`pwd`
[root@node01 hadoop]# scp mapred-site.xml yarn-site.xml node04:`pwd`
```

- 启动yarn

```shell
[root@node01 hadoop]# start-yarn.sh 
starting yarn daemons
starting resourcemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-resourcemanager-node01.out
node02: starting nodemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-nodemanager-node02.out
node04: starting nodemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-nodemanager-node04.out
node03: starting nodemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-nodemanager-node03.out
[root@node01 hadoop]# 
```

- 在node03和node04节点上手动启动resourcemanager

```shell
[root@node03 ~]# yarn-daemon.sh start resourcemanager
starting resourcemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-resourcemanager-node03.out
```

```shell
[root@node04 hadoop]# yarn-daemon.sh start resourcemanager
starting resourcemanager, logging to /opt/stanlong/hadoop-2.9.2/logs/yarn-root-resourcemanager-node04.out
```

- resourcemanager的web端口号是 8088

![](./doc/01.png)

![](./doc/02.png)

