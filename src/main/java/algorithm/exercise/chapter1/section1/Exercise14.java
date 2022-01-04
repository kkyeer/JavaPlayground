package algorithm.exercise.chapter1.section1;

public class Exercise14 {
    public static int lg(int n) {
        int count = 0;
        while ((n = n >> 1) > 0) {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(lg(1));
    }
}
