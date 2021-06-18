package com.jape.LeetCode;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 *
 * 输入: 123
 * 输出: 321
 *  示例 2:
 *
 * 输入: -123
 * 输出: -321
 * 示例 3:
 *
 * 输入: 120
 * 输出: 21
 * 注意:
 *
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Algroithm07 {

    public static void main(String[] args) {

        Algroithm07 algroithm = new Algroithm07();

        int n1 = -12340;
        int n2 = 1534236469;

        System.err.println(algroithm.reverse(n2));
    }

    public int reverse(int x) {
        long result = 0;
        while (x != 0){
            result = result * 10 + x%10;
            if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
                return 0;
            }
            x /= 10;
        }
        return (int)result;
    }


    /**
     * 时间复杂度：O(\log(x))O(log(x))，xx 中大约有 \log_{10}(x)log
     * 10
     * ​
     *  (x) 位数字。
     * 空间复杂度：O(1)O(1)。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/reverse-integer/solution/zheng-shu-fan-zhuan-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }




}



























