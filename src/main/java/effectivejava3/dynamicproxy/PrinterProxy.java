package effectivejava3.dynamicproxy;

import share.Printer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 18:38
 */
public class PrinterProxy implements InvocationHandler {
    private Printer printer;

    public PrinterProxy(Printer printer) {
        this.printer = printer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("->before invoke");
        Object result = method.invoke(this.printer, args);
        System.out.println("<-after invoke");
        return result;
    }
}
