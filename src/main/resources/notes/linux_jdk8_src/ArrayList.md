# Linux_JDK_8源码：ArrayList\<E>

## 1. 线程安全

ArrayList不是线程安全的，相对应的Vector类与ArrayList实现相同，但是是线程安全的，另外的获取线程安全List对象的方法是```Collections.synchronizedList(new ArrayList<>()```,其内部使用传入的List对象来作为锁，使用syncronized关键字来同步所有的操作，可用但实际上将所有操作串行化了，效率低
其内部存储了一个modCount字段，其存储了本对象的structural change(add,remove操作，set不算)的次数，每次操作会自增，这个变量会在后续迭代器中判断是否有并发修改，并在检测到并发修改的第一时间抛出异常，细节见下文迭代器

## 2. capacity和size

1. capacity是内部的数组的大小，内部不存储，使用时实时取elementData.length计算，size是实际存储的对象数量
2. 初始化不指定容量时，初始化过程内部为空数组，第一次add时取10和目标容量的较小值
3. capacity扩容到n：尝试扩容1.5倍左右(```int newCapacity = oldCapacity + (oldCapacity >> 1);```),如果还不够，则扩容到n；如果扩容后大于最大允许容量（ Integer.MAX_VALUE - 8），最大允许扩容到Integer.MAX_VALUE，注意capacity为int型，因此ArrayList的上限为Integer.MAX_VALUE即2^32^;对于部分虚拟机，数组需要占用1-2个字节来存储header words，所以一般会再减去8个字节
4. 一般来说，add和remove操作会在适当时候自动触发capacity的扩容，但是如果程序预计到需要一次性放入很多对象（远远超过1.5倍的当前size），可以考虑调用ensureCapacity(目标容量)方法进行手动扩容，避免没必要的多次触发扩容操作

## 3. 通用迭代器iterater()

1. ArrayList内部返回的迭代器提供best effort、Fail-fast的并发异常抛出，具体过程为：
    1. 调用iterator()方法时，初始化一个内部的Itr对象实例，此实例初始化时读取当前的modCount并保存为expectedModCount变量
    2. 在本迭代器实例内部操作(next(),remove())时，调用ArrayList的方法操作，这些操作内部会自增外部的modCount，完成后自增内部维护的expectedModCount变量
    3. 每次next或remove前，检查modCount和expectedModCount是否相等，不相等说明迭代器外部有并发操作，抛出ConcurrentModificationException异常
2. 此机制与外部的add,remove方法没有锁机制，因此不能基于此进行并发控制

## 4. List迭代器ListIterator

1. 此迭代器提供向前和向后的迭代方法
2. 采用与iterator相同的策略来进行快读并发检测与异常抛出

## 4. Fun fact

1. ArrayList内部使用```@SuppressWarnings("unchecked")```注解来消除类型强转的warning