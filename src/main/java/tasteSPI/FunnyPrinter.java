package tasteSPI;

/**
 * @author kkyeer@gmail.com
 * @date 2018/9/30 21:54
 */
public class FunnyPrinter implements Printer {
    @Override
    public void print(String inString) {
        System.out.println("---This is a funny printer----");
        System.out.println(inString);
    }
}
