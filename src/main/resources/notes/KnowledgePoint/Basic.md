# 基础知识

- Java容器有哪些？
List,Set,Tree,Queue,Dqueue

- 哪些是同步容器,哪些是并发容器？
并发ConcurrentHashMap
同步:Vector,Collections.syncronizedSet/List等

- ArrayList和LinkedList的插入和访问的时间复杂度？
插入 :ArrayList(O(logn))，LinkedList:O(1)
访问相反

- Java反射原理， 注解原理？
反射原理：
注解原理：1. 注解本身作为一个标识，有几种用途：1. @Repository作为自动扫描的标识；2. @EnableDubbo作为开关；3. @RequestParam @Nonnull，作为参数的校验

- 新生代分为几个区？
from,eden,默认一般是8:1:1的比例分配

- 使用什么算法进行垃圾回收？
from:mark-copy
eden:mark-sweep

- 为什么使用这个算法？
from:大量对象昙花一现，在gc时会被回收，因此使用mark-copy，将仍存活的对象copy到eden
eden:mark-compact 大量对象存活,mark-compact

- HashMap在什么情况下会扩容，或者有哪些操作会导致扩容？
map自身capacity超过64，且单个链超过8
put

- HashMap put方法的执行过程？
put:扩容-计算hash-决定存储方式-存入

- HashMap检测到hash冲突后，将元素插入在链表的末尾还是开头？
头

- 1.8还采用了红黑树，讲讲红黑树的特性，为什么人家一定要用红黑树而不是AVL、B树之类的？
todo

- https和http区别，有没有用过其他安全传输手段？
https对传输的流量进行了数字签名，因此拦截通信无法解析包

- 线程池的工作原理，几个重要参数，然后给了具体几个参数分析线程池会怎么做，最后问阻塞队列的作用是什么？
工作原理:todo
参数:todo

- Linux怎么查看系统负载情况？
top:todo

- 请详细描述springmvc处理请求全流程？
todo

- 讲一讲AtomicInteger，为什么要用CAS而不是synchronized？
假设竞争情况相对非竞争情况少,使用CAS有硬件支持，竞争少的情况，发生fallback的概率小，而syncronized每次访问均需要访问Monitor对象，相对来说CAS效率高

- 最近两年遇到的最大的挫折，从挫折中学到了什么？

- 最近有没有学习过新技术？

- 简单说一下面向对象的特征以及六大原则谈谈final、finally、finalize的区别
封装继承多态
final修饰变量，方法
finally配合trycatch进行异常处理
finalize对象被销毁时进行的操作：可能会延迟或永远不执行
六大原则：
    1. 里氏代换原则：可用父类必可用子类
    2. 对扩展开放，对修改封闭
    todo

- 以及应用场景谈谈线程的基本状态，其中的wait() sleep() yield()方法的区别
todo

- JVM性能调优的监控工具了解那些？
jprofile,jmap,todo

- JVM内存模型
Classloader: 类装载
运行时数据区：
    方法区(Metaspace)：类，方法，部分常量
    堆：对象
    虚拟机栈：线程运行的状态，以栈帧形式保存
    程序计数器：运行的行号
    本地方法栈： native方法行号
执行引擎:
    JIT等

- Java内存模型full gc怎么触发
eden到老年代空间不够

- ClassLoader原理和应用
todo

- 高吞吐量的话用哪种gc算法
todo ZGC

- volatile的底层如何实现，怎么就能保住可见性了
todo

- 线程之间的交互方式有哪些？有没有线程交互的封装类 （join）？
todo

- 两次点击，怎么防止重复下订单？
todo
前端第一次点击后，清空购物车，发送请求：请求带唯一ID

- CompletableFuture，这个是JDK1.8里的新特性，通过它怎么实现多线程并发控制？

- Java的静态代理和动态代理有什么差别？最好结合底层代码来说

你除了依照现有框架写业务代码时，还做了哪些改动？

我听到的回答有：增加了Redis缓存，以避免频繁调用一些不变的数据。或者，在MyBitas的xml里，select语句where条件有isnull，即这个值有就增加一个where条件，对此，会对任何一个where增加一个不带isnull的查询条件，以免该语句当传入参数都是null时，做全表扫描。或者，干脆说，后端异步返回的数据量很大，时间很长，我在项目里就调大了异步返回的最大时间，或者对返回信息做了压缩处理，以增加网络传输性能。

对于这个问题，我不在乎听到什么回答，我只关心回答符不符逻辑。一般只要答对，我就会给出“在框架层面有自己的体会，有一定的了解”，否则，我就只会给出“只能在项目经理带领下编写框架代码，对框架本身了解不多”。

- 反向代理方面，nginx的基本配置，比如如何通过lua语言设置规则，如何设置session粘滞。如果可以，再看些nginx的底层，比如协议，集群设置，失效转移等。
todo

- 远程调用dubbo方面，可以看下dubbo和zookeeper整合的知识点，再深一步，了解下dubbo底层的传输协议和序列化方式。
todo

- 消息队列方面，可以看下kafka或任意一种组件的使用方式，简单点可以看下配置，工作组的设置，再深入点，可以看下Kafka集群，持久化的方式，以及发送消息是用长连接还是短拦截。
以上仅仅是用3个组件举例，大家还可以看下Redis缓存，日志框架，MyCAT分库分表等。准备的方式有两大类，第一是要会说怎么用，这比较简单，能通过配置文件搭建成一个功能模块即可，第二是可以适当读些底层代码，以此了解下协议，集群和失效转移之类的高级知识点。

1、能通过less命令打开文件，通过Shift+G到达文件底部，再通过?+关键字的方式来根据关键来搜索信息。

2、能通过grep的方式查关键字，具体用法是, grep 关键字 文件名，如果要两次在结果里查找的话，就用grep 关键字1 文件名 | 关键字2 --color。最后--color是高亮关键字。

3、能通过vi来编辑文件。

4、能通过chmod来设置文件的权限。

当然，还有更多更实用的Linux命令，但在实际面试过程中，不少候选人连一条linux命令也不知道。还是这句话，你哪怕知道些很基本的，也比一般人强了。
通读一段底层代码，作为加分项

如何证明自己对一个知识点非常了解?莫过于能通过底层代码来说明。我在和不少工作经验在5年之内的程序员沟通时，不少人认为这很难？确实，如果要通过阅读底层代码了解分布式组件，那难度不小，但如果如下部分的底层代码，并不难懂。

1、ArrayList,LinkedList的底层代码里，包含着基于数组和链表的实现方式，如果大家能以此讲清楚扩容，“通过枚举器遍历“等方式，绝对能证明自己。

2、HashMap直接对应着Hash表这个数据结构，在HashMap的底层代码里，包含着hashcode的put，get等的操作，甚至在ConcurrentHashMap里，还包含着Lock的逻辑。我相信，如果大家在面试中，看看而言ConcurrentHashMap，再结合在纸上边说边画，那一定能征服面试官。

3、可以看下静态代理和动态代理的实现方式，再深入一下，可以看下Spring AOP里的实现代码。

4、或许Spirng IOC和MVC的底层实现代码比较难看懂，但大家可以说些关键的类，根据关键流程说下它们的实现方式。

- 你之前费了千辛万苦（其实方法方向得到，也不用费太大精力）准备的很多技能和说辞，最后应该落实到你的实际项目里。

比如你有过在Linux日志里查询关键字排查问题的经验，在描述时你可以带一句，在之前的项目里我就这样干的。又如，你通过看底层代码，了解了TreeSet和HashSet的差别以及它们的适用范围，那么你就可以回想下你之前做的项目，是否有个场景仅仅适用于TreeSet？如果有，那么你就可以适当描述下项目的需求，然后说，通过读底层代码，我了解了两者的差别，而且在这个实际需求里，我就用了TreeSet，而且我还专门做了对比性试验，发现用TreeSet比HashSet要高xx个百分点。

请记得，“实践经验”一定比“理论经验”值钱，而且大多数你知道的理论上的经验，一定在你的项目里用过。所以，如果你仅仅让面试官感觉你只有“理论经验”，那就太亏了。

- volatile、ThreadLocal的使用场景和原理；
ThreadLocal什么时候会出现OOM的情况？为什么？

JVM里的有几种classloader，为什么会有多种？

什么是双亲委派机制？介绍一些运作过程，双亲委派模型的好处；

什么情况下我们需要破坏双亲委派模型；

常见的JVM调优方法有哪些？可以具体到调整哪个参数，调成什么值？

JVM虚拟机内存划分、类加载器、垃圾收集算法、垃圾收集器、class文件

结构是如何解析的；

说说自定义注解的场景及实现；

Spring AOP的实现原理和场景？

Spring bean的作用域和生命周期；

Spring Boot比Spring做了哪些改进？ Spring 5比Spring4做了哪些改进；

如何自定义一个Spring Boot Starter？

Spring IOC是什么？优点是什么？

SpringMVC、动态代理、反射、AOP原理、事务隔离级别；

Dubbo完整的一次调用链路介绍；

Dubbo支持几种负载均衡策略？

Dubbo Provider服务提供者要控制执行并发请求上限，具体怎么做？

Dubbo启动的时候支持几种配置方式？

了解几种消息中间件产品？各产品的优缺点介绍；

消息中间件如何保证消息的一致性和如何进行消息的重试机制？

Spring Cloud熔断机制介绍；

Spring Cloud对比下Dubbo，什么场景下该使用Spring Cloud？

锁机制介绍：行锁、表锁、排他锁、共享锁；

乐观锁的业务场景及实现方式；

事务介绍，分布式事物的理解，常见的解决方案有哪些，什么事两阶段

提交、三阶段提交；

MySQL记录binlog的方式主要包括三种模式？每种模式的优缺点是什么？

MySQL锁，悲观锁、乐观锁、排它锁、共享锁、表级锁、行级锁；

分布式事务的原理2阶段提交，同步\异步\阻塞\非阻塞；

数据库事务隔离级别，MySQL默认的隔离级别、Spring如何实现事务、

JDBC如何实现事务、嵌套事务实现、分布式事务实现；

SQL的整个解析、执行过程原理、SQL行转列；


八、Redis

Redis为什么这么快？redis采用多线程会有哪些问题？

Redis支持哪几种数据结构；

Redis跳跃表的问题；

Redis单进程单线程的Redis如何能够高并发?

Redis如何使用Redis实现分布式锁？

Redis分布式锁操作的原子性，Redis内部是如何实现的？


九、其他

看过哪些源代码？然后会根据你说的源码问一些细节的问题？（这里主要考察面试者是否对技术有钻研的精神，还是只停留在表面，还是背了几道面经，这个对于很多有强迫症的面试官，如果你连源码都没看过，基本上是会pass掉的，比如我也是这样的！）



Redis基础相关问题

    Redis 是什么？说说它的优点和缺点？Redis与memcached相比有哪些优势？Redis支持哪几种数据类型？Redis主要消耗什么物理资源？Redis有哪几种数据淘汰策略？Redis官方为什么不提供Windows版本？一个字符串类型的值能存储最大容量是多少？如何将 Redis 放到应用程序中？使用 Redis 的时候应用程序是如何进行读写的？为什么Redis需要把所有数据放到内存中？什么是CAP理论？

Redis集群相关问题

    Redis集群方案应该怎么做？都有哪些方案？Redis集群方案什么情况下会导致整个集群不可用？MySQL里有2000w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据？Redis有哪些适合的场景？Redis支持的Java客户端都有哪些？官方推荐用哪个？Redis和Redisson有什么关系？Jedis与Redisson对比有什么优缺点？说说Redis哈希槽的概念？Redis集群的主从复制模型是怎样的？Redis集群会有写操作丢失吗？为什么？Redis集群之间是如何复制的？Redis集群最大节点个数是多少？Redis集群如何选择数据库？Redis中的管道有什么用？

Redis分布式锁等相关问题

    使用过Redis分布式锁么，它是怎么实现的？简述Redis分布式锁的缺陷？讲讲对Redisson实现Redis分布式锁的底层原理的理解？加锁机制，锁互斥机制，watch dog自动延期机制，可重入加锁机制，锁释放机制是什么？Redis 的 Setnx 命令是如何实现分布式锁的？说说对Setnx 的实现锁的原理的理解？如何避免死锁的出现？怎么理解Redis事务？Redis事务相关的命令有哪几个？Redis key的过期时间和永久有效分别怎么设置？Redis如何做内存优化？Redis回收进程如何工作的？使用过Redis做异步队列么，你是怎么用的？有什么缺点？什么是缓存穿透？如何避免？什么是缓存雪崩？何如避免？

Redis结合MySQL 的相关问题

    Redis 如何与 MySQL 数据库结合起来？应用通过 Redis 客户端进行读取并展示，是所有的数据都是这么做吗？在修改数据的时候是修改到 Redis 吗？还是直接修改 MySQL?如果修改 Redis 中数据，那什么时候同步到 MySQL，是被迫的，还是开发人员可控的？如果直接修改 MySQL 中数据，那 Redis 中数据会被同步吗，如何做到的？

如何正确系统的学习Redis高性能缓存数据库

Java集合22题

    ArrayList 和 Vector 的区别。说说 ArrayList,Vector, LinkedList 的存储性能和特性。快速失败 (fail-fast) 和安全失败 (fail-safe) 的区别是什么？hashmap 的数据结构。HashMap 的工作原理是什么?Hashmap 什么时候进行扩容呢？List、Map、Set 三个接口，存取元素时，各有什么特点？Set 里的元素是不能重复的，那么用什么方法来区分重复与否呢? 是用 == 还是 equals()? 它们有何区别?两个对象值相同 (x.equals(y) == true)，但却可有不同的 hash code，这句话对不对?heap 和 stack 有什么区别。Java 集合类框架的基本接口有哪些？HashSet 和 TreeSet 有什么区别？HashSet 的底层实现是什么?LinkedHashMap 的实现原理?为什么集合类没有实现 Cloneable 和 Serializable 接口？什么是迭代器 (Iterator)？Iterator 和 ListIterator 的区别是什么？数组 (Array) 和列表 (ArrayList) 有什么区别？什么时候应该使用 Array 而不是 ArrayList？Java 集合类框架的最佳实践有哪些？Set 里的元素是不能重复的，那么用什么方法来区分重复与否呢？是用 == 还是 equals()？它们有何区别？Comparable 和 Comparator 接口是干什么的？列出它们的区别Collection 和 Collections 的区别。

JVM与调优21题

    Java 类加载过程？描述一下 JVM 加载 Class 文件的原理机制?Java 内存分配。GC 是什么? 为什么要有 GC？简述 Java 垃圾回收机制如何判断一个对象是否存活？（或者 GC 对象的判定方法）垃圾回收的优点和原理。并考虑 2 种回收机制垃圾回收器的基本原理是什么？垃圾回收器可以马上回收内存吗？有什么办法主动通知虚拟机进行垃圾回收？Java 中会存在内存泄漏吗，请简单描述深拷贝和浅拷贝。System.gc() 和 Runtime.gc() 会做什么事情？finalize() 方法什么时候被调用？析构函数 (finalization) 的目的是什么？如果对象的引用被置为 null，垃圾收集器是否会立即释放对象占用的内存？什么是分布式垃圾回收（DGC）？它是如何工作的？串行（serial）收集器和吞吐量（throughput）收集器的区别是什么？在 Java 中，对象什么时候可以被垃圾回收？简述 Java 内存分配与回收策率以及 Minor GC 和 Major GC。JVM 的永久代中会发生垃圾回收么？Java 中垃圾收集的方法有哪些？什么是类加载器，类加载器有哪些？类加载器双亲委派模型机制？

并发编程28题

    Synchronized 用过吗，其原理是什么？你刚才提到获取对象的锁，这个“锁”到底是什么？如何确定对象的锁？什么是可重入性，为什么说 Synchronized 是可重入锁？JVM 对 Java 的原生锁做了哪些优化？48为什么说 Synchronized 是非公平锁？49什么是锁消除和锁粗化？49为什么说 Synchronized 是一个悲观锁？乐观锁的实现原理又是什么？什么是 CAS，它有什么特性？乐观锁一定就是好的吗？跟 Synchronized 相比，可重入锁 ReentrantLock 其实现原理有什么不同？那么请谈谈 AQS 框架是怎么回事儿？请尽可能详尽地对比下 Synchronized 和 ReentrantLock 的异同。ReentrantLock 是如何实现可重入性的？除了 ReetrantLock，你还接触过 JUC 中的哪些并发工具？请谈谈 ReadWriteLock 和 StampedLock。如何让 Java 的线程彼此同步？你了解过哪些同步器？请分别介绍下。CyclicBarrier 和 CountDownLatch 看起来很相似，请对比下呢？Java 线程池相关问题Java 中的线程池是如何实现的？创建线程池的几个核心构造参数？线程池中的线程是怎么创建的？是一开始就随着线程池的启动创建好的吗？既然提到可以通过配置不同参数创建出不同的线程池，那么 Java 中默认实现好的线程池又有哪些呢？请比较它们的异同如何在 Java 线程池中提交线程？什么是 Java 的内存模型，Java 中各个线程是怎么彼此看到对方的变量的？请谈谈 volatile 有什么特点，为什么它能保证变量对所有线程的可见性？既然 volatile 能够保证线程间的变量可见性，是不是就意味着基于 volatile 变量的运算就是并发安全的？请对比下 volatile 对比 Synchronized 的异同。请谈谈 ThreadLocal 是怎么解决并发安全的？很多人都说要慎用 ThreadLocal，谈谈你的理解，使用 ThreadLocal 需要注意些什么？

spring面试专题

    1、什么是 Spring 框架？Spring 框架有哪些主要模块？2、使用 Spring 框架能带来哪些好处？3、什么是控制反转(IOC)？什么是依赖注入？4、请解释下 Spring 框架中的 IoC？5、BeanFactory 和 ApplicationContext 有什么区别？6、Spring 有几种配置方式？7、如何用基于 XML 配置的方式配置 Spring？8、如何用基于 Java 配置的方式配置 Spring？9、怎样用注解的方式配置 Spring？10、请解释 Spring Bean 的生命周期？11、Spring Bean 的作用域之间有什么区别？12、什么是 Spring inner beans？13、Spring 框架中的单例 Beans 是线程安全的么？14、请举例说明如何在 Spring 中注入一个 Java Collection？15、如何向 Spring Bean 中注入一个 Java.util.Properties？16、请解释 Spring Bean 的自动装配？17、请解释自动装配模式的区别？18、如何开启基于注解的自动装配？19、请举例解释@Required 注解？20、请举例解释@Autowired 注解？21、请举例说明@Qualifier 注解？22、构造方法注入和设值注入有什么区别？23、Spring 框架中有哪些不同类型的事件？24、FileSystemResource 和 ClassPathResource 有何区别？25、Spring 框架中都用到了哪些设计模式？

答案详解

设计模式

    1.请列举出在 JDK 中几个常用的设计模式？2.什么是设计模式？你是否在你的代码里面使用过任何设计模式？3.Java 中什么叫单例设计模式？请用 Java 写出线程安全的单例模式4.在 Java 中，什么叫观察者设计模式（observer design pattern）？5.使用工厂模式最主要的好处是什么？在哪里使用？6.举一个用 Java 实现的装饰模式(decorator design pattern)？它是作用于对象层次还是类层次？7.在 Java 中，为什么不允许从静态方法中访问非静态变量？8.设计一个 ATM 机，请说出你的设计思路？9.在 Java 中，什么时候用重载，什么时候用重写？10.举例说明什么情况下会更倾向于使用抽象类而不是接口

答案详解

springboot面试专题

    什么是 Spring Boot？Spring Boot 有哪些优点？什么是 JavaConfig？如何重新加载 Spring Boot 上的更改，而无需重新启动服务器？Spring Boot 中的监视器是什么？如何在 Spring Boot 中禁用 Actuator 端点安全性？如何在自定义端口上运行 Spring Boot 应用程序？什么是 YAML？如何实现 Spring Boot 应用程序的安全性？如何集成 Spring Boot 和 ActiveMQ？如何使用 Spring Boot 实现分页和排序？什么是 Swagger？你用 Spring Boot 实现了它吗？什么是 Spring Profiles？什么是 Spring Batch？什么是 FreeMarker 模板？如何使用 Spring Boot 实现异常处理？您使用了哪些 starter maven 依赖项？什么是 CSRF 攻击？什么是 WebSockets？什么是 AOP？什么是 Apache Kafka？我们如何监视所有 Spring Boot 微服务？

面试答案详解


Netty10题

    BIO、NIO和AIO的区别？NIO的组成？Netty的特点？Netty的线程模型？TCP 粘包/拆包的原因及解决方法？了解哪几种序列化协议？如何选择序列化协议？Netty的零拷贝实现？Netty的高性能表现在哪些方面？NIOEventLoopGroup源码？

    面试答案详解


Redis

    什么是redis?Reids的特点Redis支持的数据类型Redis是单进程单线程的虚拟内存Redis锁读写分离模型数据分片模型Redis的回收策略使用Redis有哪些好处？redis相比memcached有哪些优势？4redis常见性能问题和解决方案MySQL里有2000w数据，redis中只存20w的数据，如何保证redis中的数据都是热点数据245Memcache与Redis的区别都有哪些？Redis 常见的性能问题都有哪些？如何解决？Redis 最适合的场景

    答案详解

面试答案50道详解

### 各种引用

强引用 不会GC
软引用 GC时如果内存不足会gc，其他不会
弱引用 GC时会被GC，无论内存是否足
虚引用 无论任何情况均有可能会被GC
