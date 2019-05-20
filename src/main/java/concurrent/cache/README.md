# 高效率缓存

## 重点
1. 记录值和结果时，使用Future记录结果，这样可以避免同一个值有两个线程在同时计算

## 测试结果

```bash
Count:1000,time:8383ms
Count:10000,time:10827ms
Count:100000,time:10300ms
Count:1000000,time:11667ms
```
```bash
Count:1000,time:9949ms
Count:10000,time:10865ms
Count:100000,time:10649ms
Count:1000000,time:9938ms
```
效果明显

