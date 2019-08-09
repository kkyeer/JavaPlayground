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
4. 初始化之前的钩子函数：调用可能的InstantiationAwareBeanPostProcessor，这些BeanPostProcessor可能用来生成代理类，如果这些BeanPostProcessor中生成了bean，则返回生成的bean不进行后续操作
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

1. 初始化BeanWrapper为null
2. 从factoryBeanInstanceCache中获取已经缓存的未完全完成的FactoryBean对应的BeanWrapper
3. 如果上一步未获取到非空的BeanWrapper，则调用BeanFactory的createBeanInstance方法，选取合适的构造策略来构建BeanWrapper
4. 调用可能的MergedBeanDefinitionPostProcessor修改bd，修改完成后mbd.postProcessed = true
5. 如果允许循环引用，则提前缓存创建中的单例Bean
6. 解析BeanWrapper中的PropertyValue并set到Bean：```populateBean(beanName, mbd, instanceWrapper);```
7. 

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
3. initBeanWrapper：初始化BeanWrapper内部的editor和conversionService
4. 返回创建好的BeanWrapper

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

##### 1.4.1.2.2 获取的实例包裹到BeanWrapperImpl对象中，并initBeanWrapper

BeanWrapperImpl的类关系图如下：

![BeanWrapperImpl的类关系图](./BeanWrapperImpl.png)

##### 1.4.1.2.3 BeanFactory的initBeanWrapper方法

```java
    protected void initBeanWrapper(BeanWrapper bw) {
        bw.setConversionService(getConversionService());
        registerCustomEditors(bw);
    }
```

第一步是将BeanFactory的ConvertionService传入BeanWrapper，第二步是初始化BeanWrapper的自定义Editor:

```java
    protected void registerCustomEditors(PropertyEditorRegistry registry) {
        PropertyEditorRegistrySupport registrySupport =
                (registry instanceof PropertyEditorRegistrySupport ? (PropertyEditorRegistrySupport) registry : null);
        if (registrySupport != null) {
            registrySupport.useConfigValueEditors();
        }
        if (!this.propertyEditorRegistrars.isEmpty()) {
            for (PropertyEditorRegistrar registrar : this.propertyEditorRegistrars) {
                try {
                    registrar.registerCustomEditors(registry);
                }
                catch (BeanCreationException ex) {
                    Throwable rootCause = ex.getMostSpecificCause();
                    if (rootCause instanceof BeanCurrentlyInCreationException) {
                        BeanCreationException bce = (BeanCreationException) rootCause;
                        String bceBeanName = bce.getBeanName();
                        if (bceBeanName != null && isCurrentlyInCreation(bceBeanName)) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("PropertyEditorRegistrar [" + registrar.getClass().getName() +
                                        "] failed because it tried to obtain currently created bean '" +
                                        ex.getBeanName() + "': " + ex.getMessage());
                            }
                            onSuppressedException(ex);
                            continue;
                        }
                    }
                    throw ex;
                }
            }
        }
        if (!this.customEditors.isEmpty()) {
            this.customEditors.forEach((requiredType, editorClass) ->
                    registry.registerCustomEditor(requiredType, BeanUtils.instantiateClass(editorClass)));
        }
    }
```

1. 将BeanWrapper的configValueEditorsActive设为true
2. 遍历BeanFactory内部的PropertyEditorRegistrars，对每个registar调用```registrar.registerCustomEditors(registry)```方法，默认的BeanFactory会有一个ResourceEditorRegistrar
3. 遍历BeanFactory内部的CustomPropertyEditors，对每个Editor调用BeanWrapper的```registerCustomEditor(requiredType, BeanUtils.instantiateClass(editorClass)))```方法

###### 1.4.1.2.3.1 ResourceEditorRegistrar的```registrar.registerCustomEditors(registry)```方法

```java
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        ResourceEditor baseEditor = new ResourceEditor(this.resourceLoader, this.propertyResolver);
        doRegisterEditor(registry, Resource.class, baseEditor);
        doRegisterEditor(registry, ContextResource.class, baseEditor);
        doRegisterEditor(registry, InputStream.class, new InputStreamEditor(baseEditor));
        doRegisterEditor(registry, InputSource.class, new InputSourceEditor(baseEditor));
        doRegisterEditor(registry, File.class, new FileEditor(baseEditor));
        doRegisterEditor(registry, Path.class, new PathEditor(baseEditor));
        doRegisterEditor(registry, Reader.class, new ReaderEditor(baseEditor));
        doRegisterEditor(registry, URL.class, new URLEditor(baseEditor));

        ClassLoader classLoader = this.resourceLoader.getClassLoader();
        doRegisterEditor(registry, URI.class, new URIEditor(classLoader));
        doRegisterEditor(registry, Class.class, new ClassEditor(classLoader));
        doRegisterEditor(registry, Class[].class, new ClassArrayEditor(classLoader));

        if (this.resourceLoader instanceof ResourcePatternResolver) {
            doRegisterEditor(registry, Resource[].class,
                    new ResourceArrayPropertyEditor((ResourcePatternResolver) this.resourceLoader, this.propertyResolver));
        }
    }

    /**
     * Override default editor, if possible (since that's what we really mean to do here);
     * otherwise register as a custom editor.
     */
    private void doRegisterEditor(PropertyEditorRegistry registry, Class<?> requiredType, PropertyEditor editor) {
        if (registry instanceof PropertyEditorRegistrySupport) {
            ((PropertyEditorRegistrySupport) registry).overrideDefaultEditor(requiredType, editor);
        }
        else {
            registry.registerCustomEditor(requiredType, editor);
        }
    }
```

代码简洁明了：

1. 根据当前BeanWrapper的resourceLoader和propertyResolver初始化一个ResourceEditor
2. 将上一步初始化的ResourceEditor放入BeanWrapper的overriddenDefaultEditors并管理Resource、ContextResource类型，**值得注意的是，在实际根据类型获取PropertyEditor时，overriddenDefaultEditors优先级高于defaultEditors，即前者覆盖后者**
3. 类似的将ResourceEditor装饰（装饰者模式）成InputStreamEditor并关联InputSteam类，等等
4. 根据当前的resourceLoader的ClassLoader，初始化URIEditor、ClassEditor、ClassArrayEditor并关联

###### 1.4.1.2.3.2 BeanWrapper的```registerCustomEditor(requiredType, BeanUtils.instantiateClass(editorClass)))```方法

### 1.4.2 MergedBeanDefinitionPostProcessor修改bd

在AbstractApplicationContext的refresh方法中，获取完新BeanFactory后```prepareBeanFactory(beanFactory)```的过程中，初始化了一个ApplicationListenerDetector类型的BeanPostProcessor，并在后续的```registerBeanPostProcessors(beanFactory);```过程中刷新了它，这个类实现了MergedBeanDefinitionPostProcessor接口，因此在会被调用到BeanPostProcessor的```postProcessMergedBeanDefinition(mbd, beanType, beanName)```方法:

```java
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        this.singletonNames.put(beanName, beanDefinition.isSingleton());
    }
```

这里内部维护的singletonNames存储了每个bean是否可以被注册成ApplicationListener的flag，初始化bean开始前，值为beanDefinition.isSingleton()，即认为所有的单例类都可注册为ApplicationListener

### 1.4.3 提前缓存创建中的单例Bean

1. 判断依据：```mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName)```
    1. bd是单例Bean
    2. BeanFactory允许循环引用(默认True)
    3. 单例正在创建中（所以才需要提前缓存）
2. 存入内部缓存并更新相关属性：```addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));```

#### 1.4.3.1 将对应的SingletonFactory存入内部缓存并更新相关属性

调用到DefaultSingletonBeanRegistry类的方法：

```
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
                this.registeredSingletons.add(beanName);
            }
        }
    }
```

各个缓存作用为：

- singletonObjects：存储单例Bean，bean name to bean instance
- singletonFactories：存储生成单例Bean的ObjectFactory，bean name to ObjectFactory
- earlySingletonObjects：存储early阶段的Bean，bean name to bean instance
- registeredSingletons：LinkedHashSet类型，**按顺序**存储注册的单例Bean的名称

### 1.4.4 解析BeanWrapper中的PropertyValue并set到Bean：```populateBean(beanName, mbd, instanceWrapper);```

1. 校验bd
2. 如果!mbd.isSynthetic()且BeanFactory内部有InstantiationAwareBeanPostProcessor实例，则调用postProcessAfterInstantiation方法处理BeanWrapper内部的bean实例，如果此Processor要求执行完毕后返回，则直接返回，不进行下一步操作
3. 如果autowireMode不是AUTO，则根据bd内部的autowireMode调用响应的方法进行自动注入：
    1. AUTOWIRE_BY_NAME：```autowireByName(beanName, mbd, bw, newPvs)```
    2. AUTOWIRE_BY_TYPE：```autowireByType(beanName, mbd, bw, newPvs)```
4. 如果BeanFactory内部有InstantiationAwareBeanPostProcessor实例，则
    1. 调用BeanFactory的filterPropertyDescriptorsForDependencyCheck方法过滤PropertyValue，过滤掉ignoredDependencyTypes和ignoredDependencyInterfaces中定义的类型
    2. 调用InstantiationAwareBeanPostProcessor的postProcessProperties处理PropertyValue
5. 检查依赖的PropertyValue是否已经满足
6. 调用applyPropertyValues方法来解析并set到Bean

#### 1.4.4.1 非自动注入的PropertyValue处理：applyPropertyValues方法

```java
    protected void applyPropertyValues(String beanName, BeanDefinition mbd, BeanWrapper bw, PropertyValues pvs) {
        if (pvs.isEmpty()) {
            return;
        }

        if (System.getSecurityManager() != null && bw instanceof BeanWrapperImpl) {
            ((BeanWrapperImpl) bw).setSecurityContext(getAccessControlContext());
        }

        MutablePropertyValues mpvs = null;
        List<PropertyValue> original;

        if (pvs instanceof MutablePropertyValues) {
            mpvs = (MutablePropertyValues) pvs;
            if (mpvs.isConverted()) {
                // Shortcut: use the pre-converted values as-is.
                try {
                    bw.setPropertyValues(mpvs);
                    return;
                }
                catch (BeansException ex) {
                    throw new BeanCreationException(
                            mbd.getResourceDescription(), beanName, "Error setting property values", ex);
                }
            }
            original = mpvs.getPropertyValueList();
        }
        else {
            original = Arrays.asList(pvs.getPropertyValues());
        }

        TypeConverter converter = getCustomTypeConverter();
        if (converter == null) {
            converter = bw;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this, beanName, mbd, converter);

        // Create a deep copy, resolving any references for values.
        List<PropertyValue> deepCopy = new ArrayList<>(original.size());
        boolean resolveNecessary = false;
        for (PropertyValue pv : original) {
            if (pv.isConverted()) {
                deepCopy.add(pv);
            }
            else {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);
                Object convertedValue = resolvedValue;
                boolean convertible = bw.isWritableProperty(propertyName) &&
                        !PropertyAccessorUtils.isNestedOrIndexedProperty(propertyName);
                if (convertible) {
                    convertedValue = convertForProperty(resolvedValue, propertyName, bw, converter);
                }
                // Possibly store converted value in merged bean definition,
                // in order to avoid re-conversion for every created bean instance.
                if (resolvedValue == originalValue) {
                    if (convertible) {
                        pv.setConvertedValue(convertedValue);
                    }
                    deepCopy.add(pv);
                }
                else if (convertible && originalValue instanceof TypedStringValue &&
                        !((TypedStringValue) originalValue).isDynamic() &&
                        !(convertedValue instanceof Collection || ObjectUtils.isArray(convertedValue))) {
                    pv.setConvertedValue(convertedValue);
                    deepCopy.add(pv);
                }
                else {
                    resolveNecessary = true;
                    deepCopy.add(new PropertyValue(pv, convertedValue));
                }
            }
        }
        if (mpvs != null && !resolveNecessary) {
            mpvs.setConverted();
        }

        // Set our (possibly massaged) deep copy.
        try {
            bw.setPropertyValues(new MutablePropertyValues(deepCopy));
        }
        catch (BeansException ex) {
            throw new BeanCreationException(
                    mbd.getResourceDescription(), beanName, "Error setting property values", ex);
        }
    }
```

1. 