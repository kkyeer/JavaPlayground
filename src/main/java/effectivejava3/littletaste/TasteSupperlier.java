package effectivejava3.littletaste;

import java.util.function.Supplier;

/**
 * @since 1.8+
 * @author kkyeer@gmail.com
 * @date 2018/10/3 11:20
 */
public class TasteSupperlier{
    public static void main(String[] args) {
        Supplier<Integer> sss = () -> "laksdjf".length();
        System.out.println(sss.get());
    }
}
