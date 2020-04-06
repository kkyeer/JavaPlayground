package leetcode;

import java.util.*;

/**
 * @author kkyeer
 * @description: 460. LFU缓存
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。它应该支持以下操作：get 和 put。
 *
 *     get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
 *     put(key, value) - 如果键不存在，请设置或插入值。当缓存达到其容量时，则应该在插入新项之前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近 最少使用的键。
 *
 * 「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。
 *
 *
 *
 * 进阶：
 * 你是否可以在 O(1) 时间复杂度内执行两项操作？
 *
 *
 *
 * 示例：
 *
 * LFUCache cache = new LFUCache( 2)
        *cache.put(1,1);
        *cache.put(2,2);
        *cache.get(1);       // 返回 1
        *cache.put(3,3);    // 去除 key 2
        *cache.get(2);       // 返回 -1 (未找到key 2)
        *cache.get(3);       // 返回 3
        *cache.put(4,4);    // 去除 key 1
        *cache.get(1);       // 返回 -1 (未找到 key 1)
        *cache.get(3);       // 返回 3
        *cache.get(4);       // 返回 4
        *
        *来源：力扣（LeetCode）
        *链接：https://leetcode-cn.com/problems/lfu-cache
        *著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
// * @date:Created in 21:57 4-5
// * @modified By:
// */
public class LFU_460 {
    static class LFUCache {
        public Map<Integer, Integer> cache;
        private Map<Integer, Integer> keyToTimes;
        private Map<Integer, LinkedList<Integer>> timesToKey;
        private int minTimes = 0;

        private int capacity;

        public LFUCache(int capacity) {
            cache = new HashMap<>(capacity);
            keyToTimes = new HashMap<>(capacity);
            timesToKey = new HashMap<>(capacity);
            this.capacity = capacity;
        }

        public int get(int key) {
            Integer value = cache.get(key);
            if (value != null) {
                int addedTimes = keyToTimes.get(key) + 1;
                adjustTimes(key,addedTimes);
            }
            return value != null ? value : -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (cache.size() == capacity && !cache.containsKey(key)) {
                LinkedList<Integer> minTimeKeyList = timesToKey.get(minTimes);
                if (minTimeKeyList != null && !minTimeKeyList.isEmpty()) {
                    cache.remove(minTimeKeyList.getLast());
                    keyToTimes.remove(minTimeKeyList.getLast());
                    minTimeKeyList.removeFirst();
                }
            }
            cache.put(key, value);
            Integer times = keyToTimes.get(key);
            if (times == null) {
                keyToTimes.put(key, 0);
                minTimes = 0;
                adjustTimes(key, 1);
            }else {
                keyToTimes.put(key, times + 1);
                adjustTimes(key, times + 1);
            }
        }

        public void adjustTimes(int key,int addedTimes){
            keyToTimes.put(key, addedTimes);

            LinkedList<Integer> lastTimesList = timesToKey.get(addedTimes - 1);
            if(lastTimesList!=null){
                lastTimesList.remove((Integer)key);
                if (addedTimes - 1 == minTimes) {
                    if (lastTimesList.isEmpty()) {
                        minTimes = addedTimes;
                    }
                }
            }
            LinkedList<Integer> addedTimesList = timesToKey.getOrDefault(addedTimes, new LinkedList<>());
            addedTimesList.addFirst(key);
            timesToKey.put(addedTimes, addedTimesList);
        }
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache( 10 /* capacity (缓存容量) */ );

//        cache.put(1, 1);
//        cache.put(2, 2);
//        System.out.println(cache.get(1));;       // 返回 1
//        cache.put(3, 3);    // 去除 key 2
//        System.out.println(cache.get(2));;       // 返回 -1 (未找到key 2)
//        System.out.println(cache.get(3));;       // 返回 3
//        cache.put(4, 4);    // 去除 key 1
//        System.out.println(cache.get(1));;       // 返回 -1 (未找到 key 1)
//        System.out.println(cache.get(3));;       // 返回 3
//        System.out.println(cache.get(4));;       // 返回 4

//        cache.put(3, 1);
//        cache.put(2, 1);
//        cache.put(2, 2);
//        cache.put(4, 4);
//        System.out.println(cache.get(2));

//        System.out.println(cache.get(2));
//        cache.put(2, 6);
//        System.out.println(cache.get(1));
//        cache.put(1, 5);
//        cache.put(1, 2);
//        System.out.println(cache.get(1));
//        System.out.println(cache.get(2));

        cache.put(10,13);
        cache.put(3,17);
        cache.put(6,11);
        cache.put(10,5);
        cache.put(9,10);
        System.out.println(cache.get(13));
        cache.put(2,19);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        cache.put(5,25);
        System.out.println(cache.get(8));
        cache.put(9,22);
        cache.put(5,5);
        cache.put(1,30);
        System.out.println(cache.get(11));
        cache.put(9,12);
        System.out.println(cache.get(7));
        System.out.println(cache.get(5));
        System.out.println(cache.get(8));
        System.out.println(cache.get(9));
        cache.put(4,30);
        cache.put(9,3);
        System.out.println(cache.get(9));
        System.out.println(cache.get(10));
        System.out.println(cache.get(10));
        cache.put(6,14);
        cache.put(3,1);
        System.out.println(cache.get(3));
        cache.put(10,11);
        System.out.println(cache.get(8));
        cache.put(2,14);
        System.out.println(cache.get(1));
        System.out.println(cache.get(5));
        System.out.println(cache.get(4));
        cache.put(11,4);
        cache.put(12,24);
        cache.put(5,18);
        System.out.println(cache.get(13));
        cache.put(7,23);
        System.out.println(cache.get(8));
        System.out.println(cache.get(12));
        cache.put(3,27);
        cache.put(2,12);
        System.out.println(cache.get(5));
        cache.put(2,9);
        cache.put(13,4);
        cache.put(8,18);
        cache.put(1,7);
        System.out.println(cache.get(6));
        cache.put(9,29);
        cache.put(8,21);
        System.out.println(cache.get(5));
        cache.put(6,30);
        cache.put(1,12);
        System.out.println(cache.get(10));
        cache.put(4,15);
        cache.put(7,22);
        cache.put(11,26);
        cache.put(8,17);
        cache.put(9,29);
        System.out.println(cache.get(5));
        cache.put(3,4);
        cache.put(11,30);
        System.out.println(cache.get(12));
        cache.put(4,29);
        System.out.println(cache.get(3));
        System.out.println(cache.get(9));
        System.out.println(cache.get(6));
        cache.put(3,4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(10));
        cache.put(3,29);
        cache.put(10,28);
        cache.put(1,20);
        cache.put(11,13);
        System.out.println(cache.get(3));
        cache.put(3,12);
        cache.put(3,8);
        cache.put(10,9);
        cache.put(3,26);
        System.out.println(cache.get(8));
        System.out.println(cache.get(7));
        System.out.println(cache.get(5));
        cache.put(13,17);
        cache.put(2,27);
        cache.put(11,15);
        System.out.println(cache.get(12));
        cache.put(9,19);
        cache.put(2,15);
        cache.put(3,16);
        System.out.println(cache.get(1));
        cache.put(12,17);
        cache.put(9,1);
        cache.put(6,19);
        System.out.println(cache.get(4));
        System.out.println(cache.get(5));
        System.out.println(cache.get(5));
        cache.put(8,1);
        cache.put(11,7);
        cache.put(5,2);
        cache.put(9,28);
        System.out.println(cache.get(1));
        cache.put(2,2);
        cache.put(7,4);
        cache.put(4,22);
        cache.put(7,24);
        cache.put(9,26);
        cache.put(13,28);
        cache.put(11,26);

    }

    private static void print(){

//        String handler = "\"put\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"get\",\"get\",\"put\",\"get\",\"put\",\"put\",\"put\",\"get\",\"put\",\"get\",\"get\",\"get\",\"get\",\"put\",\"put\",\"get\",\"get\",\"get\",\"put\",\"put\",\"get\",\"put\",\"get\",\"put\",\"get\",\"get\",\"get\",\"put\",\"put\",\"put\",\"get\",\"put\",\"get\",\"get\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"get\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"get\",\"put\",\"get\",\"get\",\"get\",\"put\",\"get\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"get\",\"get\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"get\",\"get\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"put\",\"put\",\"put\"";
//        String num = "10,13],[3,17],[6,11],[10,5],[9,10],[13],[2,19],[2],[3],[5,25],[8],[9,22],[5,5],[1,30],[11],[9,12],[7],[5],[8],[9],[4,30],[9,3],[9],[10],[10],[6,14],[3,1],[3],[10,11],[8],[2,14],[1],[5],[4],[11,4],[12,24],[5,18],[13],[7,23],[8],[12],[3,27],[2,12],[5],[2,9],[13,4],[8,18],[1,7],[6],[9,29],[8,21],[5],[6,30],[1,12],[10],[4,15],[7,22],[11,26],[8,17],[9,29],[5],[3,4],[11,30],[12],[4,29],[3],[9],[6],[3,4],[1],[10],[3,29],[10,28],[1,20],[11,13],[3],[3,12],[3,8],[10,9],[3,26],[8],[7],[5],[13,17],[2,27],[11,15],[12],[9,19],[2,15],[3,16],[1],[12,17],[9,1],[6,19],[4],[5],[5],[8,1],[11,7],[5,2],[9,28],[1],[2,2],[7,4],[4,22],[7,24],[9,26],[13,28],[11,26";
//        String[] handlers = handler.split("\\,");
//        String[] nums = num.split("\\]\\,\\[");
//        for (int i = 0; i < handlers.length; i++) {
//            String op = handlers[i];
//            String opNum = nums[i];
//            if (op.equals("\"put\"")) {
//                System.out.println("cache.put(" + opNum.substring(0, opNum.indexOf(","))+","+opNum.substring(opNum.indexOf(",")+1,opNum.length())+");");
//            }else {
//                System.out.println("System.out.println(cache.get("+opNum+"));");
//            }
//        }
    }
}
