package com.cxr.other;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }
    //dfs是深度优先
    //这个方法的作用就是传入一个数组 和数组的两个min max 构造出一个数
    private TreeNode dfs(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        // 以升序数组的中间元素作为根节点 root。
        int mid = lo + (hi - lo) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        // 递归的构建 root 的左子树与右子树。
        root.left = dfs(nums, lo, mid - 1);
        root.right = dfs(nums, mid + 1, hi);
        return root;
    }


    public static void main(String[] args) {
//        class Solution {
//            public List<List<Integer>> list=new ArrayList<>();
//            public List<List<Integer>> levelOrderBottom(TreeNode root) {
//                bfs(root,0);
//                Collections.reverse(list);
//                return list;
//            }
//            public void bfs(TreeNode root,int step){
//                if(root==null) return;
//                if(list.size()<=step) list.add(new ArrayList<>());
//                bfs(root.left,step+1);
//                bfs(root.right,step+1);
//                list.get(step).add(root.val);
//            }
//        }
    }
}
