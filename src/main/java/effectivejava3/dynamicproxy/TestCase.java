package effectivejava3.dynamicproxy;

import share.Printer;

import java.lang.reflect.Proxy;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 18:50
 */
public class TestCase {
    public static void main(String[] args) throws NoSuchMethodException {
        Printer hpPrinter = new HpPrinter();
        PrinterProxy printerProxy = new PrinterProxy(hpPrinter);
        Printer proxyPrinter = (Printer) Proxy.newProxyInstance(
                printerProxy.getClass().getClassLoader(),
                new Class[]{Printer.class},
                printerProxy
        );
        System.out.println("Class:"+proxyPrinter.getClass().getName());
        proxyPrinter.print("Test page ");
    }
}
