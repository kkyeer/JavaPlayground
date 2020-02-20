# java.util.concurrent.ThreadPoolExecutor源码解析

ThreadPoolExecutor是JDK提供的原生线程池实现，提供线程缓存，动态增减容量，线程生命周期钩子等功能

## ThreadPoolExecutor的生命周期


![未命名文件.png](https://cdn.jsdelivr.net/gh/kkyeer/picbed/未命名文件.png)