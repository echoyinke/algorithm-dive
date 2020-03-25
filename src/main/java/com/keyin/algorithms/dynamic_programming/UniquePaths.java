package com.keyin.algorithms.dynamic_programming;

public class UniquePaths {

    public static int uniquePath(int m, int n) {

        //二维问题，分配 dp
        int[][] dp = new int[m][n];
        // 初始化
        dp[0][0]=0;

        for (int k=1;k<m;k++){
            dp[k][0] = 1;
        }
        for (int l=1;l<n;l++){
            dp[0][l] = 1;
        }
        // 规划方程
        for(int i=1;i<m;i++) {
            for (int j=1;j<n;j++){
                int left = dp[i-1][j];
                int top = dp[i][j-1];
                dp[i][j]=left + top;
            }
        }
        // 索引-1
        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        int res = UniquePaths.uniquePath(7,3);
        int i =1;
    }


}
