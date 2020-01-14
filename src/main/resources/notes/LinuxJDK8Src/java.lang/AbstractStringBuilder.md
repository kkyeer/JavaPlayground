# AbstractStringBuilder

## 1. 概述

基本的StringBuilder类，内部维护一个char[]数组value和int变量count，前者存储数据，但容量可能大于实际存储数据的大小，后者存储实际存储的数据的偏移量，即value.length>=count，实际存储的数据为value[0]-value[count]

## 2. 实现TIP

### 2.1. 最大容量MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8

内部char数组的最大长度，MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8，少8个字符（char）的原因是部分VM保留部分数组内容为header（Some VMs reserve some header words in an array.）

### 2.2. 扩容

调用ensureCapacity(int minimumCapacity)可以扩容，扩容后大小的计算方式：(原容量*2+2)如果不够就选择minimumCapacity和（MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8）的较小值

### 2.3. Trim

与扩容相反，取value的前n个元素组装成新数组并替换

### 2.4. CodePoint相关方法

调用Character类中的相关实现方法

### 2.5. append及重载方法

1. append(Object o)中如果o==null,会插入一个字符串"null"
2. append(boolean o)中插入的是字符串"true"或者"false"
3. 浮点数的处理实际上是FloatingDecimal.appendTo(f,this)方法

### 2.6. 