package taste.annotaions.try2use.ioccontainer;

import utils.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @Author: kkyeer
 * @Description: 最简单的IOC容器
 * @Date:Created in 22:55 2019/4/6
 * @Modified By:
 */
class IocContainer {
    private Map<Class<?>, Object> beanStorage = new HashMap<>(8);
    private Set<Class<?>> serviceStorage = new HashSet<>();
    /**
     * 构造方法，在这里处理类的初始化，仅处理最简单的情况,即Bean里只有
     */
    IocContainer(){
        List<Class<?>> allClasses = Reflections.getClassesInPackage(this.getClass().getPackage().getName());
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                handleConfig(clazz);
            }
            if (clazz.isAnnotationPresent(Service.class)) {
                handleService(clazz);
            }
        }
    }

    /**
     * 处理配置类，遍历所有的方法获取所有的bean，这里假设bean是单例的
     * @param clazz  传入的配置类
     */
    private void handleConfig(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Object instance = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        System.out.println("实例"+clazz.getCanonicalName()+"初始化失败");
                        e.printStackTrace();
                    }
                }
                handleBean(instance,method);
            }
        }
    }

    /**
     * 处理bean，只考虑最简单的情况
     * @param instance config实例
     * @param method 产生Bean的方法
     */
    private void handleBean(Object instance,Method method){
        Class<?> resultClass = method.getReturnType();
        try {
            Object result = method.invoke(instance);
            beanStorage.put(resultClass, result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Bean"+resultClass.getCanonicalName()+"创建失败");
            e.printStackTrace();
        }

    }

    /**
     * 处理Service的方法，实际上为了一定程度上避免循环引用，使用懒加载的方式
     *
     * @param clazz 带有Config注解的类
     */
    private void handleService(Class<?> clazz) {
        serviceStorage.add(clazz);
    }

    <T extends CommonService> T getService(Class<T> targetClass) throws Exception {
        if (!serviceStorage.contains(targetClass)) {
            throw new Exception("没有对应的定义");
        }else {
            T service = targetClass.newInstance();
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowire.class)) {
                    // 1.获取fieldName
                    String fieldName = field.getName();
                    Class<?> fieldType = field.getType();
                    if (!beanStorage.containsKey(fieldType)) {
                        throw new Exception("未定义类型为" + fieldType.getCanonicalName() + "的Bean，注入失败");
                    }
                    // 2.获取field的set方法
                    String setter = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = targetClass.getMethod(setter, fieldType);
                    // 3.初始化bean实例，调用set方法放入
                    method.invoke(service, beanStorage.get(fieldType));
                }
            }
            return (T) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class[]{CommonService.class},
                    (proxy, method, args) -> {
                        method.invoke(service, args);
                        return true;
                    }
            );
        }

    }


}
