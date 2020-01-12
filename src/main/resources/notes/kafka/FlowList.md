# 需要学习的知识点列表

1. Leader选举过程
2. ConsumerGroup注册过程
3. Consume的commit过程，错误处理:commit过程发生rebalance;
4. 零拷贝：DMA
5. PartitionLeader与Partition Replicator之间的同步与异常处理
6. kafka通用请求及分类
7. zookeeper细节
8. replication分配策略(rack awareness)
9. 持久化：segment新建与删除策略
10. 压缩消息的持久化/version升级策略（升级后新老消息的同步）【108页的疑惑】
11. Compact策略（保留key的最新数据）的细节，运行过程中增加或删除此策略的处理；多个compact线程之间的同步，offset内存中的Map大小不够的处理；compact既然使用mark-copy算法，怎么解决同时还有并发写的问题（答案：从来不compact当前active的segment）

## 异常情况

1. 网络分区
2. broker 挂掉
3. zk 挂掉
4. 请求超时
5. 请求重发
6. leader选举（clean，unclean）
7. Controller重启
8. broker重启
9. producer重启
10. consumer重启
