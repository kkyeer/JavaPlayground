# java.util.HashMap<K,V> 源码解析-增删改

## 1. 构造方法

public HashMap(int initialCapacity, float loadFactor)

- initialCapacity:初始容量，传入时，threshold(下次扩容阈值)为tableSizeFor(initialCapacity)即向上找最接近的2的整数次幂，不传入默认为0，在第一次put元素时，table为空不够用导致resize()，resise后capacity为默认值16，threshold为16*loadFactor;
- loadFactor:扩容因子

## 2. 增删改方法

## 2.1. 增

实际调用下面的方法来进行新增操作：

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```

### 2.1.2 过程

- 1)如果没初始化，调用resize方法
- 2)k-v对的默认位置为index=(capacity-1)&hash
- 3)若index的位置没有值（即不冲突），则将k-v对包裹进Node对象放入，Node对象的next为null，返回null
- 4)如果key.equals(原来的key),证明是值的覆盖，返回原来的值，在!onlyIfAbsent为真时，替换值
- 2)如果!key.equals(原来的key),证明是产生hash冲突,如下

### 2.1.3 hash冲突的处理

#### 2.1.3.1 原节点已经是TreeNode，则调用TreeNode对象的putVal方法

 ```java
 e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value)
 ```

 详见TreeNode描述

#### 2)原节点不是TreeNode,即当前还是链状存储，则：

  1. 遍历此链，若有key与传入hash相等或equal的，则替换值，返回原值
  2. 若不是替换的情况，则将新K-V对包裹到Node里，并挂到最后一个节点后，并 判断当前链长是否>=TREEIFY_THRESHOLD - 1(8-1=7),若是则将此处的节点树化treeifyBin(tab, hash)

***

## 3.treeifyBin方法，将节点Node链转为红黑树

- final void treeifyBin(Node<K,V>[] tab, int hash)
- 过程如下：
1. 当HashMap内部的table的长度小于MIN_TREEIFY_CAPACITY时，仅仅resize一下
2. 当HashMap内部的table长度大于等于MIN_TREEIFY_CAPACITY时,先遍历当前hash一致的链，组成双向链表，链表内存储的是TreeNode实例，再调用头部TreeNode的treeify(tab)方法，生成红黑树，具体参考TreeNode的treeify方法

***

## 3.final Node<K,V>[] resize()

- 重新计算Map中table的尺寸
- table为null的时候，根据initialCapacity分配空间，table不为null的时候，每个Node里的元素要么不动，要么平移2^n^个位置
- 返回resize后的Node数组
