# Java 200道面试题

## 一、Java 基础

1. JDK 和 JRE 有什么区别？

    JDK包含了Java编译、运行时查看、源码包等工具
    JRE只包含了Java的运行时环境

2. == 和 equals 的区别是什么？

    == 仅判断值，对于指向对象的引用来说，仅判断地址是否相等，对于primitive类型，判断值是否相等
    equals是Object类中定义的方法，默认的实现与==相同，但是子类可以复写此方法，.equals方法的复写有几个原则：
    1. 传递性：A.equals(B) && B.equals(C) 如果是true，则A.equals(C)==true
    2. 交换性：A.equals(B) == B.equals(A)
    3. 如果A==B,则一定有A.eqauls(B)

3. 两个对象的 hashCode()相同，则 equals()也一定为 true，对吗？

    不对，反之一定成立

4. final在java中有什么作用？

    - 修饰类或者接口：不可被继承
    - 修饰变量：初始化后不可被修改

5. Java中的Math.round(-1.5)等于多少？

    - -2.0

6. String属于基础的数据类型吗？

    - 不属于，但是属于java.lang

7. Java中操作字符串都有哪些类？它们之间有什么区别？

    - StringBuilder:
    - StringBuffer:
    - StringJoiner:

8. String str="i"与 String str = new String(“i”)一样吗？

    不一样，字面量会检查常量池有则返回常量池的字符串的引用，没有则创建字符串->放入常量池->返回引用，new String("i")会在堆上创建对象，但不会放入常量池

9. 如何将字符串反转？

    new String(Collections.reverse(String.getBytes))

10. String类的常用方法都有那些？

    match,replace,substring,replaceAll

11. 抽象类必须要有抽象方法吗？

    不一定

12. 普通类和抽象类有哪些区别？

    抽象类不可被实例化

13. 抽象类能使用 final 修饰吗？

    不可以

14. 接口和抽象类有什么区别？

    JDK1.8之前接口方法不可以包含方法体，但1.8以后接口可以包含默认实现

15. java 中 IO 流分为几种？

    BIO、NIO、AIO

16. BIO、NIO、AIO 有什么区别？

    - BIO是Blocking IO的意思。在类似于网络中进行read, write, connect一类的系统调用时会被卡住。
    - NIO是指将IO模式设为“Non-Blocking”模式

17. Files的常用方法都有哪些？

## 二、容器

18. java 容器都有哪些？

    - Set
    - List
    - Map
    - Queue

19. Collection 和 Collections 有什么区别？

    - Collection是接口，所有容器接口都继承这个接口，Collection接口内部规定了接口必须实现的方法如add,addAll,remove,iterator,size等方法
    - Collections是工具类，提供了对Collection的操作方法如random,sort等

20. List、Set、Map 之间的区别是什么？

    - List是有序的，且允许某元素重复
    - Set不允许元素重复
    - Map存储键值对，且key不允许重复

21. HashMap 和 Hashtable 有什么区别？

    - HashMap是继承自AbstractMap类，而HashTable是继承自Dictionary类
    - 前者线程不安全，后者线程安全
    - HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。当get()方法返回null值时，可能是 HashMap中没有该键，也可能使该键所对应的值为null。因此，在HashMap中不能由get()方法来判断HashMap中是否存在某个键， 而应该用containsKey()方法来判断。Hashtable不允许键或者值为null，调用put时会抛空指针异常
    - 计算hash值的方法不同，Hashtable直接取hashcode，HashMap是高16位与低16位按位与
    - 扩容机制不同，HashMap初始16，如果有构造器入参会取右侧最近2的指数次值，好处是计算index可以直接用位运算，坏处是hash碰撞概率大，HashTable初始11，如果有构造入参直接用，用奇数的话index计算慢但是hash碰撞概率小

22. 如何决定使用 HashMap 还是 TreeMap？
    - HashMap：适用于在Map中插入、删除和定位元素。
    - Treemap：适用于按自然顺序或自定义顺序遍历键(key)。

23. 说一下 HashMap 的实现原理？

24. 说一下 HashSet 的实现原理？

    内部维护一个HashMap，所有元素存在HashMap的key中，value统一为类加载时的一个Object对象，所有实现依赖于HashMap的方法

25. ArrayList 和 LinkedList 的区别是什么？

    - ArrayList：数组存储，LinkedList：链表存储

26. 如何实现数组和 List 之间的转换？

    - Arrays.asList()
    - List.toArray()

27. ArrayList 和 Vector 的区别是什么？

    - 后者线程安全

28. Array 和 ArrayList 有何区别？

    - Array是反射包里的类，是数组的相关类
    - ArrayList是List的实现

29. 在 Queue 中 poll()和 remove()有什么区别？

    - 如果是空队列，remove会抛异常，poll不会

30. 哪些集合类是线程安全的？

    - Vector,Hashtable,ConcurrentHashMap

31. 迭代器 Iterator 是什么？

32. Iterator 怎么使用？有什么特点？

33. Iterator 和 ListIterator 有什么区别？

    一、ListIterator有add()方法，可以向List中添加对象，而Iterator不能。

    二、ListIterator和Iterator都有hasNext()和next()方法，可以实现顺序向后遍历。但是ListIterator有hasPrevious()和previous()方法，可以实现逆向（顺序向前）遍历。Iterator就不可以。

    三、ListIterator可以定位当前的索引位置，nextIndex()和previousIndex()可以实现。Iterator 没有此功能。

    四、都可实现删除对象，但是ListIterator可以实现对象的修改，set()方法可以实现。Iterator仅能遍历，不能修改。因为ListIterator的这些功能，可以实现对LinkedList等List数据结构的操作。

34. 怎么确保一个集合不能被修改？

    - Collections.unmodifiableList
    - 自己覆写所有的方法，抛异常

##   三、多线程

35. 并行和并发有什么区别？
    - 并行要求同时发生
    - 并发是交替发生

36. 线程和进程的区别？

    - 进程是程序运行的最小单位
    - 线程是代码运行的最小单位

37. 守护线程是什么？
    - 线程分两种：用户线程和守护线程，如果所有的用户线程全部退出，则守护线程也会全部退出
    - 守护线程有两种，一种是虚拟机启动的线程如GC线程，一种是手动启动的，调用 daemonThread.setDaemon(true);可以将线程设置为守护线程

38. 创建线程有哪几种方式？

    - 继承Thread
    - 实现Runnable

39. 说一下 runnable 和 callable 有什么区别？

    - callable接口内部的call方法有返回值，且抛出异常
    - runnable接口内部的run方法没有返回值

40. 线程有哪些状态？
    - 新建
    - 就绪
    - 运行
    - 阻塞
    - 死亡

41. sleep() 和 wait() 有什么区别？

    - sleep()是Thread静态方法，被调用时调用线程会睡眠不少于参数的时间，被调用时不释放intrinsicLock
    - wait()方法是Object的方法，表明线程会等待其他线程的notify或notifyAll方法唤醒，被调用时会释放同步锁

42. notify()和 notifyAll()有什么区别？

    - notify会唤醒该对象的等待池的某一个线程，notifyAll会唤醒所有

43. 线程的 run()和 start()有什么区别？

    - 线程的run方法是线程对Runnable接口的实现，单独调用时跟普通方法调用一致，需要等方法体所有指令执行完毕后才返回
    - 线程的start方法会异步启动调用的线程，并放到队列中等待CPU调度，而调用start()方法的线程在调用此方法且方法被标记为就绪后立即返回

44. 创建线程池有哪几种方式？

    - Excutors的静态方法
    - new ThreadPoolExcuter

45. 线程池都有哪些状态？

    1. RUNNING

        (1) 状态说明：线程池处在RUNNING状态时，能够接收新任务，以及对已添加的任务进行处理。
        (2) 状态切换：线程池的初始化状态是RUNNING。换句话说，线程池被一旦被创建，就处于RUNNING状态，并且线程池中的任务数为0！

    2. SHUTDOWN

        (1) 状态说明：线程池处在SHUTDOWN状态时，不接收新任务，但能处理已添加的任务。
        (2) 状态切换：调用线程池的shutdown()接口时，线程池由RUNNING -> SHUTDOWN。

    3. STOP

        (1) 状态说明：线程池处在STOP状态时，不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。
        (2) 状态切换：调用线程池的shutdownNow()接口时，线程池由(RUNNING or SHUTDOWN ) -> STOP。

    4. TIDYING

        (1) 状态说明：当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING状态。当线程池变为TIDYING状态时，会执行钩子函数terminated()。terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；可以通过重载terminated()函数来实现。
        (2) 状态切换：当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 SHUTDOWN -> TIDYING。
        当线程池在STOP状态下，线程池中执行的任务为空时，就会由STOP -> TIDYING。

    5. TERMINATED

        (1) 状态说明：线程池彻底终止，就变成TERMINATED状态。 
        (2) 状态切换：线程池处在TIDYING状态时，执行完terminated()之后，就会由 TIDYING -> TERMINATED。

46. 线程池中 submit()和 execute()方法有什么区别？

    submit方法返回一个Future对象，通过调用Future.get来获取返回值并捕获可能的异常

47. 在 java 程序中怎么保证多线程的运行安全？

48. 多线程锁的升级原理是什么？

49. 什么是死锁？

    两个线程互相竞争对方已持有的锁

50. 怎么防止死锁？

    - 对于需要竞争外部锁的方法体，需要注意申请锁的顺序
    - 申请锁要有超时及超时处理
    - 对于涉及到锁申请的位置，要写明申请的锁

51. ThreadLocal是什么？有哪些使用场景？

    - ThreadLocal是一个类，管理存储于线程对象上的实例属性,threadLocals和inheritableThreadLocals

52. 说一下 synchronized 底层实现原理？

53. synchronized 和 volatile 的区别是什么？

    - synchronized修饰方法或者方法体
    - volatile修饰变量，表明在涉及到此变量的StoreLoad指令时不可进行指令重排序

54. synchronized 和 Lock 有什么区别？

    - 前者是JVM进行锁申请和释放，且在某些实现中会进行锁的融合

55. synchronized 和 ReentrantLock 区别是什么？

    synchronized 竞争锁时会一直等待；ReentrantLock 可以尝试获取锁，并得到获取结果
    synchronized 获取锁无法设置超时；ReentrantLock 可以设置获取锁的超时时间
    synchronized 无法实现公平锁；ReentrantLock 可以满足公平锁，即先等待先获取到锁
    synchronized 控制等待和唤醒需要结合加锁对象的 wait() 和 notify()、notifyAll()；ReentrantLock 控制等待和唤醒需要结合 Condition 的 await() 和 signal()、signalAll() 方法
    synchronized 是 JVM 层面实现的；ReentrantLock 是 JDK 代码层面实现
    synchronized 在加锁代码块执行完或者出现异常，自动释放锁；ReentrantLock 不会自动释放锁，需要在 finally{} 代码块显示释放

    补充一个相同点：都可以做到同一线程，同一把锁，可重入代码块。

56. 说一下 atomic 的原理？

    CAS

##    四、反射

57. 什么是反射？

    程序可以访问、检测和修改它本身状态或行为的一种能力

58. 什么是 java 序列化？什么情况下需要序列化？

    Java对象序列化即将某个实例的当前状态转换为字节流
    远程调用，异常恢复

59. 动态代理是什么？有哪些应用？

    动态控制调用者实际调用的方法内容
    权限控制、AOP、Spring所以涉及到注入的Bean的调用

60. 怎么实现动态代理？

    - 实现InvocationHandler接口
    - Cglib框架

## 五、对象拷贝

61. 为什么要使用克隆？

    - 复制源对象的状态但又不想后续状态变化时影响源对象

62. 如何实现对象克隆？

    - 调用clone方法

63. 深拷贝和浅拷贝区别是什么？

    - 深拷贝，对于非primative类型，递归调用clone方法
    - 浅拷贝，对于非primative类型，直接复制对应的引用

## 六、Java Web

64. jsp 和 servlet 有什么区别？

    - jsp是模板引擎
    - sevlet是JavaEE规定的处理网络请求的接口

65. jsp 有哪些内置对象？作用分别是什么？

66. 说一下 jsp 的 4 种作用域？

67. session 和 cookie 有什么区别？

    - session信息存储在服务端，通过cookie存储的sessionId进行识别

68. 说一下 session 的工作原理？

    - 服务端生成状态，存储，将ID放到Cookie里传回客户端

69. 如果客户端禁止 cookie 能实现 session 还能用吗？

    - 放到LocalStorege或SessionStorege

70. spring mvc 和 struts 的区别是什么？

71. 如何避免 sql 注入？

    - 使用PrepareStatement
    - Mybatis参数使用#

72. 什么是 XSS 攻击，如何避免？

    - 网页执行了恶意的js代码
    - HTML转义，应当尽量避免自己写转义库，而应当采用成熟的、业界通用的转义库。
    - 对于链接跳转，如 <a href="xxx" 或 location.href="xxx"，要检验其内容，禁止以 javascript: 开头的链接，和其他非法的 scheme。
    - 避免拼接 HTML

73. 什么是 CSRF 攻击，如何避免？

    - 恶意网站B请求已登录的正常网站A的资源，会自动带上A的cookie，而A服务器是通过cookie（或者cookie中存储的sessionId对应的session）来进行校验的，会自动执行此请求
    - 解决方案：
        - 验证 HTTP Referer 字段
        - 在请求地址中添加 token 并验证

## 七、异常

74. throw 和 throws 的区别？

    - throw 后接Excetion实例表示抛出一个异常
    - throws 在方法的参数列表后表示此方法可能抛出某异常

75. final、finally、finalize 有什么区别？

    - final是修饰符
    - finally是try-catch的关键字，表示无论是否catch异常都会执行的部分
    - finalize是对象被销毁时可能执行的方法

76. try-catch-finally 中哪个部分可以省略？

    finally

77. try-catch-finally 中，如果 catch 中 return 了，finally 还会执行吗？

    会

78. 常见的异常类有哪些？

    NullPointer
    OutOfMemorry
    InterruptedException

##  八、网络

79. http 响应码 301 和 302 代表的是什么？有什么区别？

    - 301永久转移
    - 302临时转移

80. forward 和 redirect 的区别？

    - forward是服务端的请求转发
    - redirect是请求重定向，要求客户端再次请求对应的网址

81. 简述 tcp 和 udp的区别？

    - tcp保证网络包按顺序到达，有验证
    - udp不保证按顺序到达，且发送端不关心是否到达

82. tcp 为什么要三次握手，两次不行吗？为什么？

    - 三次握手：为了保证TCP连接是全双工的，需要保证各自确认自己和对方的发送接收正常

        |序号|客户端确认客户端状态|客户端确认服务端状态|服务端确认服务端状态|服务端确认客户端状态|
        |-|-|-|-|-|
        |1:客户端->服务端|发送/-|-/-|-/接收|发送/-|
        |2:服务端->客户端|发送/接收|发送/接收|发送/接收|发送/-|
        |3:客户端->服务端|发送/接收|发送/接收|发送/接收|发送/接收|

    - 四次挥手：当收到对方的FIN报文时，仅表示对方不再发送数据但还能接收收据，我们也未必把全部数据都发给了对方，所以我们可以立即close，也可以发送一些数据给对方后，再发送FIN报文给对方表示同意关闭连接。因此我们的ACK和FIN一般会分开发送。

83. 说一下 tcp 粘包是怎么产生的？
    - 数据包大于缓冲区，发生拆包
    - 多个数据包粘在一起发送

84. OSI 的七层模型都有哪些？
    1. 应用层
    2. 协议层
    3. 会话层
    4. 传输层
    5. 网络层
    6. 数据链路层
    7. 物理层

85. get 和 post 请求有哪些区别？
    1. GET产生一个TCP数据包；POST产生两个TCP数据包。
    2. 而对于POST，浏览器先发送header，服务器响应100 continue，浏览器再发送data，服务器响应200 ok（返回数据）。
    3. 表面区别最直观的表现就是get请求的参数跟在url后面，post请求参数跟在request body中。
    get请求会在浏览器中缓存，而post请求会进行第二次提交。
    get请求参数有长度限制，post参数无长度限制。
    get请求在浏览器回退时是无害的，post请求在回退时会被再次提交。
    get请求的参数只接受ASII字符，而post无限制。

86. 如何实现跨域？
    1. 服务端响应OPTIONS请求
    2. 静态资源服务器配置代理服务
    3. JSONP跨域

87. 说一下 JSONP 实现原理？

    服务端返回的报文中增加调用前端处理函数的内容，如前端function名叫handleResp，则返回报文为"handleResp({"name":"john"})"

##   九、设计模式

88. 说一下你熟悉的设计模式？

    - 工厂模式
    - 简单工厂模式
    - 单例模式
    - 代理模式
    - 装饰模式
    - 外观模式
    - 迭代器模式
    - 命令模式
    - 观察者模式
    - 模板模式

89. 简单工厂和抽象工厂有什么区别？

    - 简单工厂将所有的根据入参来决定调用哪一个初始化策略都内含在类里面，违反了开闭原则
    - 抽象工厂模式解决了简单工厂的弊端，但是每一个产品都要对应一个工厂实现类

    十、Spring/Spring MVC

90. 为什么要使用 spring？

    - IOC容器，解决了DI问题

91. 解释一下什么是 aop？

    - 面向切面编程

92. 解释一下什么是 ioc？

    - 反转控制，即自己不需要控制依赖

93. spring 有哪些主要模块？

    - Spring-context
    - Spring-beans
    - Spring-aop
    - Spring-mvc
    - Spring-core
    - Spring-cloud

94. spring 常用的注入方式有哪些？

    - 自动注入和构造器注入

95. spring 中的 bean 是线程安全的吗？

    bean没有线程安全这一说，默认是单例，换句话说如果bean内部method没有作特殊处理，则不安全

96. spring 支持几种 bean 的作用域？

    - singleton
    - prototype
    - request
    - session
    - http-session

97. spring 自动装配 bean 有哪些方式？

    - no：默认方式，手动装配方式，需要通过ref设定bean的依赖关系
    - byName：根据bean的名字进行装配，当一个bean的名称和其他bean的属性一致，则自动装配
    - byType：根据bean的类型进行装配，当一个bean的属性类型与其他bean的属性的数据类型一致，则自动装配
    - constructor：根据构造器进行装配，与 byType 类似，如果bean的构造器有与其他bean类型相同的属性，则进行自动装配
    - autodetect：如果有默认构造器，则以constructor方式进行装配，否则以byType方式进行装配

98. spring 事务实现方式有哪些？

    （1）编程式事务管理对基于 POJO 的应用来说是唯一选择。我们需要在代码中调用beginTransaction()、commit()、rollback()等事务管理相关的方法，这就是编程式事务管理。
    （2）基于 TransactionProxyFactoryBean的声明式事务管理
    （3）基于 @Transactional 的声明式事务管理
    （4）基于Aspectj AOP配置事务

99. 说一下 spring 的事务隔离？

    数据库的隔离级别:
    - 读未提交
    - 读已提交
    - 可重复读
    - 串行化

    Spring的事务隔离: 除了上面的隔离级别外，还有DEFAULT，即数据库默认隔离级别

    数据库的默认隔离级别:Mysql是可重复读，Oracle是读已提交

100. 说一下 spring mvc 运行流程

    - 初始化WebApplicationContext，主要实现IOC的Bean初始化
    - 初始化DispatcherServlet，以及其内部的WebApplicationContext

101. spring mvc 有哪些组件？
    - HandlerMapping
    - HandlerAdapter
    - HandlerExceptionResolver
    - ViewResolver
    - RequestToViewNameTranslator
    - LocalResolver
    - ThemeResolver
    - MultipartResolver
    - FlashMapManager

102. @RequestMapping 的作用是什么？

    指定URL的配置

103. @Autowired 的作用是什么？

    自动注入

## 十一、Spring Boot/Spring Cloud

104. 什么是 spring boot？

    简化配置，约定大于配置，在各个starter中均定义了默认配置

105. 为什么要用 spring boot？

    集成化程度高，部署简单

106. spring boot 核心配置文件是什么？

    application.properties

107. spring boot 配置文件有哪几种类型？它们有什么区别？

    properties、xml、yml

108. spring boot 有哪些方式可以实现热部署？

    - maven 插件 springloaded
    - 依赖 spring-boot-devtools 热部署模块

109. jpa 和 hibernate 有什么区别？

110. 什么是 spring cloud？

111. spring cloud 断路器的作用是什么？

112. spring cloud 的核心组件有哪些？

## 十二、Hibernate

113. 为什么要使用 hibernate？

114. 什么是 ORM 框架？

115. hibernate 中如何在控制台查看打印的 sql 语句？

116. hibernate 有几种查询方式？

117. hibernate 实体类可以被定义为 final 吗？

118. 在 hibernate 中使用 Integer 和 int 做映射有什么区别？

119. hibernate 是如何工作的？

120. get()和 load()的区别？

121. 说一下 hibernate 的缓存机制？

122. hibernate 对象有哪些状态？

123. 在 hibernate 中 getCurrentSession 和 openSession 的区别是什么？

124. hibernate 实体类必须要有无参构造函数吗？为什么？

## 十三、Mybatis

125. mybatis 中 #{}和 ${}的区别是什么？

    - #{}会将传入的数据都当成一个字符串，自动防sql注入
    - ${}原样放入

126. mybatis 有几种分页方式？

127. RowBounds 是一次性查询全部结果吗？为什么？

128. mybatis 逻辑分页和物理分页的区别是什么？

129. mybatis 是否支持延迟加载？延迟加载的原理是什么？

130. 说一下 mybatis 的一级缓存和二级缓存？

131. mybatis 和 hibernate 的区别有哪些？

132. mybatis 有哪些执行器（Executor）？

133. mybatis 分页插件的实现原理是什么？

134. mybatis 如何编写一个自定义插件？

## 十四、RabbitMQ

135. rabbitmq 的使用场景有哪些？

136. rabbitmq 有哪些重要的角色？

137. rabbitmq 有哪些重要的组件？

138. rabbitmq 中 vhost 的作用是什么？

139. rabbitmq 的消息是怎么发送的？

140. rabbitmq 怎么保证消息的稳定性？

141. rabbitmq 怎么避免消息丢失？

142. 要保证消息持久化成功的条件有哪些？

143. rabbitmq 持久化有什么缺点？

144. rabbitmq 有几种广播类型？

145. rabbitmq 怎么实现延迟消息队列？

146. rabbitmq 集群有什么用？

147. rabbitmq 节点的类型有哪些？

148. rabbitmq 集群搭建需要注意哪些问题？

149. rabbitmq 每个节点是其他节点的完整拷贝吗？为什么？

150. rabbitmq 集群中唯一一个磁盘节点崩溃了会发生什么情况？

151. rabbitmq 对集群节点停止顺序有要求吗？

## 十五、Kafka

152. kafka 可以脱离 zookeeper 单独使用吗？为什么？

153. kafka 有几种数据保留的策略？

154. kafka 同时设置了 7 天和 10G 清除数据，到第五天的时候消息达到了 10G，这个时候 kafka 将如何处理？

155. 什么情况会导致 kafka 运行变慢？

156. 使用 kafka 集群需要注意什么？

## 十六、Zookeeper

157. zookeeper 是什么？

158. zookeeper 都有哪些功能？

159. zookeeper 有几种部署模式？

160. zookeeper 怎么保证主从节点的状态同步？

161. 集群中为什么要有主节点？

162. 集群中有 3 台服务器，其中一个节点宕机，这个时候 zookeeper 还可以使用吗？

163. 说一下 zookeeper 的通知机制？

十七、MySql

164. 数据库的三范式是什么？

    - 关系模式R所有属性不可分解为更小的数据单位，称满足第一范式即1NF
    - 关系模式R所有属性均满足1NF,且R的所有非主属性均完全依赖于R的每一个候选关键属性，称满足第二范式即2NF
    - 关系模式R所有属性的依赖均直接依赖不需要传递称为满足第三范式即3NF

165. 一张自增表里面总共有 7 条数据，删除了最后 2 条数据，重启 mysql 数据库，又插入了一条数据，此时 id 是几？

    5，重启会将自增初始值重置为最大值加1

166. 如何获取当前数据库版本？

    select @@version;

167. 说一下 ACID 是什么？

    事务的4个属性：
    A：Atomic 原子性
    C：Consistency 一致性
    I：Isolation 隔离性
    D：Durability 持久性

168. char 和 varchar 的区别是什么？

    - char是定长的，长度不够会补0，索引性能好
    - varchar是变长的，长度单位是字节
    - nvarchar也是变长的，长度单位是字符

169. float 和 double 的区别是什么？

    它们都代表浮点数。阿FLOAT用于单精度，而DOUBLE是双精度数字。

    MySQL的单精度值使用四个字节，双精度值使用八个字节。

170. mysql 的内连接、左连接、右连接有什么区别？

    内连接仅连接左右均非空的行，
    左连接展示左表所有行，
    右连接展示右表所有行

171. mysql 索引是怎么实现的？

    - innoDb引擎：每个索引创建一个B+树，其中主键索引树的叶子节点存储（主键索引，数据），其他索引树存储（索引，主键索引）

172. 怎么验证 mysql 的索引是否满足需求？

    - explain查看sql的执行计划
    - 监控sql的执行效率

173. 说一下数据库的事务隔离？

    - 读未提交
    - 读已提交
    - 可重复读
    - 串行化    

174. 说一下 mysql 常用的引擎？

    - MyISAM,支持全文索引，不支持事务，保存行数
    - innodb，不支持全文索引，支持事务，不保存行数

175. 说一下 mysql 的行锁和表锁？

    - (BLog)[https://blog.csdn.net/tr1912/article/details/81323256]
    - 表锁：偏向MyISAM引擎，开销小，加锁快，无死锁，粒度大，并发性能差:
        - 加锁：lock table mytable read;
        - 释放所有锁：unlock tables;
        - 查看加锁的表：show open tables;In_use 列为1 的就是加锁的表
        - 分析：show status like 'table%';
            - Table_locks_immediate：产生表级锁定的次数，表示可以立即获取锁的查询次数，每立即获取锁值加1 。
            - Table_locks_waited：出现表级锁定争用而发生等待的次数(不能立即获取锁的次数，每等待一次锁值加1)，此值高则说明存在着较严重的表级锁争用情况。
    - 行锁：偏向InnoDB引擎，粒度小，开销大，并发性能高，可能产生死锁
    - 间隙锁：如果检索的数据条件不是相等而是区间，InnoDB会给符合条件的已有数据记录的索引项加锁；对于键值在条件范围内但实际上没有对应记录的，称之为GAP，InnoDB也会对这些GAP加锁，称之为间隙锁
        - 问题：当插入操作涉及到已加间隙锁的GAP对应的键值时，由于间隙锁的存在，相应线程无法进行插入操作，造成性能下降

176. 说一下乐观锁和悲观锁？

    - 悲观锁，先获取锁再操作，缺点是对竞争概率小的场景，由于每次操作均需要加锁，会造成性能损失，对于MySQL来说需要显式进行加锁比如" select * from * for update"，需要注意的是MySQL在执行相应的操作时，会主动锁定所有相关行的行锁
    - 乐观锁，先操作，如果涉及到数据竞争，则尝试获取锁，如果没有获取到锁，则回滚操作。优点是在低竞争条件下性能较好，缺点是如果获取锁失败的概率大，因为回滚成本较高，性能会降低

177. mysql 问题排查都有哪些手段？

    - 事物级别
        select @@global.tx_isolation;
        复制代码输出数据当前状态
        --返回最近一次死锁场景，等等信息
        SHOW ENGINE INNODB STATUS ;
        复制代码可用于排查死锁问题，锁定行数等问题
        查询数据库连接信息
        select * from information_schema.PROCESSLIST
        复制代码查询事务信息
        --观察事务启动时间，判断是否为最近的创建的
        select * from information_schema.INNODB_TRX;
        复制代码查询数据库锁等待信息
        --如果存在数据表示当前存在所等待情况
        select * from information_schema.INNODB_LOCK_WAITS;
        复制代码手动杀掉某个进程
        --来源于select * from information_schema.INNODB_TRX;
        kill trx_mysql_thread_id; 
        复制代码数据库客户端连接ip统计
        --用户判断客户端连接数问题
        SELECT
        substr(host, 1, instr(host, ':') - 1),
        count(*)
        FROM information_schema.processlist
        WHERE command <> 'Binlog Dump'
        GROUP BY substr(host, 1, instr(host, ':') - 1)
        ORDER BY count(*) DESC;

        查询数据库指定连接的当前执行sql
        SELECT *
        FROM performance_schema.events_statements_current
        WHERE THREAD_ID IN (SELECT THREAD_ID
                            FROM performance_schema.threads
                            WHERE PROCESSLIST_ID = 1333192);
        复制代码查询锁等待的前后事物和客户端调用的简单sql
        SELECT
        '程序等待获取锁' name,
        t1.requesting_trx_id,
        p1.HOST,
        p1.DB,
        p1.INFO,
        t4.trx_mysql_thread_id,
        t4.trx_state,
        t4.trx_started,
        t1.requested_lock_id,
        t2.lock_mode  req_lockmode,
        t2.lock_type  req_locktype,
        t2.lock_table req_locktable,
        t2.lock_index req_lockindex,
        #   t2.lock_page  req_lockpage,
        #   t2.lock_rec   req_lockrec,
        t2.lock_data  req_lockdata,
        '程序hold事务不释放' name,
        t1.blocking_trx_id,
        t5.trx_mysql_thread_id,
        p1.HOST,
        p1.DB,
        p1.INFO,
        t5.trx_state,
        t5.trx_started,
        t1.blocking_lock_id,
        t3.lock_mode  blocking_lockmode,
        t3.lock_type  blocking_locktype,
        t3.lock_table blocking_locktable,
        t3.lock_index blocking_lockindex,
        #   t3.lock_page  blocking_lockpage,
        #   t3.lock_rec   blocking_lockrec,
        t3.lock_data  blocking_lockdata
        FROM information_schema.INNODB_LOCK_WAITS t1
        LEFT JOIN information_schema.INNODB_LOCKS t2
            ON (t1.requesting_trx_id = t2.lock_trx_id)
        LEFT JOIN information_schema.INNODB_LOCKS t3
            ON (t1.blocking_trx_id = t3.lock_trx_id)
        LEFT JOIN information_schema.INNODB_TRX t4
            ON (t4.trx_id = t1.requesting_trx_id)
        LEFT JOIN information_schema.INNODB_TRX t5
            ON (t5.trx_id = t1.blocking_trx_id)
        LEFT JOIN information_schema.processlist p1
            ON (p1.id = t4.trx_mysql_thread_id)
        LEFT JOIN information_schema.processlist p2
            ON (p1.id = t5.trx_mysql_thread_id);
        复制代码数据库客户端连接简介
        SELECT
        p.id,
        p.user,
        p.host,
        p.db,
        p.state,
        p.info,
        #   t.trx_id,
        #   t.trx_state,
        #   t.trx_mysql_thread_id,
        #   t.trx_query,
        #   t.trx_ad,
        t.*
        FROM information_schema.processlist p
        LEFT JOIN information_schema.INNODB_TRX t
        ON (p.id = t.trx_mysql_thread_id) where p.DB='test';

178. 如何做 mysql 的性能优化？

    - 监控sql性能，锁定影响效率的sql
    - 执行计划，查看索引命中
    - 查看锁状态

## 十八、Redis

179. redis 是什么？都有哪些使用场景？

    - redis存储时一个内存数据库，C语言写成，支持多种数据结构，支持单点部署和集群部署，有数据持久化措施

180. redis 有哪些功能？

181. redis 和 memecache 有什么区别？

    - memecache 把数据全部存在内存之中，断电后会挂掉，数据不能超过内存大小，redis有部份存在硬盘上，这样能保证数据的持久性。
    - redis数据支持类型比memecache要多
    - redis仅支持linux部署

182. redis 为什么是单线程的？

    - CPU不是redis存储的瓶颈，网络带宽和内存带宽
    - 采用epoll多路复用，单线程避免了线程上下文切换等重量级操作

183. 什么是缓存穿透？怎么解决？

    - 大量请求未命中缓存导致降级到数据库查找
    - 对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃

184. redis 支持的数据类型有哪些？

    - string,set,list,hash,zset

185. redis 支持的 java 客户端都有哪些？

    - Redisson,Jedis，lettuce等等，官方推荐使用Redisson。

186. jedis 和 redisson 有哪些区别？

    - jedis是对Redis命令的Java封装，本质是Redis客户端，Redisson除了提供基础的访问API外，通过内存网格提供了很多原生的实现，比如分布式锁，Map映射等

187. 怎么保证缓存和数据库数据的一致性？

    - 如果系统严格要求缓存与数据库的一致性，那么需要将所有的读写请求串行化到Jvm的Queue里，优点是一定保证缓存和数据库的一致性，缺点是串行化会导致读写性能严重下降
    - Cache Aside Pattern:读的时候，先读缓存，没有的话，降级到数据库，读取出来，放入缓存，返回响应；写的时候先更新数据库再删除缓存
    - 对于高并发状态下多线程交互更新同一"行"数据库和缓存的时候，有可能出现**线程2删除缓存->线程1确认没有缓存，降级查询并更新缓存为旧数据->线程2更新数据库为新数据**的情况，导致不一致；解决方案为将写操作hash后，发送到一个jvm内部维护的操作队列里，同样读操作也发送到一个操作队列里，此队列维护了所有对应的操作的读写操作，本质是操作串行化，缺点是性能降低，如果一段时间

188. redis 持久化有几种方式？

    - RDB持久化：对redis中的数据进行周期性的持久化，默认的持久化方式，根据设置的间隔存储全量快照文件，方式是fork一个子进程，子进程将内存中的页面写入文件，优点是恢复快，缺点是会丢失数据
    - AOF：AOF 机制对每条写入命令作为日志，以 append-only 的模式写入一个日志文件中，在 redis 重启的时候，可以通过回放 AOF 日志中的写入指令来重新构建整个数据集

189. redis 怎么实现分布式锁？

    - 加锁：SETNX,NX(NOT EXIST),如果返回OK说明加锁成功，否则说明锁被占用，需要轮询
    - 释放锁：通过LUA脚本，将GET(KEY)==ORI_VALUE? DEL_LOCK:return false;

190. redis 分布式锁有什么缺陷？

    - 主从切换会出现问题：如果一个线程在主服务器上加锁，但是主服务器在同步到从服务器之前下线，此时如果开启了哨兵模式，哨兵将从服务器升级为主服务器，这样会丢失互斥锁，即允许其他线程再次获得此锁
    - 缓存易失性：如果锁字段在内存淘汰时被淘汰（如设置了淘汰为allkeys-random，随机淘汰），会导致丢失锁信息

191. redis 如何做内存优化？

    - 压缩key和Value：使用缩写、Gzip
    - 使用Set不要用append,Append会多申请一次内存空间
    - 截至redis 4.0 只要hash map的key的数量小于等于 512，value的大小小于等于64字节。hash map的底层数据结构就是ziplist，反之则是hash table。这样当hash map里面的数据不是很多时，用ziplist来实现即节省了内存，效率也不用下降很多，因为数据不多。但生产中如果我们有这样的一个hash map，它的key有512个，value有只有一个的大小超过了64字节，刚好是65。这个时候Redis 就默认把ziplist换成了hash table，这是不是很坑！这时可以自己优化转换规则：config set hash-max-ziplist-entries 65。这样value大于65字节才转换成hash table
    - 整数对象使用常量共享池，大小默认为0-9999，所以能用整数且大概率命中共享池的地方，尽量用整数

192. redis 淘汰策略有哪些？

    - noeviction：内存达到阈值时，涉及新申请内存的操作会报错
    - allkeys-lru：优先移除最近未使用的key
    - volatile-lru：在设置了过期时间的keys移除最近未使用的key
    - allkeys-random：全量key中随机移除
    - volatile-random：在设置了过期时间的keys随机移除
    - volatile-ttl：在设置了过期时间的keys移除更早过期时间的

193. redis 常见的性能问题有哪些？该如何解决？

    - Master写内存快照，save命令调度rdbSave函数，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务，所以Master最好不要写内存快照。
    - Master AOF持久化，如果不重写AOF文件，这个持久化方式对性能的影响是最小的，但是AOF文件会不断增大，AOF文件过大会影响Master重启的恢复速度。
    - Master调用BGREWRITEAOF重写AOF文件，AOF在重写的时候会占大量的CPU和内存资源，导致服务load过高，出现短暂服务暂停现象。

十九、JVM

194. 说一下 jvm 的主要组成部分？及其作用？

    - ClassLoader
    - 运行时数据区
    - 执行引擎
    - native方法接口
    - native方法库

195. 说一下 jvm 运行时数据区？

    - 方法区：存储类信息和常量池
    - 堆：存储对象
    - 虚拟机栈：线程内部的存储Java代码运行的栈帧的区域
    - 本地方法栈：线程内部存储native代码的运行的栈帧的区域
    - 程序计数器：用于保存当前线程执行的内存地址 

196. 说一下堆栈的区别？

    - 栈内存用来存储局部变量和方法调用、对象引用，堆内存存储对象
    - 堆内存是线程共享的，栈内存是线程私有的
    - 栈空间不足抛出:StackOverFlowError,堆空间不足抛出：OutOfMemoryError

197. 队列和栈是什么？有什么区别？

    - 存储结构，队列FIFO，栈FILO

198. 什么是双亲委派模型？

    - 某个ClassLoader收到加载类的指令时，先判断本身是否可以加载，如果没有，就去父ClassLoader加载，最顶层的为BootStrap ClassLoader，采用NativeCode实现，是JVM的一部分，负责加载rt.jar
    - BootStrap ClassLoader加载完成后，加载ExtentionClassloader(jre/lib/bin/ext.jar)和AppClassloader($Classpath)

199. 说一下类加载的执行过程？

    - 1、装载：查找和导入Class文件 ，生成对应的Class 对象

    - 2、链接：其中解析步骤是可以选择的 

    - （a）检查：检查载入的class文件数据的正确性

    - （b）准备：给类的静态变量分配存储空间 

    - （c）解析：将符号引用转成直接引用 

    - 3、初始化：对静态变量，静态代码块执行初始化工作

200. 怎么判断对象是否可以被回收？

    - 从GC roots出发，如果一个对象不可达，则对象可被回收，GC roots包括下面几类：
        - 虚拟机栈中引用的对象
        - 方法区中静态引用的对象
        - 本地方法栈中引用的对象

201. java 中都有哪些引用类型？

    - 强引用：GC不回收
    - 弱引用：GC扫描到一个对象只有弱引用，如果堆空间不足需要进行GC，则对象会被回收
    - 软引用：GC扫描到一个对象只有软引用，不管是否需要进行GC，对象都有可能会被回收
    - 虚引用：如果一个对象只有虚引用，则认为此对象没有引用，随时会被回收

202. 说一下 jvm 有哪些垃圾回收算法？

    - 标记-清除
    - 标记-整理
    - 复制算法

203. 说一下 jvm 有哪些垃圾回收器？

    - Serial
    - Serial Old
    - Par New
    - Parallel Scavenge
    - Parallel Old
    - CMS
    - G1

204. 详细介绍一下 CMS 垃圾回收器？

    - CMS是老年代和永久代垃圾回收器，不收集年轻代
    - CMS是预处理垃圾回收器，需要在old内存用尽前开始GC，默认阈值92%，可调整
    - 只能配合Parallel New或者Serial垃圾回收器来进行新生代回收

205. 新生代垃圾回收器和老生代垃圾回收器都有哪些？有什么区别？

    - 新生代：Serial,Par New,Parallel Scavenge
    - 老生代：Serial Old，Parallel Old,CMS,

206. 简述分代垃圾回收器是怎么工作的？

    - 堆内存分为Eden、Survivor、Tenured/Old区，前两者叫新生代，最后叫老生代
    - Eden：Survior一般是1：8，Survior一般有两个
    - 大部分对象在Eden区就被回收，Eden区经过N次GC仍旧未回收的对象放入Survivor区
    - Eden满了或达到一定比例(CMS)后会触发Minor GC
    - 对象第一次存活会从Eden到Survivor1，第二次存活会从Survivor1到Survivor2
    - 对象存活次数达到阈值（默认15），会存入老生代
    - Old区达到一定大小，触发Major GC，清理老生代
    - Old区满了，触发Full GC
    - 对象大小达到阈值的，会直接放入老生代

207. 说一下 jvm 调优的工具？

    - jdk自带的jconsole
    - jdk自带的VisualVM
    - 第三方MAT:内存dump文件分析工具
    - GChisto

208. 常用的 jvm 调优的参数都有哪些？

    –verbose:gc在虚拟机发生内存回收时在输出设备显示信息
    -Xloggc:filename把GC相关日志信息记录到文件以便分析
    -XX:-HeapDumpOnOutOfMemoryError当首次遭遇OOM时导出此时堆中相关信息
    -XX:OnError="<cmdargs>;<cmd args>" 出现致命ERROR之后运行自定义命令
    -XX:-PrintClassHistogram遇到Ctrl-Break后打印类实例的柱状信息，与jmap -histo功能相同
    -XX:-PrintConcurrentLocks遇到Ctrl-Break后打印并发锁的相关信息，与jstack -l功能相同
    -XX:-PrintGC每次GC时打印相关信息
    -XX:-PrintGCDetails每次GC时打印详细信息
    -XX:-PrintGCTimeStamps打印每次GC的时间戳
    -XX:+PrintGCApplicationStoppedTime打印垃圾回收期间程序暂停的时间
    -XX:+PrintHeapAtGC打印GC前后的详细堆栈信息
    -XX:+PrintTenuringDistribution查看每次minor GC后新的存活周期的阈值,即在年轻代survivor中的复制次数.
    -XX:-TraceClassLoading跟踪类的加载信息

- 双亲委派模型的优点
- Spring AOP的原理
- 消息队列
- Collections类的排序算法
