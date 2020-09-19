package lab.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 18:38
 */
public class PrinterProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("->before invoke");
        System.out.println("This is dynamic printer");
        for (Object arg : args) {
            System.out.println(arg);
        }
        System.out.println("<-after invoke");
        return null;
    }
}
