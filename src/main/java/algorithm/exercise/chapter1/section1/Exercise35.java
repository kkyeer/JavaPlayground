package algorithm.exercise.chapter1.section1;

import java.util.Random;

public class Exercise35 {
    public static void main(String[] args) {
        double[] dist = calDist();
        System.out.println("std");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(dist[i]);
        }

        int times = 1000000;
        double[] actualDist = difference(dist, times);
        boolean result = true;
        System.out.println("Actual");
        for (int i = 0; i < actualDist.length; i++) {
            System.out.println(actualDist[i]);
            if (Math.abs(actualDist[i] - dist[i]) >= 0.001) {
                result = false;
                break;
            }
        }
        System.out.println(result);
    }

    public static double[] difference(double[] targetDist, int times) {
        int SIZES = 6;
        Random random = new Random();
        double[] actualDist = new double[targetDist.length];
        int val1, val2;
        for (int i = 0; i < times; i++) {
            val1 = random.nextInt(SIZES) + 1;
            val2 = random.nextInt(SIZES) + 1;
            actualDist[val1 + val2] += 1.0;
        }
        for (int i = 0; i < actualDist.length; i++) {
            actualDist[i] /= times;
        }
        return actualDist;
    }

    public static double[] calDist() {
        final int SIDES = 6;
        double dist[] = new double[2 * SIDES + 1];
        for (int i = 1; i <= SIDES; i++) {
            for (int j = 1; j <= SIDES; j++) {
                dist[i + j] += 1.0;
            }
        }
        for (int i = 0; i < dist.length; i++) {
            dist[i] /= 36.0;
        }
        return dist;
    }

}
