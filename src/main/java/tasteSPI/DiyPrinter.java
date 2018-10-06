package tasteSPI;

import share.Printer;

/**
 * @author kkyeer@gmail.com
 * @date 2018/9/30 22:01
 */
public class DiyPrinter implements Printer {

    @Override
    public void print(String inString) {
        System.out.println("---This is a diy printer----");
        System.out.println(inString);
    }
}
