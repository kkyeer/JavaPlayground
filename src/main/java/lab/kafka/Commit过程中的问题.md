# Kafka Consumer 在commit offset过程中的问题

1. 自动commit offset:自动commit默认时间间隔为5s（可以通过参数调整），但是假如两次commit的间隔之间出现rebalance或者服务终止，现在offset不会commit，那么rebalance以后会出现重复消费
2. 消费且处理完以后，手动同步commit offset（自动retry）:
    1. 如果消息处理过程为异步的，则不保证所有消息实际已经处理完
    2. 程序会阻塞直到commit完成（或者重试完成）如果commit offset过程出现网络阻塞，会影响吞吐量（此时commit最终会成功，但是由于程序被阻塞，程序不会拉取新消息并消费）
3. 消费且处理完以后，手动异步commit offset（不会retry）：
    1. 多次commit并发，因此顺序不确定，因此不会retry，一种可行的解决方案是在客户端引入计数器，通过CAS机制来retry当前最后一次commit
    2. 如果consumer close，且commit失败，由于不会retry，会丢失offset信息
    3. 如果rebalance，且commit失败，会有重复消费问题，需要引入rebalance listener来解决
4. 在业务场景中commit offset：业务场景处理完毕，将offset和处理后的业务数据存入数据库，结合每次consumer启动或者rebalance时，在数据库中获取当前需要处理的offset，并且通过consumer api手动指定消费的起始位置，则commit offset的策略可以忽略（因为每次都会手动指定消费的起始位置）