package concurrent.threadlocal;

/**
 * @Author: kkyeer
 * @Description: 试用下InheritableThreadLocal
 * @Date:Created in 17:04 2019/7/30
 * @Modified By:
 */
class TasteInheritableThreadLocal {
    public static void main(String[] args) {
        InheritableThreadLocal<String> parentThreadLocal= new InheritableThreadLocal<>();
        parentThreadLocal.set("ParentValue");
        new Thread(
                () -> System.out.println("Child thread got value from parent:" + parentThreadLocal.get())
        ).start();
    }
}
