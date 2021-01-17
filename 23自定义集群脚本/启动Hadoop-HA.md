# 启动Hadoop-HA

如果SSH 远程到别的机器上无法执行环境变量的命令，需要在家目录下的 .bashrc 上增加环境变量

```shell
[root@node01 ~]# vi .bashrc
```

```shell
# .bashrc

# User specific aliases and functions

alias rm='rm -i'
alias cp='cp -i'
alias mv='mv -i'

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi
export JAVA_HOME=/usr/java/jdk1.8.0_65
# export HADOOP_HOME=/opt/stanlong/hadoop-single/hadoop-2.9.2 # HADOOP单节点环境变量
# export HADOOP_HOME=/opt/stanlong/hadoop-cluster/hadoop-2.9.2 # HADOOP完全分布式环境变量
export HADOOP_HOME=/opt/stanlong/hadoop-ha/hadoop-2.9.2 # HADOOP-HA环境变量
export ZOOKEEPER_HOME=/opt/stanlong/zookeeper-3.4.11
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$ZOOKEEPER_HOME/bin
```

使 .bashrc 生效

```shell
[root@node01 ~]# source .bashrc
```

**启动Hadoop-HA**

```shell
#!/bin/bash
#启动hadoop ha集群
start(){
    echo ----------------启动zk集群------------------
	for i in node01 node02 node03 node04
	do
        	ssh $i zkServer.sh start
			sleep 3s
	done
	
	echo ----------------启动JNN------------------
	for i in node01 node04
	do
        	ssh $i hadoop-daemon.sh start journalnode
			sleep 1s
	done
	
	echo ----------------启动主NameNode------------------
	ssh node01 hadoop-daemon.sh start namenode
	sleep 3s
	
	echo ----------------启动DFS------------------
	ssh node01 start-dfs.sh 
	sleep 8s
	
	echo ----------------启动主YARN------------------
	ssh node01 start-yarn.sh 
	sleep 4s
	
	echo ----------------启动备RS------------------
	ssh node04 yarn-daemon.sh start resourcemanager
}
```

