package effectivejava3.AnoymousInnerClassInheritation;

import share.Printer;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/1 17:37
 */
public class Parent {
    Printer printer = new Printer() {
        @Override
        public void print(String inString) {
            System.out.println("Parent printer start");
            System.out.println(inString);
        }
    };

    void test(){
        this.printer.print("This is in parent class");
    }

    public static void main(String[] args) {
        new Parent().test();
    }
}
