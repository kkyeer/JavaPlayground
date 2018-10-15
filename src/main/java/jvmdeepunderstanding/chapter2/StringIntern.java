package jvmdeepunderstanding.chapter2;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:02 PM 9/17/18
 * @Modified By:
 */
public class StringIntern {
    public static void main(String[] args) {
        String str1 = new StringBuilder("沙").append("发").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String str3 = new StringBuilder("open").append("jdk").toString();
        System.out.println(str3.intern() == str3);
    }
}
