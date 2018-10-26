package effectivejava3.lambdas;

import java.util.function.DoubleBinaryOperator;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 22:09 2018/10/26
 * @Modified By:
 */
public enum Operations {
    //    加法
    PLUS("+", (x, y) -> (x + y)),
    //    减法
    MINUS("-", (x, y) -> (x - y));

    private String symbol;
    private DoubleBinaryOperator op;

//    @FunctionalInterface
//    private interface MyOperatorInterface{
//        double apply(double a, double b);
//    }


    Operations(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }

    public static void main(String[] args) {
        System.out.println(Operations.PLUS.apply(1,2));
    }
}
