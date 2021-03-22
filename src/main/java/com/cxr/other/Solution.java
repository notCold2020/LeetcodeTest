import java.util.Arrays;

/**
 * 输入：s = "babad"
 * 输出："bab"
 */
class Solution {
    public static void main(String[] args) {
        String s = longestPalindrome("12");
        System.out.println(s);
    }

    public static String longestPalindrome(String s) {
        StringBuilder realDeviceName = new StringBuilder(s);
        realDeviceName.append("ddd");
        s = realDeviceName.toString();
        return s;
    }
}
