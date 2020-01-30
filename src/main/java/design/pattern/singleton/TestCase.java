package design.pattern.singleton;

import utils.Assertions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试安全性,获取一个类的实例，有四种方法，即new对象，clone对象，反序列化，反射四种，都要测试
 * @Date:Created in 10:57 2019/6/24
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 测试调用静态方法
//        testNormalInvoke();
        // 测试序列化与反序列化
        testSerialization();
        // 测试反射
        testReflection();
        // 测试clone方法
//        testClone();
    }

    /**
     * 测试各种单例的实现中，调用clone方法能否破坏单例的唯一性
     */
    private static void testClone() throws CloneNotSupportedException {
        LazyLoadWithInnerClass instance1 = LazyLoadWithInnerClass.getInstance();
        Singleton instance2 = (Singleton) instance1.clone();
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance1==instance2);
    }

    /**
     * 由于构造器私有，所以测试调用静态方法的线程安全性
     */
    private static void testThreadSafety() {
        final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors()*300;
        System.out.println("TESTING WITH "+TEST_THREAD_COUNT+" THREADS");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(TEST_THREAD_COUNT + 1);
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        AtomicInteger instanceHash = new AtomicInteger(0);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            executorService.submit(
                    ()->{
                        try {
                            cyclicBarrier.await();
//                            Singleton created = PlainNotSafe.getInstance();
//                            Singleton created = LoadAhead.getInstance();
//                            Singleton created = LazyLoadWithOneSynchronization.getInstance();
//                            Singleton created = LazyLoadWithDoubleCheckSynchronization.getInstance();
//                            Singleton created = LazyLoadWithInnerClass.getInstance();
                            Singleton created = LazyLoadWithEnum.INSTANCE.getInstance();
                            instanceHash.compareAndSet(0, created.hashCode());
                            Assertions.assertTrue(instanceHash.get() == created.hashCode());
                            Assertions.assertTrue(instanceHash.get() != 0);
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        } catch (AssertionError exception) {
                            System.out.println("wrong");
//                            exception.printStackTrace();
                        } finally {
                            try {
                                cyclicBarrier.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
        long start = System.nanoTime();
        try {
            cyclicBarrier.await();
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        long duration = (System.nanoTime() - start)/1000000;
        System.out.println(duration+" milli seconds");
        executorService.shutdownNow();
    }

    /**
     * 测试反序列化过程中是否仍旧保持单例不变，结果显示只有枚举模式才可以
     */
    private static void testSerialization(){
//        Singleton created = PlainNotSafe.getInstance();
//        Singleton created = LoadAhead.getInstance();
//        Singleton created = LazyLoadWithOneSynchronization.getInstance();
//        Singleton created = LazyLoadWithDoubleCheckSynchronization.getInstance();
//        Singleton created = LazyLoadWithInnerClass.getInstance();
        Singleton created = LazyLoadWithEnum.INSTANCE.getInstance();
        System.out.println(created.hashCode());
        File testFile = new File("obj.txt");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(testFile));
            objectOutputStream.writeObject(created);
            objectOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(testFile));
            Singleton dematerializedObject = (Singleton) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println(dematerializedObject.hashCode());
            Assertions.assertTrue(dematerializedObject == created,"破坏了单例唯一性");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            testFile.delete();
        }
    }

    /**
     * 测试使用反射，即手动调用构造器的newInstance方法是否破坏单例
     */
    private static void testReflection()  {
        try {
//            Singleton created = LazyLoadWithDoubleCheckSynchronization.getInstance();
//            Constructor<LazyLoadWithDoubleCheckSynchronization> constructor = LazyLoadWithDoubleCheckSynchronization.class.getDeclaredConstructor();
//            constructor.setAccessible(true);
//            Singleton instanceWithReflection = constructor.newInstance();

            Singleton created = LazyLoadWithEnum.INSTANCE.getInstance();
            Constructor<LazyLoadWithEnum> constructor = LazyLoadWithEnum.class.getDeclaredConstructor(String.class, int.class);
            constructor.setAccessible(true);
            Singleton instanceWithReflection = constructor.newInstance();


            System.out.println(created.hashCode());
            System.out.println(instanceWithReflection.hashCode());
            Assertions.assertTrue(instanceWithReflection == created,"反射破坏单例唯一性");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
