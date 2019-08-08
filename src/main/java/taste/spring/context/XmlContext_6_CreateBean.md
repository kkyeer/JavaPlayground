# SpringContext之xml配置(6) CreateBean

实际执行具体的bean创建的，是refresh过程新建的DefaultListableBeanFactory的父类AbstractAutowireCapableBeanFactory中的createBean方法：

```java
    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        if (logger.isTraceEnabled()) {
            logger.trace("Creating instance of bean '" + beanName + "'");
        }
        RootBeanDefinition mbdToUse = mbd;

        // Make sure bean class is actually resolved at this point, and
        // clone the bean definition in case of a dynamically resolved Class
        // which cannot be stored in the shared merged bean definition.
        Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
        if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
            mbdToUse = new RootBeanDefinition(mbd);
            mbdToUse.setBeanClass(resolvedClass);
        }

        // Prepare method overrides.
        try {
            mbdToUse.prepareMethodOverrides();
        }
        catch (BeanDefinitionValidationException ex) {
            throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(),
                    beanName, "Validation of method overrides failed", ex);
        }

        try {
            // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
            Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
            if (bean != null) {
                return bean;
            }
        }
        catch (Throwable ex) {
            throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName,
                    "BeanPostProcessor before instantiation of bean failed", ex);
        }

        try {
            Object beanInstance = doCreateBean(beanName, mbdToUse, args);
            if (logger.isTraceEnabled()) {
                logger.trace("Finished creating instance of bean '" + beanName + "'");
            }
            return beanInstance;
        }
        catch (BeanCreationException | ImplicitlyAppearedSingletonException ex) {
            // A previously detected exception with proper bean creation context already,
            // or illegal singleton state to be communicated up to DefaultSingletonBeanRegistry.
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanCreationException(
                    mbdToUse.getResourceDescription(), beanName, "Unexpected exception during bean creation", ex);
        }
    }
```

1. 解析Bean对应的BeanClass
2. 如果bean内部还没有BeanClass属性，则拷贝一份bd(beanDefinition的简称，下同)，并传入上一步解析的BeanClass
3. 校验并准备所有的MethodOverride
4. 初始化之前的钩子函数：调用可能的BeanPostProcessor，这些BeanPostProcessor可能用来生成代理类，如果这些BeanPostProcessor中生成了bean，则返回生成的bean不进行后续操作
5. 调用doCreateBean方法来获取Object
6. 

## 1.1 解析Bean对应的BeanClass

BeanClass的确定按找下述顺序确定

1. bd里的beanClass是Class对象（刚读取xml配置后是String对象）时，则返回bd的beanClass属性
2. 根据当前是否配置SecurityManager，来进行授权调用或非授权调用，调用的方法为doResolveBeanClass方法

### 1.1.1 doResolveBeanClass

1. 获取当前BeanFactory的BeanClassLoader属性
2. 判断是否需要调用动态类加载器，如果是则调用DecoratingClassLoaderexcludeClass(typeToMatch.getName())的方法
3. 如果不需要动态类加载器，则调用evaluateBeanDefinitionString(className,bd)方法来获取类名最终的字符串，注意从版本4.2开始，类名定义中允许添加#{}占位符定义，此步骤主要就是进行#{}占位符的解析
    1. 调用BeanFactory的内部维护的scope表，在表中获取bd相关的scope对应的Scope对象，
    2. 调用```this.beanExpressionResolver.evaluate(value, new BeanExpressionContext(this, scope))```来获取最终的class字符串
4. 判断上一步解析出的class字符串是否有变化，如果有变化，说明定义中有动态解析的部分，再判断是否已经解析出Class对象，解析出的话返回，
5. 如果没有解析出，则尝试用BeanFactory的TempClassLoader来加载Class对象，加载成功则返回，否则继续尝试
6. 如果上面全部未解析出，则调用BeanDefinition的```resolveBeanClass(Classloader classloader)```方法，此方法内部调用org.springframework.util包下的ClassUtils的forName(String,Classloader)方法，步骤如下：
    1. 比对工具类维护的primitiveTypeNameMap中存储的primitiveClass的Map，判断是否primitive类型，是则返回对应Class对象
    2. 比对工具内维护的commonClassCache缓存中存储的常用类，有则返回对应Class对象
    3. "java.lang.String[\]"类型的数组，则去掉后面的"[]"后缀后，递归调用forName方法获取Class，再根据Class类型返回对应的数组实例
    4. "[Ljava.lang.String;"类型定义的数组，则去掉"[L"前缀，递归调用forName方法获取Class，再根据Class类型返回对应的数组实例
    5. "[[I" or "[[Ljava.lang.String;"类型定义的数组，去掉"["前缀后，递归调用forName方法获取Class，再根据Class类型返回对应的数组实例
    6. 如果上面都不符合，则调用传入的ClassLoader(为空则获取defaultClassLoader)来加载类```Class.forName(name, false, clToUse)```并返回Class，如果还是没有加载，则尝试把最后一个"."替换为"$"继续调用Class.forName方法来加载内部类，如果还是不行，则抛异常

#### 1.1.1.1 beanExpressionResolver的evaluate方法

```java
    @Override
    @Nullable
    public Object evaluate(@Nullable String value, BeanExpressionContext evalContext) throws BeansException {
        if (!StringUtils.hasLength(value)) {
            return value;
        }
        try {
            Expression expr = this.expressionCache.get(value);
            if (expr == null) {
                expr = this.expressionParser.parseExpression(value, this.beanExpressionParserContext);
                this.expressionCache.put(value, expr);
            }
            StandardEvaluationContext sec = this.evaluationCache.get(evalContext);
            if (sec == null) {
                sec = new StandardEvaluationContext(evalContext);
                sec.addPropertyAccessor(new BeanExpressionContextAccessor());
                sec.addPropertyAccessor(new BeanFactoryAccessor());
                sec.addPropertyAccessor(new MapAccessor());
                sec.addPropertyAccessor(new EnvironmentAccessor());
                sec.setBeanResolver(new BeanFactoryResolver(evalContext.getBeanFactory()));
                sec.setTypeLocator(new StandardTypeLocator(evalContext.getBeanFactory().getBeanClassLoader()));
                ConversionService conversionService = evalContext.getBeanFactory().getConversionService();
                if (conversionService != null) {
                    sec.setTypeConverter(new StandardTypeConverter(conversionService));
                }
                customizeEvaluationContext(sec);
                this.evaluationCache.put(evalContext, sec);
            }
            return expr.getValue(sec);
        }
        catch (Throwable ex) {
            throw new BeanExpressionException("Expression parsing failed", ex);
        }
    }
```

1. 校验class名字符串，为空返回
2. 获取表达式对象：从内部维护的expressionCache中查找，如果没有，则调用内部的expressionParser来parse，将结果存入expressionCache中
3. 获取StandardEvaluationContext对象：从内部维护的evaluationCache中查找，如果没有则初始化一个：
    1. 新建StandardEvaluationContext对象实例
    2. 按顺序增加4个PropertyAccessor实[BeanExpressionContextAccessor\BeanFactoryAccessor\MapAccessor\EnvironmentAccessor],PropertyAccessor决定了如何从目标对象中读取或者写入Property，内部的getSpecificTargetClasses()、canRead()和canWrite()方法决定了适用的类和读写控制
    3. 增加一个BeanResolver:new BeanFactoryResolver(evalContext.getBeanFactory())
    4. 增加TypeLocator:new StandardTypeLocator(evalContext.getBeanFactory().getBeanClassLoader()),TypeLocator接口负责根据类型名（可能是全限定名也可能是String这种名称）获取Class对象， StandardTypeLocator会尝试在传入的ClassLoader中试图加载Class没有的话，就在"java.lang"下查找Class对象
    5. TypeConverter设置为new StandardTypeConverter(beanFactory.conversionService)

##### 1.1.1.1.1 获取表达式对象：expressionParser的parse方法

```java
    @Override
    public Expression parseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
        if (context != null && context.isTemplate()) {
            return parseTemplate(expressionString, context);
        }
        else {
            return doParseExpression(expressionString, context);
        }
    }
```

如果是模板表达式则调用parseTemplate方法，否则调用doParseExpression对象，后者返回SpelExpression对象，前者调用parseExpression方法，并根据返回的数组数量决定是直接返回数组的唯一元素或者将Expression数组包裹到CompositeStringExpression对象返回

```java
private Expression[] parseExpressions(String expressionString, ParserContext context)
```

将字符串转化为Expression数组，方法是如果字符串中有"#{expr}"格式的子串，则将之前面放入一个LiteralExpression对象，将字串解析后包裹在SpelExpression对象中，后面继续解析，最后将字符串分成多个Express对象，放到数组中返回。如果把LiteralExpression简写为LE,SpelExpression简写为SE，则"a#{b}c#{d}"会被parse为{LE("a"),SE{"b"},LE("c"),SE("d")}

## 1.2 校验并准备所有的MethodOverride

## 1.3 调用可能的InstantiationAwareBeanPostProcessor

## 1.4 doCreateBean方法

1. 初始化BeanWrapper
2. 从factoryBeanInstanceCache中获取已经缓存的未完全完成的FactoryBean
3. 调用createBeanInstance方法，选取合适的构造策略来构建实例
4. 

### 1.4.1 createBeanInstance方法

根据bd，按下列顺序尝试获取实例

1. InstanceSupplier：bd内部的Supplier
2. FactoryMethod：调用FactoryBean的对应Method
3. 寻找使用的Constructor和autowireMode：
    1. 缓存中查找：resolvedConstructorOrFactoryMethod缓存了可能的解析完成的Constructor和Factory Method，constructorArgumentsResolved标识了构造器参数列表是否解析完成
    2. 如果上述缓存没有发现，则调用determineConstructorsFromBeanPostProcessors方法来获取BeanProcessor相关的Constructor数组
    3. 如果上面返回的构造器数组非空（表明有BeanPostProcessor定义），或者bd里的autowireMode为AUTOWIRE_CONSTRUCTOR，或者调用初始化bean的args列表非空，或者bd的constructor-args配置非空，均表明可能需要自动注入，则调用```autowireConstructor(beanName, mbd, ctors, args)```方法来自动注入并初始化
    4. 如果class只有一个空构造器，且配置里也没有方法constructor-args参数列表，则调用空构造器来初始化实例```instantiateBean(beanName, mbd)```

#### 1.4.1.1 自动注入构造器的初始化Bean:autowireConstructor

#### 1.4.1.2 空参数构造器初始化Bean:instantiateBean

```java
    protected BeanWrapper instantiateBean(final String beanName, final RootBeanDefinition mbd) {
        try {
            Object beanInstance;
            final BeanFactory parent = this;
            if (System.getSecurityManager() != null) {
                beanInstance = AccessController.doPrivileged((PrivilegedAction<Object>) () ->
                        getInstantiationStrategy().instantiate(mbd, beanName, parent),
                        getAccessControlContext());
            }
            else {
                beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, parent);
            }
            BeanWrapper bw = new BeanWrapperImpl(beanInstance);
            initBeanWrapper(bw);
            return bw;
        }
        catch (Throwable ex) {
            throw new BeanCreationException(
                    mbd.getResourceDescription(), beanName, "Instantiation of bean failed", ex);
        }
    }
```

1. 调用InstantiationStrategy(对于AbstractAutoWireCapableBeanFactory是CglibSubclassingInstantiationStrategy)的instantiate(mbd, beanName, parent)方法来获取bean实例
2. 获取的实例包裹到BeanWrapperImpl对象中
3. initBeanWrapper

##### 1.4.1.2.1 CglibSubclassingInstantiationStrategy的instantiate方法

```java
    @Override
    public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
        // Don't override the class with CGLIB if no overrides.
        if (!bd.hasMethodOverrides()) {
            Constructor<?> constructorToUse;
            synchronized (bd.constructorArgumentLock) {
                constructorToUse = (Constructor<?>) bd.resolvedConstructorOrFactoryMethod;
                if (constructorToUse == null) {
                    final Class<?> clazz = bd.getBeanClass();
                    if (clazz.isInterface()) {
                        throw new BeanInstantiationException(clazz, "Specified class is an interface");
                    }
                    try {
                        if (System.getSecurityManager() != null) {
                            constructorToUse = AccessController.doPrivileged(
                                    (PrivilegedExceptionAction<Constructor<?>>) clazz::getDeclaredConstructor);
                        }
                        else {
                            constructorToUse = clazz.getDeclaredConstructor();
                        }
                        bd.resolvedConstructorOrFactoryMethod = constructorToUse;
                    }
                    catch (Throwable ex) {
                        throw new BeanInstantiationException(clazz, "No default constructor found", ex);
                    }
                }
            }
            return BeanUtils.instantiateClass(constructorToUse);
        }
        else {
            // Must generate CGLIB subclass.
            return instantiateWithMethodInjection(bd, beanName, owner);
        }
    }
```

如果没有MethodOverride的情况，则调用clazz.getDeclaredConstructor()方法获取空参数构造器，存入bd的resolvedConstructorOrFactoryMethod缓存后，调用BeanUtils.instantiateClass(constructorToUse)方法来获取实例，在这个方法中，会尝试将非public的Constructor更改为public，然后调用constructor.newInstance()方法来获取对象并返回，当然，如果是Kotlin，则调用Kotlin相关的代理方法来构造

如果有MethodOverride的情况，则调用cglib框架的Enhancer相关方法，创建bd里beanClass的子类，使用反射来调用子类的构造器并返回构造的bean实例


