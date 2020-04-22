package leetcode;

import utils.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 199. 二叉树的右视图
 * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 *
 * 示例:
 *
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1, 3, 4]
 * 解释:
 *
 *    1            <---
 *  /   \
 * 2     3         <---
 *  \     \
 *   5     4       <---
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-right-side-view
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。'
 *
 * @Date:Created in 下午2:53 20-4-22
 * @Modified By:
 */
public class RightSideView_199 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        new RightSideView_199().test();
    }

    public void test(){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(5);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(4);
        List<Integer> result = rightSideView(root);
        Assertions.equal(3, result.size());
        Assertions.equal(1, result.get(0));
        Assertions.equal(3, result.get(1));
        Assertions.equal(4, result.get(2));


        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.right = new TreeNode(3);
        result = rightSideView(root);
        Assertions.equal(3, result.size());
        Assertions.equal(1, result.get(0));
        Assertions.equal(3, result.get(1));
        Assertions.equal(4, result.get(2));
    }

    Integer[] result;
    public List<Integer> rightSideView(TreeNode root) {
        int level = 0;
        if (root != null) {
            result = new Integer[1];
            mark(root, 0);
        }else {
            result = new Integer[0];
        }
        return Arrays.asList(result);
    }

    public void mark(TreeNode root,int level){
        if (root.right != null) {
            mark(root.right, level + 1);
        }
        if (root.left != null) {
            mark(root.left, level + 1);
        }

        {
            if (result.length < level + 1) {
                result = Arrays.copyOf(result, level + 1);
            }
        }
        if (result[level] == null) {
            result[level] = root.val;
        }
    }
}
