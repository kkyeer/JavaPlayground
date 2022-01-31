package algorithm.exercise.chapter1.section2;

import java.security.InvalidParameterException;

public class RationalNumber {
    /**
     * 分子
     */
    private final int numerator;

    /**
     * 分母
     */
    private final int denominator;

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public RationalNumber(int numerator, int denominator) {
        if (denominator == 0) {
            throw new InvalidParameterException("分母不能为0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public RationalNumber plus(RationalNumber addend) {
        int numerator = this.numerator * addend.getDenominator() + this.denominator * addend.getNumerator();
        int denominator = this.denominator * addend.getDenominator();
        int gcd = gcd(numerator, denominator);
        return new RationalNumber(numerator / gcd, denominator / gcd);
    }

    public static void main(String[] args) {
        RationalNumber r1 = new RationalNumber(1, 2);
        RationalNumber r2 = new RationalNumber(1, 4);
        System.out.println(r1.plus(r2));
    }

    /**
     * 最大公约数
     * 
     * @param a 数字1
     * @param b 数字2
     * @return 最大公约数
     */
    private static int gcd(int a, int b) {
        if (a < b) {
            a = a ^ b;
            b = a ^ b;
            a = a ^ b;
        }
        if (a % b == 0) {
            return b;
        } else {
            return gcd(b, a % b);
        }
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

}
