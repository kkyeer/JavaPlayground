# SpringContext之xml配置(2) new XmlApplicationContext("a-c.xml")过程的refresh()方法

内容接续[第一节:configLocation参数处理](./XmlContext_1_process_config.md)，在处理完configLocation并执行完父类的构造方法后，正式开始扫描Xml并进行bean的构造与存储，即执行ClassPathXmlApplicationContext类的父类AbstractApplicationContext的refresh()方法，代码如下：

```java
	/**
	 * Load or refresh the persistent representation of the configuration,
	 * which might an XML file, properties file, or relational database schema.
	 * <p>As this is a startup method, it should destroy already created singletons
	 * if it fails, to avoid dangling resources. In other words, after invocation
	 * of that method, either all or no singletons at all should be instantiated.
	 * @throws BeansException if the bean factory could not be initialized
	 * @throws IllegalStateException if already initialized and multiple refresh
	 * attempts are not supported
	 */
	@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
```

## 1.1 准备环境：prepareRefresh()

prepareRefresh方法的执行过程如方法名所示，主要进行环境变量的操作、日志的打印以及某些listener的处理，主要过程如下：

1. 更改当前Context的状态，```startupDate=当前时间;closed=false;active=true;```
2. 打印debug记录
3. 初始化上下文中的propertySources占位，在这个类里是空方法体
4. 初始化earlyApplicationListeners和applicationListeners，当前用例里为空，注意存储使用的是LinkedHashSet，是有序set
5. 初始化earlyApplicationEvents为空的LinkedHashSet

## 1.2 初始化空beanFactory：ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory()

```java
	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		refreshBeanFactory();
		return getBeanFactory();
	}
```

方法中主要是调用了方法refreshBeanFactory()，然后返回getBeanFactory()的执行结果，这两个方法在AbstractApplicationContext中均为抽象方法，对于本例来说，实际由子类
AbstractRefreshableApplicationContext实现

### 1.2.1 refreshBeanFactory()

此方法的具体代码如下：

```java
	/**
	 * This implementation performs an actual refresh of this context's underlying
	 * bean factory, shutting down the previous bean factory (if any) and
	 * initializing a fresh bean factory for the next phase of the context's lifecycle.
	 */
	@Override
	protected final void refreshBeanFactory() throws BeansException {
		if (hasBeanFactory()) {
			destroyBeans();
			closeBeanFactory();
		}
		try {
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			beanFactory.setSerializationId(getId());
			customizeBeanFactory(beanFactory);
			loadBeanDefinitions(beanFactory);
			synchronized (this.beanFactoryMonitor) {
				this.beanFactory = beanFactory;
			}
		}
		catch (IOException ex) {
			throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
		}
	}
```

执行过程分析：

1. 销毁当前所有已经创建的bean，关闭beanFactory
2. 创建一个新的beanFactory并赋值新序列号
3. 自定义beanFactory
4. 加载bean定义
5. 赋值给当前ApplicationContext的beanFactory

主要关注点应该在第2、3、4步中：

#### 1.2.1.1 初始化BeanFactory:createBeanFactory()

```java
	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
	}
```

注意到初始化的BeanFactory类型为DefaultListableBeanFactory，且此处调用的是带参构造方法，参数类型为BeanFactory，值为getInternalParentBeanFactory()方法的返回值，实例初始化的过程可分为三步

// todo 放入DefaultListableBeanFactory的UML类图

1. DefaultListableBeanFactory的类加载、final变量加载
2. 获取getInternalParentBeanFactory()方法的返回值
3. 构造器调用

##### 1.2.1.1.1 DefaultListableBeanFactory的类加载、final变量加载

1. 静态代码块，测试classpath中是否有```javax.inject.Provider```类
2. Map from serialized id to factory instance.```private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories = new ConcurrentHashMap<>(8);```

##### 1.2.1.1.2 getInternalParentBeanFactory()

```java
	return (getParent() instanceof ConfigurableApplicationContext ?
				((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent());
```

如果this.parent变量指向的是ConfigurableApplicationContext，则返回this.parent.getBeanFactory()，否则返回this.parent，对于当前用例，this.parent == null，所以返回null

##### 1.2.1.1.3 DefaultListableBeanFactory构造器调用

1. 显示调用super的带参构造方法```public DefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory) {super(parentBeanFactory);}```
2. 父类AbstractAutowireCapableBeanFactory的带参构造方法

AbstractAutowireCapableBeanFactory类主要实现了bean的创建、初始化、参数注入、构造方法注入等功能

```java
	public AbstractAutowireCapableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
		this();
		setParentBeanFactory(parentBeanFactory);
	}
```

this()调用的无参构造方法:

```java
	public AbstractAutowireCapableBeanFactory() {
		super();
		ignoreDependencyInterface(BeanNameAware.class);
		ignoreDependencyInterface(BeanFactoryAware.class);
		ignoreDependencyInterface(BeanClassLoaderAware.class);
	}
```

super()调用的父类无参构造方法，沿继承链上溯到DefaultSingletonBeanRegistry类中，

###### 1.2.1.1.3.1 DefaultSingletonBeanRegistry类的初始化

此类中定义了存取SingletonBean的缓存和相关方法，其中主要属性列举如下：

1. 存储各种bean的缓存：

- Cache of singleton objects: bean name to bean instance. ```private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);```
- Cache of singleton factories: bean name to ObjectFactory.```private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);```
- Cache of early singleton objects: bean name to bean instance.```private final Set<String> registeredSingletons = new LinkedHashSet<>(256);```
- Disposable bean instances: bean name to disposable instance.```private final Map<String, Object> disposableBeans = new LinkedHashMap<>();```

注意，存储单例对象的缓存应该是主要缓存，且会被并发访问，因此使用ConcurrentHashMap保证线程安全的同时，初始容量设置为256来减少初期的Map扩容

2. 存储bean和bean关系的各种Map:

- Map between containing bean names: bean name to Set of bean names that the bean contains.```private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);```
- Map between dependent bean names: bean name to Set of dependent bean names.```private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);```
- Map between depending bean names: bean name to Set of bean names for the bean's dependencies.```private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);```

除了存储创建好的Bean的缓存外，还有一些属性，表征创建过程状态的如singletonsCurrentlyInCreation，inCreationCheckExclusions，singletonsCurrentlyInDestruction等。

DefaultSingletonBeanRegistry初始化完成后，沿继承链轮到DefaultListableBeanFactory的父类AbstractBeanFactory初始化：

###### 1.2.1.1.3.2 AbstractBeanFactory类

这个类中，除从父类继承来的SingletonBean相关属性方法外，还定义了很多处理类和更多的缓存变量，列举如下：

1. bean过程处理相关的类和flag

- ClassLoader：
- beanMetaData的flag：是否缓存metaData，默认true
- propertyEditorRegistrars：Set\<PropertyEditorRegistrar>类型
- ```Map<Class<?>, Class<? extends PropertyEditor>> customEditors```，自定义的PeropertyEditors
- ```List<StringValueResolver> embeddedValueResolvers```:String resolvers to apply e.g. to annotation attribute values.
- ```List<BeanPostProcessor> beanPostProcessors```

2. Bean缓存变量和过程变量

- ```Map<String, Scope> scopes```:Map from scope identifier String to corresponding Scope.
- ```Map<String, RootBeanDefinition> mergedBeanDefinitions```:Map from bean name to merged RootBeanDefinition.
- ```Set<String> alreadyCreated```:Names of beans that have already been created at least once.
- ```ThreadLocal<Object> prototypesCurrentlyInCreation```:Names of beans that are currently in creation.

AbstractBeanFactory实例初始化完成后，沿继承链回到AbstractAutowireCapableBeanFactory类的初始化

###### 1.2.1.1.3.3 AbstractAutowireCapableBeanFactory的变量初始化

父类全部初始化完成后，初始化本类的变量：

- ```private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();```
- ```private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();```
- ```private final Set<Class<?>> ignoredDependencyTypes = new HashSet<>();```
- ```private final Set<Class<?>> ignoredDependencyInterfaces```,注意，这个set在空构造器中被放入了BeanNameAware、BeanFactoryAware、BeanClassLoaderAware三个接口
- ```private final NamedThreadLocal<String> currentlyCreatedBean```:The name of the currently created bean, for implicit dependency registration on getBean etc invocations triggered from a user-specified Supplier callback.
- ```private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache```:Cache of unfinished FactoryBean instances: FactoryBean name to BeanWrapper.
- ```private final ConcurrentMap<Class<?>, Method[]> factoryMethodCandidateCache```:Cache of candidate factory methods per factory class.
- ```private final ConcurrentMap<Class<?>, PropertyDescriptor[]> filteredPropertyDescriptorsCache```:Cache of filtered PropertyDescriptors: bean Class to PropertyDescriptor array.

在初始化完成上述变量后将this.parentBeanFactory=parentBeanFactory也就是null，至此AbstractAutowireCapableBeanFactory实例初始化完成

##### 1.2.1.1.4 DefaultListableBeanFactory变量初始化

- ```private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();```
- ```private final Map<Class<?>, Object> resolvableDependencies```:依赖Class到实例
- ```private final Map<String, BeanDefinition> beanDefinitionMap```:bean名到bean定义
- ```private final Map<Class<?>, String[]> allBeanNamesByType```:依赖class到bean名的数组，不管bean是否单例
- ```private final Map<Class<?>, String[]> singletonBeanNamesByType```:依赖class到bean名的数组，bean是单例
- ```private volatile List<String> beanDefinitionNames```:按register顺序存储的beanDefinitionNames
- ```private volatile Set<String> manualSingletonNames```:按register顺序存储的手动注册的单例

上述属性初始化完成后，DefaultListableBeanFactory初始化完成，初始化完成的DefaultListableBeanFactory实例返回到refreshBeanFactory方法，返回的beanFactory被赋值序列号：类名@hashcode

#### 1.2.1.2 customizeBeanFactory(beanFactory)

根据当前上下文的变量更新beanFactory的allowBeanDefinitionOverriding和allowCircularReferences属性，当前用例中都是null，因此无操作

#### 1.2.1.3 loadBeanDefinitions(beanFactory)

