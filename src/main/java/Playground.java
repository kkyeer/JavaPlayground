import java.util.ArrayList;

/**
 * @Author: kkyeer
 * @Description: 代码操场随便玩
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */

public class Playground {
    public static void main(String[] args) {
        ArrayList<String> original = new ArrayList<>();
        original.add("aa");
        original.add("bb");
        String[] result = (String[]) original.toArray();
        result = original.toArray(new String[0]);
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
