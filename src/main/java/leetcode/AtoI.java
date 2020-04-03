package leetcode;

/**
 * @Author: kkyeer
 * @Description: 8. 字符串转换整数 (atoi)
 * @Date:Created in  2020-4-3 10:43
 * @Modified By:
 */
public class AtoI {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(myAtoi("      -1238712364817263487162834"));
    }

    public static int myAtoi(String str) {
        boolean signed = false;
        boolean positive = false;
        char NUM0 = '0';
        char PLUS = '+';
        char MINUS = '-';
        char NUM9 = '9';
        char SPACE = ' ';
        char[] arr = str.toCharArray();
        int result = 0;
        for (char c : arr) {
            if (!signed) {
                if (SPACE == c) {
                } else if (PLUS == c) {
                    signed = true;
                    positive = true;
                } else if (MINUS == c) {
                    signed = true;
                    positive = false;
                } else if (c >= NUM0 && c <= NUM9) {
                    signed = true;
                    positive = true;
                    result = c - NUM0;
                } else {
                    return result;
                }
            }else {
                if (c >= NUM0 && c <= NUM9){
//                    前置判断越界
                    if (positive && result > (Integer.MAX_VALUE - (c - NUM0))/10) {
                        return Integer.MAX_VALUE;
                    } else if (!positive && result < (Integer.MIN_VALUE + (c - NUM0)) / 10) {
                        return Integer.MIN_VALUE;
                    } else {
                        result = result * 10 + (positive ? 1 : -1) * (c - NUM0);
                    }
                }else {
                    return result;
                }
            }
        }
        return result;
    }
}