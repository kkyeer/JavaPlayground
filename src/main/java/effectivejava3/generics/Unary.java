package effectivejava3.generics;

import java.util.function.UnaryOperator;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:31 2018/10/18
 * @Modified By:
 */
public class Unary {
    private static  UnaryOperator<Object> IDENTIFY_FN = (t)->t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identifyFuntion(){
        return (UnaryOperator<T>)IDENTIFY_FN;
    }

    public static void main(String[] args) {
        String[] strings = {"asdf", "lkjl", "q9w"};
        UnaryOperator<String> unaryOperator = identifyFuntion();
        for (String string : strings) {
            System.out.println(unaryOperator.apply(string));
        }
    }
}
