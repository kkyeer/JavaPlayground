# SimpleDateFormat非线程安全

## 1. 演示

```java
    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("s");
        AtomicInteger second = new AtomicInteger(0);
        ConcurrentTest.test(
                ()-> {
                    int index = second.incrementAndGet();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.SECOND,index);
                    Date toFormat = calendar.getTime();
                    for (int i = 0; i < 10000; i++) {
                        String parsed = dateFormat.format(toFormat);
                        if (index != Integer.parseInt(parsed)) {
                            System.err.println(index + "-" + parsed);
                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
```

理论上，index 应该永远等于 Integer.parseInt(parsed)，程序不输出错误，实际运行时，程序会大量输出error

## 2. 源码解析

SimpleDateFormat实例初始化时，会初始化一个共享的calendar实例，在format的过程中，第一步是将传入的Date赋值到共享的calendar实例，由于整个过程没有加锁，因此，多个线程使用同一个SimpleDateFormat实例时，有概率calendar实例赋值入参后立马被其他线程篡改，导致输出错误：

```java

private StringBuffer format(Date date, StringBuffer toAppendTo,
                                FieldDelegate delegate) {
        // Convert input date to time field list
        calendar.setTime(date);
        // 。。。。。。
        return toAppendTo;
    }
```

## 3. 解决方案

### 3.1 使用ThreadLocal来保存dateFormat实例

```java
DateFormat dateFormat = dateFormatThreadLocal.get();
if (dateFormat == null) {
    dateFormat = new SimpleDateFormat("s");
    dateFormatThreadLocal.set(dateFormat);
}
```
