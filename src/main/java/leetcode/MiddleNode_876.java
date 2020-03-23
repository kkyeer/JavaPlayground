package leetcode;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 876. 链表的中间结点
 * 给定一个带有头结点 head 的非空单链表，返回链表的中间结点。
 *
 * 如果有两个中间结点，则返回第二个中间结点。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[1,2,3,4,5]
 * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
 * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
 * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
 *
 * 示例 2：
 *
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
 * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
 *
 *
 *
 * 提示：
 *
 *     给定链表的结点数介于 1 和 100 之间。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/middle-of-the-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-23 13:12
 * @Modified By:
 */
public class MiddleNode_876 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6};
        ListNode head = null, pre = null;
        for (int i = 0; i < arr.length; i++) {
            ListNode current = new ListNode(arr[i]);
            if (pre == null) {
                pre = current;
                head = current;
            }else {
                pre.next = current;
                pre = current;
            }
        }
        System.out.println(new MiddleNode_876().middleNode(head));
    }

    private ListNode middleNode(ListNode head){
        if (head == null) {
            return null;
        }
        ListNode mid = head;
        while (head.next != null) {
            head = head.next;
            mid = mid.next;
            if (head.next != null) {
                head = head.next;
            }else {
                break;
            }
        }
        return mid;
    }

    private static class ListNode{
        int value;
        ListNode next;

        public ListNode(int i) {
            this.value = i;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", ListNode.class.getSimpleName() + "[", "]")
                    .add("value=" + value)
                    .add("next=" + next)
                    .toString();
        }
    }
}