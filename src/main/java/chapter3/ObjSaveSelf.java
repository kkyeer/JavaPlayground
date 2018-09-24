package chapter3;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:53 PM 9/19/18
 * @Modified By:
 */
public class ObjSaveSelf {
    static ObjSaveSelf SAVE_HOOK;

    void printAlive(){
        System.out.println("I am alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize triggered");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new ObjSaveSelf();
        SAVE_HOOK=null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK!=null) {
            SAVE_HOOK.printAlive();
        }else {
            System.out.println("He is dead");
        }

        SAVE_HOOK=null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK!=null) {
            SAVE_HOOK.printAlive();
        }else {
            System.out.println("He is dead");
        }
    }
}
