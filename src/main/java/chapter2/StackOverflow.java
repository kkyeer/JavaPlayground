package chapter2;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 1:42 AM 9/16/18
 * @Modified By:
 */
public class StackOverflow {
    private long stackDepth = 0;
    void recursiveMethod(){
        stackDepth++;
        recursiveMethod();
    }

    public static void main(String[] args) {
        StackOverflow stackOverflow = new StackOverflow();
        try {
            stackOverflow.recursiveMethod();
        }catch (Throwable e){
            System.out.println("depth:"+stackOverflow.stackDepth);
            throw e;
        }

    }
}
