package algorithm.exercise.chapter1.section1;

import algorithm.std.StdOut;

public class Exercise30 {

    public static void main(String[] args) {
        boolean[][] result = nTableForPrime(6);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                StdOut.print(result[i][j] + " ");
            }
            StdOut.println();
        }

    }

    public static boolean[][] nTableForPrime(int n) {
        boolean[][] result = new boolean[n][n];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (i == 0 || j == 0) {
                    result[i][j] = (i == 0 && j == 1) || (j == 0 && i == 1);
                }
                if (gcd(i, j) > 1) {
                    result[i][j] = false;
                } else {
                    result[i][j] = true;
                }
            }
        }
        return result;
    }

    public static int gcd(int m, int n) {
        if (n == 0) {
            return m;
        }
        return gcd(n, m % n);
    }
}
