//请你仅使用两个栈实现先入先出队列。队列应当支持一般队列的支持的所有操作（push、pop、peek、empty）： 
//
// 实现 MyQueue 类： 
//
// 
// void push(int x) 将元素 x 推到队列的末尾 
// int pop() 从队列的开头移除并返回元素 
// int peek() 返回队列开头的元素 
// boolean empty() 如果队列为空，返回 true ；否则，返回 false 
// 
//
// 
//
// 说明： 
//
// 
// 你只能使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
// 
// 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。 
// 
//
// 
//
// 进阶： 
//
// 
// 你能否实现每个操作均摊时间复杂度为 O(1) 的队列？换句话说，执行 n 个操作的总时间复杂度为 O(n) ，即使其中一个操作可能花费较长时间。 
// 
//
// 
//
// 示例： 
//
// 
//输入：
//["MyQueue", "push", "push", "peek", "pop", "empty"]
//[[], [1], [2], [], [], []]
//输出：
//[null, null, null, 1, 1, false]
//
//解释：
//MyQueue myQueue = new MyQueue();
//myQueue.push(1); // queue is: [1]
//myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
//myQueue.peek(); // return 1
//myQueue.pop(); // return 1, queue is [2]
//myQueue.empty(); // return false
// 
//
// 
// 
//
// 
//
// 提示： 
//
// 
// 1 <= x <= 9 
// 最多调用 100 次 push、pop、peek 和 empty 
// 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作） 
// 
// Related Topics 栈 设计 
// 👍 349 👎 0


package leetcode.editor.cn;

import utils.Assertions;

import java.util.ArrayDeque;
import java.util.Deque;

public class ImplementQueueUsingStacks232{
    public static void main(String[] args){
        MyQueue myQueue = new ImplementQueueUsingStacks232().new MyQueue();
        myQueue.push(1); // queue is: [1]
        myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)\
        myQueue.push(3);// queue is: [1, 2] (leftmost is front of the queue)\
        myQueue.push(4);// return 1
        Assertions.equal(myQueue.pop(),1);// queue is: [1, 2] (leftmost is front of the queue)\
        myQueue.push(5); // return 1, queue is [2]
        Assertions.assertTrue(!myQueue.empty()); // return false
        Assertions.equal(myQueue.pop(),2);
        Assertions.equal(myQueue.pop(),3);
        Assertions.equal(myQueue.pop(),4);
        Assertions.equal(myQueue.pop(),5);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class MyQueue {
//        优秀解法：push的时候都是压入input，pop或者peek的时候再倒转
        Deque<Integer> input = new ArrayDeque<>();
        Deque<Integer> output = new ArrayDeque<>();
    /** Initialize your data structure here. */
    public MyQueue() {
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        while (!output.isEmpty()) {
            input.push(output.pop());
        }
        input.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
        return output.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
        return output.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
//leetcode submit region end(Prohibit modification and deletion)

}