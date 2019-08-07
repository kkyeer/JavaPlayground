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

## 1.1 解析Bean对应的BeanClass

BeanClass的确定按找下述顺序确定

1. bd里的beanClass是Class对象（刚读取xml配置后是String对象）时，则返回bd的beanClass属性
2. 根据当前是否配置SecurityManager，来进行授权调用或非授权调用，调用的方法为doResolveBeanClass方法

### 1.1.1 doResolveBeanClass

1. 获取当前BeanFactory的BeanClassLoader属性
2. 判断是否需要