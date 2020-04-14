package leetcode;

/**
 * @Author: kkyeer
 * @Description: 2. 两数相加
 * @Date:Created in  2020-4-14 09:25
 * @Modified By:
 */
public class AddTwoNumbers_2 {
    public static void main(String[] args) {

    }
    private static class ListNode{
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode iterator = null;
        int remainAdd = 0;
        while (l1 != null || l2 != null) {
            int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + remainAdd;
            l1 = l1 != null ? l1.next : l1;
            l2 = l2 != null ? l2.next : l2;
            if (iterator == null) {
                iterator = new ListNode(sum % 10);
                result = iterator;
            } else {
                iterator.next = new ListNode(sum % 10);
                iterator = iterator.next;
            }
            remainAdd = sum / 10;
        }
        if (remainAdd != 0) {
            iterator.next = new ListNode(remainAdd);
        }
        return result;
    }
}