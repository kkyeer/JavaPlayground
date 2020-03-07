package leetcode;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @Author: kkyeer
 * @Description: 面试题59队列的最大值
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的时间复杂度都是O(1)。【后来官方做了一次题目更改，修改为**均摊**时间复杂度都是O(1)，私以为表述不妥)
 *
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 *
 * 示例 1：
 *
 * 输入:
 * ["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
 * [[],[1],[2],[],[],[]]
 * 输出: [null,null,null,2,1,2]
 *
 * 示例 2：
 *
 * 输入:
 * ["MaxQueue","pop_front","max_value"]
 * [[],[],[]]
 * 输出: [null,-1,-1]
 *
 *
 *
 * 限制：
 *
 *     1 <= push_back,pop_front,max_value的总操作数 <= 10000
 *     1 <= value <= 10^5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/dui-lie-de-zui-da-zhi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 08:20 3-7
 * @Modified By:
 */
class MaxQueue_M59 {
    public static void main(String[] args) {
        MaxQueue maxQueue = new MaxQueue();
        maxQueue.push_back(1);
        maxQueue.push_back(2);
        System.out.println(maxQueue.max_value());
    }

    static class MaxQueue {
        //        此题题目有问题，最开始表述为三种操作时间复杂度均为O(1),没有想到解法，后来改为均摊时间复杂度为O(1)，等于为O(n)的排序插入操作改时间复杂度定义，有问题
        // deque 保存大的值
        private LinkedList<Integer> sortedDeque = new LinkedList<>();
        private LinkedList<Integer> queue = new LinkedList<>();
        public MaxQueue() {}

        public int max_value() {
            return sortedDeque.isEmpty() ? -1 : sortedDeque.getFirst();
        }

        public void push_back(int value) {
            queue.addLast(value);
            if (sortedDeque.isEmpty()) {
                sortedDeque.add(value);
            }else {
                ListIterator<Integer> sortedListIterator = sortedDeque.listIterator(sortedDeque.size());
                while (sortedListIterator.hasPrevious() && sortedListIterator.previous() < value) {
                    sortedListIterator.remove();
                }
                sortedDeque.addLast(value);
            }
        }

        public int pop_front() {

            try {
                int result = queue.pollFirst();
                if (sortedDeque.getFirst() == result) {
                    sortedDeque.removeFirst();
                }
                return result;
            } catch (Exception e) {
                return -1;
            }
        }
    }

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */
}
