# SpringContext之xml配置(3) new XmlApplicationContext("a-c.xml")过程的refresh()方法:Resource对象中加载BeanDefinition

内容接续[第二节:规整configLocation成Resource数组](./XmlContext_2_refresh.md)，在初始化完BeanFactory和XmlBeanDefinitionReader对象后，XmlBeanDefinitionReader对象将每一个configLocation字符串规整为Resource数组完成后，开始遍历获取到的Resource数组，并在每个对象上调用的loadBeanDefinitions(Resource resource)方法来加载BeanDefinition到reader内部的BeanFactory中，代码如下：

```java
	public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
		return loadBeanDefinitions(new EncodedResource(resource));
	}
```

调用下面的方法

```java
	public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
		Assert.notNull(encodedResource, "EncodedResource must not be null");
		if (logger.isTraceEnabled()) {
			logger.trace("Loading XML bean definitions from " + encodedResource);
		}

		Set<EncodedResource> currentResources = this.resourcesCurrentlyBeingLoaded.get();
		if (currentResources == null) {
			currentResources = new HashSet<>(4);
			this.resourcesCurrentlyBeingLoaded.set(currentResources);
		}
		if (!currentResources.add(encodedResource)) {
			throw new BeanDefinitionStoreException(
					"Detected cyclic loading of " + encodedResource + " - check your import definitions!");
		}
		try {
			InputStream inputStream = encodedResource.getResource().getInputStream();
			try {
				InputSource inputSource = new InputSource(inputStream);
				if (encodedResource.getEncoding() != null) {
					inputSource.setEncoding(encodedResource.getEncoding());
				}
				return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
			}
			finally {
				inputStream.close();
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"IOException parsing XML document from " + encodedResource.getResource(), ex);
		}
		finally {
			currentResources.remove(encodedResource);
			if (currentResources.isEmpty()) {
				this.resourcesCurrentlyBeingLoaded.remove();
			}
		}
	}
```

代码逻辑如下：

1. 判断是当前reader中是否有相同的resource正在加载，如果有说明配置错误，报错
2. 获取resource的inputStream，并包裹成sax类型的InputSource
3. 调用doLoadBeanDefinitions(inputSource, encodedResource.getResource())方法来读取xml文件并加载BeanDefinition

其中第三步的doLoadBeanDefinitions(inputSource, encodedResource.getResource())方法中，先加载xml到Document对象实例中，再调用```public int registerBeanDefinitions(Document doc, Resource resource)```方法进行bean的注册，并处理过程中的异常与打印，后者是核心方法

## 1.1 ClassPathResource获取inputStream

首先，底层调用ClassLoader类中getResource(String name)方法获取文件对应的URL，其内部执行的顺序为：

1. 调用父ClassLoader的getResource(name)的方法，如果返回值不为空则返回
2. 在虚拟机的内置BootStrap ClassLoader里寻找name对应的url，有则返回结果
3. 调用本对象的findResource(name)方法（默认是返回null，可能被子类复写），无论是否返回调用结果

在本例中，实际上是在第三步中，调用当前的UrlClassLoader的方法获取到URL，获取到的URL为file协议，指向项目target/classes目录下的application-context.xml目录，形式上类似"file:/myproject/target/classes/application-context.xml"文件，获取到文件后，通过调用URL类的openConnection方法返回相关的文件流InputStream

## 1.2 XmlBeanDefinitionReader从xml流中加载Bean定义：doLoadBeanDefinitions方法


