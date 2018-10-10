package effectivejava3.dynamicproxy;

import share.Printer;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 18:43
 */
public class HpPrinter implements Printer {
    @Override
    public void print(String inString) {
        System.out.println("-----This is Hp Printer-----");
        System.out.println(inString);
        System.out.println("-----Hp printer end    -----");
    }
}
