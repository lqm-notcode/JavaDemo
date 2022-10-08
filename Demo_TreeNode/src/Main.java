public class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1,new TreeNode(2,new TreeNode(),new TreeNode()),new TreeNode(3,new TreeNode(),new TreeNode()));

        int depth = maxDepth(root);
        System.out.println(depth);
    }
    public static int maxDepth(TreeNode root){
        if(root==null){
            return 0;
        }else{
            int left = maxDepth(root.left);
            int right = maxDepth(root.right);
            return Math.max(left,right)+1;
        }
    }
}
