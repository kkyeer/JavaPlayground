package algorithm.exercise.chapter1.section2;

public class Exercise6 {
    public static void main(String[] args) {
        System.out.println(isMutualCircularRotation("ACTGACG", "TGACGAC"));
    }

    private static boolean isMutualCircularRotation(String s, String t) {
        // if (s.length() != t.length()) {
        //     return false;
        // }
        // String temp = t;
        // for (int i = 0; i < s.length(); i++) {
        //     if (s.equals(temp)) {
        //         return true;
        //     }
        //     temp = t.substring(i, t.length()) + t.substring(0, i);
        // }
        // return false;
        return s.length() == t.length() && (s+s).contains(t);
    }
}
