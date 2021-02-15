import com.cxr.algorithm.tree.TreeNode;
import com.cxr.other.ListNode;
import com.cxr.other.demo.entriy.User;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int len = 1;
        int max = 1;
        int lmax = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (i != lmax && nums[i] < nums[i + 1]) {
                len++;
                max = Math.max(max, len);
            } else {
                len = 1;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println("我我我我");
    }

}
