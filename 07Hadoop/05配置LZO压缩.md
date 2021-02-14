# Hadoop配置LZO压缩

没安装成功，待续.........

1. 先下载lzo的jar项目

   ```shell
   [root@node01 ~]# wget https://github.com/twitter/hadoop-lzo/archive/master.zip
   
   # 下载maven
   [root@node01 ~]# wget https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
   ```

2. 解压

   ```shell
   [root@node01 ~]# unzip master.zip 
   [root@node01 ~]# cd hadoop-lzo-master/
   [root@node01 hadoop-lzo-master]# vi pom.xml 
   
   # 修改hadoop版本为自己使用的版本
   89 <properties>
   90     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   91     <hadoop.current.version>2.9.2</hadoop.current.version>
   92     <hadoop.old.version>1.0.4</hadoop.old.version>
   93  </properties>
   ```

3. 编译打包

4. 将编译好后的hadoop-lzo-0.4.21.jar 放入hadoop-2.9.2/share/hadoop/common

   ```shell
   [root@node01 ~]# mv hadoop-lzo-0.4.21.jar /opt/stanlong/hadoop-ha/hadoop-2.9.2/share/hadoop/common
   ```

5. 分发hadoop-lzo-0.4.21.jar到 node02，node03，nodr04上

   ```shell
   [root@node01 common]# pwd
   /opt/stanlong/hadoop-ha/hadoop-2.9.2/share/hadoop/common
   [root@node01 common]# ~/myshell/rsyncd.sh hadoop-lzo-0.4.20.jar
   ```

6. core-site.xml增加配置支持LZO压缩

   ```xml
   <property>
   <name>io.compression.codecs</name>
   <value>
   org.apache.hadoop.io.compress.GzipCodec,
   org.apache.hadoop.io.compress.DefaultCodec,
   org.apache.hadoop.io.compress.BZip2Codec,
   org.apache.hadoop.io.compress.SnappyCodec,
   com.hadoop.compression.lzo.LzoCodec,
   com.hadoop.compression.lzo.LzopCodec
   </value>
   </property>
   
   <property>
       <name>io.compression.codec.lzo.class</name>
       <value>com.hadoop.compression.lzo.LzoCodec</value>
   </property>
   ```

7. 分发 core-site.xml到 node02，node03，nodr04上

   ```shell
   [root@node01 hadoop]# ~/myshell/rsyncd.sh core-site.xml 
   ```

8. 重启hadoop

   ```shell
   [root@node01 hadoop]# stop-all.sh
   [root@node01 hadoop]# start-all.sh
   ```

   



