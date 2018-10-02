package tasteSPI;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author kkyeer@gmail.com
 * @date 2018/9/30 21:58
 */
public class TestCase {
    public static void main(String[] args) {
        ServiceLoader<Printer> printers = ServiceLoader.load(Printer.class);
        Iterator<Printer> printerIterator = printers.iterator();
        Printer printer;
        while (printerIterator.hasNext()) {
            printer = printerIterator.next();
            printer.print("test  page");
        }

    }
}
