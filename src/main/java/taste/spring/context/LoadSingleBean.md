# Spring加载Bean的过程

## 1. 测试代码

pom.xml中的依赖

```xml
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.1.8.RELEASE</version>
    </dependency>
```

文件application-context.xml位于src/main/resources目录下，bean定义如下：

```xml
	<bean id="myBean" class="com.kkyeer.taste.Person">
		<property name="age" value="12"/>
		<property name="name" value="Zhang San"/>
	</bean>
```

Person.java类包含age,name两个Field，入口类比较简单，先从application-context.xml文件中加载一个ApplicationContext，再通过getBean(Person.class)方法获取Bean，最后打印验证

```Java
public class TasteSpringBoot {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person.getName());
    }
}
```

## 2. new ApplicationContext("a-c.xml")的过程

### 2.1. ApplicationContext的UML

![UML](./ClassPathXmlApplicationContext.png)

### 2.2. 调用ClassPathXmlApplicationContext的构造方法

```Java
	public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[] {configLocation}, true, null);
	}
```

调用到下面的构造方法

```Java
	public ClassPathXmlApplicationContext(
			String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
			throws BeansException {

		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();
		}
	}
```

### 2.2.1. super(parent)

调用（最近的）某父类的参数为（ApplicationContext parent)的的构造方法，沿父类构造方法追溯，最终到下面的类构造方法

```Java
	public AbstractApplicationContext(@Nullable ApplicationContext parent) {
		this();
		setParent(parent);
	}
```

这里调用了this的无参构造方法，无参构造方法会依次调用整个继承链上所有父类的无参构造方法：

#### 2.2.1.1. AbstractApplicationContext父类到this的无参构造方法

##### 2.2.1.1.1. DefaultResourceLoader的无参构造方法

沿着类图向上追溯，向上追溯到DefaultResourceLoader的无参构造方法：

```Java
	public DefaultResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}
```

final field初始化

- ```private final Set<ProtocolResolver> protocolResolvers = new LinkedHashSet<>(4);```
- ```private final Map<Class<?>, Map<Resource, ?>> resourceCaches = new ConcurrentHashMap<>(4);```

非final field初始化

- ```private ClassLoader classLoader = = ClassUtils.getDefaultClassLoader();```

这里的getDefaultClassLoader方法用来获取ClassLoader，优先级：当前线程的ContextClassLoader>ClassUtils.class的ClassLoadeer>SystemClassLoader

classLoader初始化完成后，DefaultResourceLoader的空参构造方法执行完成

###### 2.2.1.1.2. AbstractApplicationContext的空参数构造方法

1. final field初始化

- ```public static final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";```
- ```public static final String LIFECYCLE_PROCESSOR_BEAN_NAME = "lifecycleProcessor";```
- ```public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";```
- ```private String id = ObjectUtils.identityToString(this);```
- ```private String displayName = ObjectUtils.identityToString(this);```
- ```private final AtomicBoolean active = new AtomicBoolean();```false
- ```private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();```**这是个关键列表**
- ```private final AtomicBoolean closed = new AtomicBoolean();```false
- ```private final Object startupShutdownMonitor = new Object();```**refresh和destroy方法的Monitor对象**
- ```private final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();```**监听器对象**
- ```protected final Log logger = LogFactory.getLog(getClass());```

**注意logger字段是protected的，子类可以访问**，这里调用org.apache.commons.logging.LogFactory的getLog方法，返回的也是同一个包下的Log对象实例，实际执行过程中通过顺序尝试加载log4j_spi\log4j_slf4j\slf4j_spi\slf4j的核心类来确认当前使用的Logger，并通过LogAdapter类中的各种适配器将实际类中的方法适配到common.logging包下的Log接口的方法

2. 手动初始化其他field

- 简化后：
```private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(this);```
过程：1. ```private PathMatcher pathMatcher = new AntPathMatcher();```2. 将PathMatchingResourcePatternResolver内部的resourceLoader变量初始化为正在初始化的ApplicationContext（ApplicationContext的类继承路径上有ResourceLoader）

以上全部执行完毕后，所有无参构造方法调用完毕，继续执行

###### 2.2.1.2. AbstractApplicationContext的setParent(parent)

```java
	@Override
	public void setParent(@Nullable ApplicationContext parent) {
		this.parent = parent;
		if (parent != null) {
			Environment parentEnvironment = parent.getEnvironment();
			if (parentEnvironment instanceof ConfigurableEnvironment) {
				getEnvironment().merge((ConfigurableEnvironment) parentEnvironment);
			}
		}
	}
```

对于此次给定的初始化参数来说，由于parent为null，因此此处无操作

#### 2.2.2. ClassPathXmlApplicationContext调用setConfigLocations(configLocations)

这里configLocations 是字符串数组: ["application-context.xml"]，这个方法的方法体为：

```java
	/**
	 * Set the config locations for this application context.
	 * <p>If not set, the implementation may use a default as appropriate.
	 */
	public void setConfigLocations(@Nullable String... locations) {
		if (locations != null) {
			Assert.noNullElements(locations, "Config locations must not be null");
			this.configLocations = new String[locations.length];
			for (int i = 0; i < locations.length; i++) {
				this.configLocations[i] = resolvePath(locations[i]).trim();
			}
		}
		else {
			this.configLocations = null;
		}
	}
```

校验输入后，遍历传入的configLocations数组，对每个元素分别调用resolvPath()方法并存入this.configLocations数组

##### 2.2.2.1 resolvePath方法

