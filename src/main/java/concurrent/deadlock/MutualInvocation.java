package concurrent.deadlock;

/**
 * @Author: kkyeer
 * @Description: 互相调用导致死锁
 * @Date:Created in 16:18 2019/6/4
 * @Modified By:
 */
class MutualInvocation {

    public static void main(String[] args) throws InterruptedException {
        class Invoker{
            private Invoker target;

            void setTarget(Invoker target) {
                this.target = target;
            }

            void invoke() throws InterruptedException {
                synchronized (this){
                    System.out.println("acquired intrinsic lock");
                    Thread.sleep(1000);
                    synchronized (target) {
                        System.out.println("acquired target lock");
                        target.invoke();
                    }
                }
            }
        }

        Invoker invokerA = new Invoker();
        Invoker invokerB = new Invoker();
        invokerA.setTarget(invokerB);
        invokerB.setTarget(invokerA);
        new Thread(
                ()->{
                    try {
                        invokerA.invoke();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        invokerB.invoke();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}
