package com.jape.LeetCode;

/**
 * 动态规划-1025题-除数博弈-简单
 * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
 * 最初，黑板上有一个数字N。在每个玩家的回合，玩家需要执行以下操作：
 * 选出任一x，满足0 < x < N 且N % x == 0。(其约数)
 * 用 N - x替换黑板上的数字 N 。
 * 如果玩家无法执行这些操作，就会输掉游戏。
 * 只有在爱丽丝在游戏中取得胜利时才返回True，否则返回 False。假设两个玩家都以最佳状态参与游戏。
 * <p>
 * 示例 1：
 * 输入：2
 * 输出：true
 * 解释：爱丽丝选择 1，鲍勃无法进行操作。
 * <p>
 * 示例 2：
 * 输入：3
 * 输出：false
 * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
 * <p>
 * 提示：
 * 1 <= N <= 1000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/divisor-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jape
 * @since 2020/12/9 15:29
 */
public class DP1025 {
    public static void main(String[] args) {
        divisorGameByDP(1000);

    }

    //归纳
    public static boolean divisorGame(int n) {
        /*
         * 归纳分析N(转到状态以轮到爱丽丝选为准)
         * 1 无 false
         * 2 1 true
         * 3 1->2 1->1 转到1
         * 4 1->3 1->2 转到2 ；2->2 1->1 转到1
         * 5 1->4 (1->3 转到3 ； 2->2 转到2)又由于玩家状态最佳，bob想赢必选1，所以转到3
         *
         * ...得出奇数必输，偶数必赢
         * 谁拿到偶数就可以赢，奇数的约束必为1或奇数，相减必为偶数，对方必赢
         * */
        return n % 2 == 0;
    }

    //动态规划,如果当前这个数可以被1-n中的数i整除，并且在n-i位置是输（对方输），则能赢
    public static boolean divisorGameByDP(int n) {
        boolean[] win = new boolean[n + 1];
        win[1] = false;
        win[2] = true;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (i % j == 0 && win[i - j] == false) {
                    win[i] = true;
                    break;
                }
            }
        }
        return win[n];
    }

}
