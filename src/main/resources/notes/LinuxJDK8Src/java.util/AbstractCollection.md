# java.util.AbstractCollection

## 1. 概述

此类为Collection接口的抽象实现类，提供了大部分的Collections接口的默认实现，同时，大部分的常用容器实现（List,Set,Map)都继承自本类，其中部分覆盖了默认的方法实现

## 2. toArray重载方法的不同逻辑

### 2.1 方法签名 

本类中提供了两个不同的重载方法来获取容量实例内部的数组:

1. 无参方法返回Object数组:

    ```java
    public Object[] toArray(){...}
    ```

2. 入参为T\[]的方法,返回带泛型的数组T\[]:

    ```java
    public <T> T[] toArray(T[] a){...}
    ```

### 2.2 实现细节

由于某些容器对于内部实际存储数据的数组的利用不是连续、有序的（典型的如HashMap)，直接返回内部的数组并不是一个可接受的策略，实际上是通过调用iterator()返回的迭代器的next()方法，并将返回的元素放入数组中返回来实现。
两个重载方法中，无参方法内部根据初次调用size()的返回值来初始化结果数组，iterator()的返回值来填充数组，其间如果发现数组容量不够大(迭代过程中有并发修改)，则进行近似1.5倍扩容。
对于传入数组的重载方法，初始化时判断size()是否适配传入的目标数组，如果目标数组不够大，则进行扩容，够大则直接使用传入的数组进行填充。

### 2.2.1 扩容后返回扩容后数组

但如果传入数组太小发生扩容并最终返回扩容后的数组，则返回数组!=传入数组，如果程序假设数据永远存入传入数组的话，本地及测试环境可能由于数据量小或者并发低运行正常，但上线后会出错，验证程序如下：

```java
    public static void main(String[] args) throws InterruptedException {
        Collection<Integer> collection = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            collection.add(i);
        }

        // 传入数组大小足够的情况，运行正常
        Integer[] canFitArray = new Integer[10];
        collection.toArray(canFitArray);
        printArray(canFitArray);

        // 输出:0,1,2,3,4,5,6,7,8,9

        // 传入数组大小不够的情况，运行错误
        Integer[] canNotFitArray = new Integer[2];
        collection.toArray(canNotFitArray);
        printArray(canNotFitArray);

        // 输出:null,null

    }

    private static void printArray(Integer[] array) {
        StringJoiner printedString = new StringJoiner(",");
        for (Integer integer : array) {
            printedString.add(integer == null ? "null" : "" + integer);
        }
        System.out.println(printedString);
    }
```



### 2.2.2 特殊情况：先扩容，后并发删除，返回源数组

某种情况下，如果先根据size()判断传入的目标数组不够大而进行扩容，但实际迭代后，由于并发删除了一些数据，导致最后的结果实际上可以存入目标数组，则将结果拷贝到先前传入目标数组并返回目标数组，具体代码如下：

```java
    public <T> T[] toArray(T[] a) {
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        T[] r = a.length >= size ? a :
                  (T[])java.lang.reflect.Array
                  .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) { // fewer elements than expected
                if (a == r) {
                    r[i] = null; // null-terminate
                } else if (a.length < i) {
                    return Arrays.copyOf(r, i);
                } else {
                    // 此时迭代完成，发现a!=r即发生过扩容，但a.length >= i,
                    // 即实际a数组足够存储数据，则将数据拷贝到a数组并返回a
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        // more elements than expected
        return it.hasNext() ? finishToArray(r, it) : r;
    }
```
