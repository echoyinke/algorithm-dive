package com.keyin.algorithms.dynamic_programming;

public class ClimStair {
    public int climbStairs(int n) {
        return climb_Stairs(0, n);
    }
    public int climb_Stairs(int i, int n) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        int ifOneStep = climb_Stairs(i+1, n);
        int ifTwoStep = climb_Stairs(i+2,n);
        return ifOneStep+ifTwoStep;
    }

    
    /*
    * 递归操作，在第n步，要么是通过两步上来，要么是通过一步上来
    * 
    * */
    public static  int recurClimb(int n) {
        if (n==1) {
            return 1;
        }
        if (n==2) {
            return 2;
        }
        return recurClimb(n-1) + recurClimb(n-2);
    }
    
    /*
    * 
    * 跟递归公式是一样的，只不过是把每个状态都记录下来
    * */
    public static int dp(int n) {
        int[] dp = new int[n+1];
        dp[0]=0;
        dp[1]=1;
        dp[2]=2;

        for (int i =3;i<n;i++) {
            dp[i]=dp[i-1] +dp[i-2];
        }
        return dp[n-1];
    }



    public static void main(String[] args) {

        ClimStair climStair = new ClimStair();
        int res2 = ClimStair.recurClimb(5);
        int res = climStair.climbStairs(5);
        int res3 = ClimStair.dp(5);
        int i=1;

    }



}
