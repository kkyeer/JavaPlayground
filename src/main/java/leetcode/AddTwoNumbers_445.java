package leetcode;

import utils.Assertions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 445. 两数相加 II
 * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 *
 *
 *
 * 进阶：
 *
 * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
 *
 *
 *
 * 示例：
 *
 * 输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 8 -> 0 -> 7
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-4-14 09:25
 * @Modified By:
 */
public class AddTwoNumbers_445 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(7);
        ListNode add1 = listNode;
        listNode = listNode.next = new ListNode(2);
        listNode = listNode.next = new ListNode(4);
        listNode = listNode.next = new ListNode(3);
        listNode = new ListNode(5);
        ListNode add2 = listNode;
        listNode = listNode.next = new ListNode(6);
        listNode = listNode.next = new ListNode(4);

        ListNode result = addTwoNumbers(add1, add2);
        Assertions.equal(7, result.val);
        Assertions.equal(8, (result = result.next).val);
        Assertions.equal(0, (result = result.next).val);
        Assertions.equal(7, result.next.val);

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
        l1 = flip(l1);
        l2 = flip(l2);
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
        return flip(result);
    }

    public static ListNode flip(ListNode l1) {
        // 翻转
        List<Integer> digits1 = new ArrayList<>();
        while (l1 != null) {
            digits1.add(l1.val);
            l1 = l1.next;
        }
        ListNode original = new ListNode(0);
        ListNode pointer = original;
        for (int i = digits1.size()-1; i >= 0; i--) {
            pointer.val = digits1.get(i);
            if (i != 0) {
                pointer.next = new ListNode(0);
                pointer = pointer.next;
            }
        }
        return original;
    }
}