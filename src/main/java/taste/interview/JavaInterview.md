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

    - myissam,支持全文索引
    - innodb，不支持全文索引

175. 说一下 mysql 的行锁和表锁？

176.说一下乐观锁和悲观锁？

177.mysql 问题排查都有哪些手段？

178.如何做 mysql 的性能优化？

十八、Redis

179.redis 是什么？都有哪些使用场景？

180.redis 有哪些功能？

181.redis 和 memecache 有什么区别？

182.redis 为什么是单线程的？

183.什么是缓存穿透？怎么解决？

184.redis 支持的数据类型有哪些？

185.redis 支持的 java 客户端都有哪些？

186.jedis 和 redisson 有哪些区别？

187.怎么保证缓存和数据库数据的一致性？

188.redis 持久化有几种方式？

189.redis 怎么实现分布式锁？

190.redis 分布式锁有什么缺陷？

191.redis 如何做内存优化？

192.redis 淘汰策略有哪些？

193.redis 常见的性能问题有哪些？该如何解决？

十九、JVM

194.说一下 jvm 的主要组成部分？及其作用？

195.说一下 jvm 运行时数据区？

196.说一下堆栈的区别？

197.队列和栈是什么？有什么区别？

198.什么是双亲委派模型？

199.说一下类加载的执行过程？

200.怎么判断对象是否可以被回收？

201.java 中都有哪些引用类型？

202.说一下 jvm 有哪些垃圾回收算法？

203.说一下 jvm 有哪些垃圾回收器？

204.详细介绍一下 CMS 垃圾回收器？

205.新生代垃圾回收器和老生代垃圾回收器都有哪些？有什么区别？

206.简述分代垃圾回收器是怎么工作的？

207.说一下 jvm 调优的工具？

208.常用的 jvm 调优的参数都有哪些？