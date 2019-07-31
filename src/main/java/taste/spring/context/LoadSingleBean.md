# Spring加载Bean的过程

## 测试代码

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

## 加载过程

### 1. 初始化ApplicationContext

#### 1.0 类图

![UML](./ClassPathXmlApplicationContext.png)

#### 1.1 调用ClassPathXmlApplicationContext的构造方法

```Java
public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) throws BeansException;
```

沿着类图向上追溯，向上追溯到DefaultResourceLoader的空参数构造方法：

```Java
	public DefaultResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}
```
