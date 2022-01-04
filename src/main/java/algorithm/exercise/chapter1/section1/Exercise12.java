package algorithm.exercise.chapter1.section1;

import algorithm.std.StdOut;

public class Exercise12 {

    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            a[i] = 9 - i;            
        }
        for (int i = 0; i < 10; i++) {
            a[i] = a[a[i]];
        }
        for (int i = 0; i < 10; i++) {
            StdOut.println(a[i]);
        }
    }
    
}
