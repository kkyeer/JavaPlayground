package taste.annotations;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 20:57 2019/3/4
 * @Modified By:
 */
public class ActionListenerInstaller {
    public static void processAnnotations(Object object) {
        try {
            Class clazz = object.getClass();
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods) {
                ActionListenerFor actionListenerFor = method.getAnnotation(ActionListenerFor.class);
                if (actionListenerFor != null) {
                    String srcName = actionListenerFor.source();
                    Field srcField = clazz.getDeclaredField(srcName);
                    // 此处需要设置成可访问，不然会报错java.lang.IllegalAccessException: Class taste.annotations.ActionListenerInstaller can not access a member of class taste.annotations.ButtonFrame with modifiers "private"
                    srcField.setAccessible(true);
                    Object srcObj = srcField.get(object);
                    addListener(srcObj,method,object);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用代理的方式动态设置事件监听
     *
     * @param srcObj    需要增加事件监听的控件
     * @param action    实际需要执行的方法
     * @param container 方法对应的容器对象
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private static void addListener(Object srcObj, Method action, Object container) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 创建一个代理，当此实例的对应方法被触发时，实际上调用的是第三个参数定义的对象内部的invoke接口
        ActionListener actionListener = (ActionListener) Proxy.newProxyInstance(container.getClass().getClassLoader(), new Class[]{ActionListener.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 被触发时，实际上调用的是方法容器对应的方法,即实际上执行 container.action，写成反射就是action.invoke(container)
                return action.invoke(container);
            }
        });
        // 下面这两句是增加监听器方法，实质上等同于srcObj.addActionListener(actionListener)
        Method adder = srcObj.getClass().getMethod("addActionListener",ActionListener.class);
        adder.invoke(srcObj, actionListener);
    }
}


