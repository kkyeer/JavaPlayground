package leetcode;

/**
 * @Author: kkyeer
 * @Description: 二叉树的直径 543题
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * <p>
 * 示例 :
 * 给定二叉树
 * <p>
 *     1
 *    / \
 *   2   3
 *  / \
 * 4   5
 * <p>
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * <p>
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 22:04 3-10
 * @Modified By:
 */
class DiameterOfBinaryTree_543 {
    public static void main(String[] args) {
        Integer[] mid = new Integer[]{4, -7, -3, null, null, -9, -3, 9, -7, -4, null, 6, null, -6, -6, null, null, 0, 6, 5, null, 9, null, null, -1, -4, null, null, null, -2};
        TreeNode root = new TreeNode(1);
        TreeNode l2 = new TreeNode(2);
        TreeNode l3 = new TreeNode(3);
        root.left = l2;
        root.right = l3;
        TreeNode l4 = new TreeNode(4);
        TreeNode l5 = new TreeNode(5);
        l2.left = l4;
        l2.right = l5;
        System.out.println(diameterOfBinaryTree(root));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    // 记录当前的最大直径
    static int result = 0;

    static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        depth(root);
        return result;
    }

    static int depth(TreeNode node){
//        直径=左子树深度+右子树深度
        if (node == null) {
            return 0;
        }else {
            int leftDepth = node.left == null ? 0 : depth(node.left)+1;
            int rightDepth = node.right == null ? 0 : depth(node.right)+1;
            result = Math.max(result, leftDepth + rightDepth );
            return Math.max(leftDepth , rightDepth );
        }
    }
}
