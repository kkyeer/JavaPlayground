package algorithm.exercise.chapter1.section2;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @Author: kkyeer
 * @Description: 表达式计算器，不考虑优先级，要求显式使用括号标识优先级
 * @Date:Created in 5:15 PM 2022/1/31
 * @Modified By:
 */
public class Evaluator {
    private Deque<OperatorEnum> operatorStack = new ArrayDeque<>();
    private Deque<Integer> operandStack = new ArrayDeque<>();
    private Deque<Character> tempStack = new ArrayDeque<>();

    public Evaluator(String input) {
        char[] chars = input.toCharArray();
        Set<Character> supportedOperator = Arrays.stream(OperatorEnum.values()).map(OperatorEnum::getOperatorChar).collect(Collectors.toSet());
        for (int i = 0; i < chars.length; i++) {
            if (chars[i]>='0' && chars[i]<='9'){
                tempStack.push(chars[i]);
            } else if (supportedOperator.contains(chars[i])) {
                int last =  getNumFromCharStack(tempStack);
                if (last >= 0) {
                    operandStack.push(last);
                }
                operatorStack.push(OperatorEnum.getFromOperatorChar(chars[i]));
            } else if (')' == chars[i]) {
                int num1;
                if (!tempStack.isEmpty()) {
                    num1 = getNumFromCharStack(tempStack);
                }else {
                    num1 = operandStack.pop();
                }
                int num2 = operandStack.pop();
                int result = operatorStack.pop().getFunction().apply(num2, num1);
                operandStack.push(result);
            }
        }
        int last = getNumFromCharStack(tempStack);
        if (last >= 0) {
            operandStack.push(last);
        }
        // 最后只剩同一优先级的，应该是左结合，从左往右
        while (operandStack.size() > 1) {
            int num1 = operandStack.pollLast();
            int num2 = operandStack.pollLast();
            int result = operatorStack.pollLast().getFunction().apply(num1, num2);
            operandStack.addLast(result);
        }
    }

    private int getNumFromCharStack(Deque<Character> tempStack) {
        if (tempStack.isEmpty()) {
            return -1;
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            while (!tempStack.isEmpty()) {
                stringBuilder.append(tempStack.pollLast());
            }
            return Integer.parseInt(stringBuilder.toString());
        }
    }

    public int result(){
        return operandStack.pop();
    }

    public static void main(String[] args) {
        String expression = " 1 + 11-(12*3)-12+3";
        System.out.println(new Evaluator(expression).result());
    }



    private enum OperatorEnum {
        ADD('+', Integer::sum),
        MINUS('-', (a, b) -> (a - b)),
        MULTIPLY('*', (a, b) -> a * b),
        DIVIDE('/', (a, b) -> a / b);
        private char operatorChar;
        private BiFunction<Integer, Integer, Integer> function;
        private static Map<Character, OperatorEnum> map = null;

        OperatorEnum(char aChar, BiFunction<Integer, Integer, Integer> function) {
            this.operatorChar = aChar;
            this.function = function;
        }

        public char getOperatorChar() {
            return operatorChar;
        }

        public BiFunction<Integer, Integer, Integer> getFunction() {
            return function;
        }

        public static OperatorEnum getFromOperatorChar(char operatorChar) {
            if (map == null) {
                map = new HashMap<>();
                for (OperatorEnum value : values()) {
                    map.put(value.getOperatorChar(), value);
                }
            }
            return map.get(operatorChar);
        }
    }


}
