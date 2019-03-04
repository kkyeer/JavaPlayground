package taste.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import share.HpPrinter;
import share.Person;
import share.Printer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:45 PM 9/18/18
 * @Modified By:
 */
public class TasteCglib {
    /**
     * 方法拦截
     */
     static void methodInterceptor(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HpPrinter.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println(methodProxy.getSignature());
            System.out.println(methodProxy.getSuperIndex());
            System.out.println(methodProxy.getSuperName());
            System.out.println("-----before invoke-----");
            return methodProxy.invokeSuper(o,objects);
        });
        Printer printer = (Printer) enhancer.create();
        printer.print("Test page");
    }

    /**
     * 固定返回
     */
    static void fixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HpPrinter.class);
        enhancer.setCallback((FixedValue)()-> "Fixed Value");
        Printer printer = (Printer) enhancer.create();
        printer.print("Test page");
        System.out.println(printer.getClass());
        System.out.println(printer.toString());
        System.out.println(printer.hashCode());
    }

    /**
     * InvocationHandler
     */
    static void invocationHandler() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Printer.class);
        enhancer.setCallback((InvocationHandler) (o, method, args) -> {
            System.out.println("->before invoke");
            System.out.println("Cglib dynamic printer");
            for (Object arg : args) {
                System.out.println(arg);
            }
            System.out.println("<-after invoke");
            return null;
        });
        Printer printer = (Printer) enhancer.create();
        printer.print("Test Test");
    }

    /**
     * 不可变bean
     */
    static void immutableBean() {
        Person xiaoming = new Person(20, "xiaoming");
        Person xiaomingbuzhangle = (Person) ImmutableBean.create(xiaoming);
        xiaoming.setAge(21);
        System.out.println(xiaoming);
        xiaomingbuzhangle.setAge(20);
    }

    static void beanGenerator() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value",String.class);
        Object myBean = beanGenerator.create();
        Method setter = myBean.getClass().getMethod("setValue",String.class);
        setter.invoke(myBean,"Hello cglib");

        Method getter = myBean.getClass().getMethod("getValue");
        System.out.println(getter.invoke(myBean));

    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//         methodInterceptor();
//        fixedValue();
//        invocationHandler();
//        immutableBean();
        beanGenerator();
    }

}
