package com.cxr.algorithm.tree.simple;

import javax.swing.tree.TreeNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * 107. 二叉树的层次遍历 II
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其自底向上的层次遍历为：
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
 */
public class LevelOrderBottom {



    public List<List<Integer>> levelOrderBottom(TreeNode root) throws ParseException {
       //返回结果
       List<List<Integer>> resultList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.parse("");
       return  resultList;
    }
}
