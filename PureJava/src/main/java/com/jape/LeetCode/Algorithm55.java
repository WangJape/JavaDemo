package com.jape.LeetCode;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个位置。
 *
 * 示例 1:
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 *
 * 示例 2:
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Algorithm55 {

    public static void main(String[] args) {
        Algorithm55 algorithm = new Algorithm55();

        int[] nums1 = {2,3,1,1,4};
        int[] nums2 = {3,2,1,0,4};
        int[] nums3 = new int[25002];
        for(int i = 0; i<25000;i++){
            nums3[i] = 25000-i;
        }
        nums3[24999] = 1;
        nums3[25000] = 0;
        nums3[25001] = 0;

        //long startTime=System.currentTimeMillis();//获取开始时间
        long startTime=System.nanoTime();   //获取开始时间

        //System.err.println(algorithm.canJump(nums1));
        System.err.println(algorithm.canJump(nums3));

        //long endTime=System.currentTimeMillis(); //获取结束时间
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间：["+(endTime-startTime)+"]ns");
    }

    /**
     * 贪心
     * @param nums
     * @return
     */
    /*public boolean canJump(int[] nums) {
        int total = nums.length;//全部
        int transferStation = total-1;//中转站，初始为终点
        for(int i = total-1; i>=0; i--){//从终点倒叙找中转站
            if(i + nums[i] >= transferStation){//如果能到达中转站，则必能到达终点
                transferStation = i;//确定新的中转站
            }
        }
        return transferStation==0;//如果起点也为中转站，则可以到达

    }*/

    enum Index {
        GOOD, BAD, UNKNOWN
    }

    /**
     * 自底向上的动态规划
     * 自底向上和自顶向下动态规划的区别就是消除了回溯，在实际使用中，自底向下的方法有更好的时间效率因为我们不再需要栈空间，可以节省很多缓存开销。更重要的事，这可以让之后更有优化的空间。回溯通常是通过反转动态规划的步骤来实现的。
     *
     * 这是由于我们每次只会向右跳动，意味着如果我们从右边开始动态规划，每次查询右边节点的信息，都是已经计算过了的，不再需要额外的递归开销，因为我们每次在 memo 表中都可以找到结果。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/jump-game/solution/tiao-yue-you-xi-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        Index[] memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (memo[j] == Index.GOOD) {
                    memo[i] = Index.GOOD;
                    break;
                }
            }
        }
        return memo[0] == Index.GOOD;
    }


    /**
     * 失败！每次跳最大的，如果不成立，就回溯，
     * @param nums
     * @return
     */
    /*public boolean canJump(int[] nums) {

        int[] stack = new int[nums.length];//回溯堆栈
        int stackIndex = -1;//回溯堆栈 栈顶指针

        int hopNumber = 0;

        int i = 0;
        while (i < nums.length-1) {
            System.err.print("当前索引" + i);

            hopNumber = nums[i];

            stackIndex++;
            System.err.print("[堆栈" + stackIndex + "进栈" + hopNumber + "]");
            stack[stackIndex] = hopNumber;//回溯堆栈暂存

            while(hopNumber == 0){
                stackIndex--;
                if(stackIndex == -1){
                    System.err.println("\n");
                    return false;
                }
                i -= stack[stackIndex];//回溯
                hopNumber = --stack[stackIndex];//可跳减1
                System.err.print("  可跳越数为0，回溯到上一步,当前索引" + i);

            }

            System.err.print("  可跳越数" + hopNumber);
            i += hopNumber;
            System.err.println("  跳跃到索引" + i);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return true;
    }*/
}
