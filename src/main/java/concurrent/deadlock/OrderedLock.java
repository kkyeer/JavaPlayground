package concurrent.deadlock;

/**
 * @Author: kkyeer
 * @Description: 带顺序的锁
 * @Date:Created in 17:06 2019/6/4
 * @Modified By:
 */
class OrderedLock {
    private static class ErrorProneAccount implements Account{
        public void transfer(Account target, int amount) {
            try {
                synchronized (this) {
                    System.out.println("before transfer,check balance and so on");
                    // IO time estimated 1 second
                    Thread.sleep(1000);
                    synchronized (target) {
                        // IO time estimated 1 second
                        Thread.sleep(1000);
                        System.out.println("transferred money");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SolvedDeadlockAccount implements Account{
        public void transfer(Account target, int amount) {
            Account lockA = System.identityHashCode(this)<=System.identityHashCode(target)?this:target;
            Account lockB = System.identityHashCode(this)<=System.identityHashCode(target)?target:this;
            try {
                synchronized (lockA) {
                    System.out.println("before transfer,check balance and so on");
                    // IO time estimated 1 second
                    Thread.sleep(1000);
                    synchronized (lockB) {
                        // IO time estimated 1 second
                        Thread.sleep(1000);
                        System.out.println("transferred money");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {


        Account accountA = new ErrorProneAccount();
        Account accountB = new ErrorProneAccount();


//        Account accountA = new SolvedDeadlockAccount();
//        Account accountB = new SolvedDeadlockAccount();
        new Thread(
                ()-> accountA.transfer(accountB,100)
        ).start();
        new Thread(
                ()-> accountB.transfer(accountA,100)
        ).start();
    }
}
