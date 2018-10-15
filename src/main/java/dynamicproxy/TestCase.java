package dynamicproxy;

import share.Printer;

import java.lang.reflect.Proxy;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 18:50
 */
public class TestCase {
    public static void main(String[] args) {
        PrinterProxy printerProxy = new PrinterProxy();
        Printer proxyPrinter = (Printer) Proxy.newProxyInstance(
                printerProxy.getClass().getClassLoader(),
                new Class[]{Printer.class},
                printerProxy
        );
        System.out.println("Class:"+proxyPrinter.getClass().getName());
        proxyPrinter.print("Test page ");
    }
}
