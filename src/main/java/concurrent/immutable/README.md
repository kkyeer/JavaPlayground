# 带缓存的计算器测试—Atomic组合使用

## 前言

虽然Atomic类的实例本身线程安全，但是组合使用时，仍旧

## 接口说明

假设当前需要实现一个带缓存的计算器，其中一个方法为，传入一个整数（任意长度），返回此整数的整数次方，
由于计算耗费资源较多，因此将上次的计算结果进行缓存，若命中缓存则返回缓存的值

## 测试用代码

```java
class TestCase {
    private static final int RANDOM_SIZE = 3;
    public static void main(String[] args) {
        Calculator calculator = new NoneSafeCachedCalculator();
        Random random = new Random();
        for (int i = 1; i < 10; i++) {
            if(i>RANDOM_SIZE){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            BigInteger input = new BigInteger("1" + random.nextInt(RANDOM_SIZE));
            new Thread(()->{
                BigInteger[] result = calculator.powers(input);
                if (!result[0].equals(input) || !result[1].equals(input.multiply(input))) {
                    System.out.println("----WRONG----Input["+(input)+"]:"+result[0]+"-"+result[1]+"-"+result[2]);
                }else {
                    System.out.println("Input:"+(input)+":"+result[0]+"-"+result[1]+"-"+result[2]);
                }
            }).start();
        }
    }
}
```
当计算过程有错误时，输出中有----WRONG----前缀

## 线程不安全的程序
```java
NoneSafeCachedCalculator
```
线程不安全的原因为：即使存放上次输入和上次输出的变量已经是Atomic，但只能保证对这两个变量本身的存取是线程安全的，程序中对两个变量存取的动作加起来并不是线程安全的

## 线程安全的程序

将读写需要原子操作的多个变量，存入一个不可变的变量中，在使用时，使用volatile关键字修饰此不可变变量。

关键代码如下：
```java
class PowerCache {
    private final BigInteger number;
    private final BigInteger[] powers;

    PowerCache(BigInteger number, BigInteger[] powers) {
        this.number = number;
        this.powers = powers==null?null:Arrays.copyOf(powers,powers.length);
    }

    BigInteger[] getPowers(BigInteger number) {
        if(number!=null&&number.equals(this.number)){
            return powers;
        }
        return null;
    }
}
```

关键点有两处：
1. 保证存放状态的类内部状态不可被修改，手段包括私有化构造器和内部状态，仅提供带参构造器
2. 存放状态的类提供带参构造器时，注意非primitive类型，应使用拷贝，避免逃逸引起的状态变化
3. 要了解，一个线程内部的变量是线程独有的，不会共享，因此解决问题的关键是，在访问共享变量时保证原子性
