package com.keyin.algorithms.dynamic_programming;

public class CoinChange {



    /**
     * https://cspiration.com/leetcodeClassification#10311
     *
     *
     * **/
    public static  int coinChange(int[] coins, int amount) {
        int dp[] = new int[amount + 1];
        dp[0] = 1;

        for (int j = 0; j < coins.length; j++) {
            for (int i = coins[j]; i <= amount; i++) {
                int prev = dp[i - coins[j]];
                if (prev > 0) {
                    if (dp[i] == 0) dp[i] = prev + 1;
                    else dp[i] = Math.min(dp[i], prev + 1);
                }
            }
        }

        return dp[amount] - 1;
    }

    //  {1,2,5} => f(n)= f(n-1)+f(n-2) +f(n-5)
    public static int coinChange2(int amount) {
        int[] dp = new int[amount+1];
        dp[0]=0;
        dp[1]=1;
        dp[2]=2;
        dp[3]=2;
        dp[4]=2;

        for (int i=5;i<=amount;i++) {
            dp[i]=Math.min(Math.min(dp[i-1]+1, dp[i-2]+1), dp[i-5]+1);
        }
        dp.clone();
        return dp[amount];


    }


    public static void main(String[] args) {
        int res = CoinChange.coinChange(new int[]{1,2,5}, 11);
        int res2 = CoinChange.coinChange2( 11);

        int i=1;
    }

}
