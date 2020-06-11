

# HDFS架构

- 存储模型：字节
  - 文件线形切割成块（Block）:偏移量 offset(byte)
  - Block分散存储在集群节点中
  - 单一文件Block大小一致，文件与文件可以不一致
  - Block可以设置副本数，副本分散在不同节点中。副本数不要超过节点数量
  - 文件上传可以设置副本数和Block大小
  - 已上传的文件Block副本数可以调整，大小不变
  - 只支持一次写入多次读取。同一时刻只有一个写入者
  - 可以append追加数据
- 架构模型
  - 文件元数据 MetaData, 文件数据（元数据，数据本身）
  - (主) NameNode节点保存文件元数据：单节点，posix
  - (从) DataNode节点保存文件Block数据：多节点
  - DataNode与NameNode保持心跳，提交Block列表
  - HdfsClient与NameNode交互元数据信息
  - HdfsClient与DataNode交互文件Block数据
- HDFS架构



- HDFS设计思想



- NameNode(NN)
  - 基于内存存储：不会和磁盘发生交换
    - 只存在内存中
    - 持久化
  - NameNode主要功能
    - 接受客户端的读写服务
    - 收集DataNode汇报的Block列表信息
  - NameNode保存metadata信息包括
    - 文件owership和permissions
    - 文件大小，时间
    - Block列表：Block偏移量，位置信息
    - Block每副本位置（由DataNode上报）
- DataNode(DN)
  - 本地磁盘目录存储数据（Block）,文件形式
  - 同时存储Block的元数据信息文件
  - 启动DN时会向NN汇报block信息
  - 通过向NN发送心跳保持与其联系（3秒一次），如果NN10分钟没有收到DN的心跳，则认为其已经lost，并copy其上的block到其它DN

- HDFS优点
  - 高容错性
    - 数据自动保存多个备份
    - 副本丢失后，自动备份
  - 适合批处理
    - 移动计算而非数据
    - 数据位置影暴露给计算框架（Block偏移量）
  - 适合大数据处理
  - 可构建在廉价的机器上
- HDFS缺点
  - 低延迟数据
    - 比如毫秒级
    - 低延迟与高吞吐率
  - 小文件存取
    - 占用NameNode大量内存
    - 寻道时间超过读取时间
  - 并发写入，文件随机修改
    - 一个文件只能有一个写入者
    - 仅支持append

- Block的副本放置策略
  - 第一个副本：放置在上传文件的DN; 如果是集群外提交，则随机挑选一台磁盘不太满，cpu不太忙的节点
  - 第二个副本：放置在于第一个副本不同的机架的节点上
  - 第三个副本：与第二个副本相同的机架的节点
  - 更多副本：随机节点



