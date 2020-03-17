# JDK源码学习-AQS

[toc]

## 1. 整体架构

## 2. Node类

### 2.1 waitStatus

整个Node类的核心状态，有下面几个选项：

- SIGNAL: -1 Node被激活，其后代已经或即将被block
- CANCELLED: 1 Node被取消，可能的原因是超时或interrupt，注意这是唯一>0的状态，所以一般判断如果waitStatus>0则Node为Cancelled状态
- CONDITION: -2 Node在Condition Queue里，除非被转移，不然不可以用作同步队列的Node
- PROPAGATE: -3 当前Node被releaseShared，且需要被扩散，只出现在head里
- 0: 初始状态，无意义

#### 2.1.1 状态变化

