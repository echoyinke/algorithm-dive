package com.keyin.algorithms.backtrack;

import java.util.Arrays;

/**
 * 
 * 八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。
 * 该问题是国际西洋棋棋手马克斯·贝瑟尔于1848年提出：在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，
 * 即任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 * @author colter
 * 2018/3/25
 */
public class QueenSolution {
	//修改棋盘的大小，可模拟其他皇后类似问题
    //模拟一个8X8的棋盘，0代表没有放置，1代表放置了一个皇后
    private int[][] board = new int[4][4];

    //解法的数量
    private int total = 0;

    /**
     * 放置皇后的时候从第0行开始，依次放置一个
     * 如果放置成功，那么继续放置下一行。（0-7）
     * 当要放置k=8的时候说明已经全部放置完
     * 毕了，找到了一个对应的解
     *
     * @param row 放置第几个皇后, 因为皇后不能同row, 所以也可以记做row号，K从0开始
     */
    //放置第K个皇后
    public void putQueen(int row) {
        int max = board.length;
        //放置第8个，说明棋盘已经放置完毕了，输出结果。
        if (row >= max) {
            //找到一个解，打印出来。
            total++;
            System.out.println(String.format("=============%s===============", total));
            for (int i = 0; i < max; i++) {
                System.out.println(Arrays.toString(board[i]));
            }
            System.out.println("=============================");
        } else {
            /**
             * A：
             * 1. 从第0行，0列开始放置一个皇后
             * 2. 如果可以放置，那么就先将该位置置为1，然后放置下一行
             * 3. 如果全部顺利，那么直到找到一个解
             * 4. 然后0行1列放置一个皇后，找到一个解。以此类推
             *
             * B：
             * 1. 从第0行，0列开始放置一个皇后
             * 2. 如果可以放置，那么就先将该位置置为1，然后放置下一行
             * 3. 如果下一列没有可以放置的位置，那么将刚才放置的位置 置为0
             * （也就是皇后不能放在这里，此时会回溯到上一层循环，重新放置）
             * 例子：
             * row = 6 的时候，假如board[6][0]放置了一个皇后(row=6,i=0)，
             * 那么在接下来执行putQueen(6+1)
             * 假如遍历board[7]之后发现没有位置能够放置一个皇后，
             * 那么会执行board[row][i] = 0 (row=6,i=0)
             * 此时第6行第0列的皇后被拿走了，此时i自增为1
             * 然后执行check(row,i) （row=6,i=1）如果能放置的话，重复上边的动作，
             * 如果不能放置的话继续自增i，放置到第6行的下一列。
             */
            for (int colum = 0; colum < max; colum++) {
                if (check(row, colum)) {
                    board[row][colum] = 1;
                    putQueen(row + 1);
                    board[row][colum] = 0;
                }
            }
        }
    }

    /**
     * 皇后放置的时候是从上到下每一行放置的，所以不用检查改行以及之后的行
     * 所以只用检查列以及左上右上对角线
     *
     * @param row 检查的对应行
     * @param col 检查的对应列
     * @return 返回改点是否满足可以放置一个皇后
     */
    private boolean check(int row, int col) {
        //检查列是否有皇后
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }
        
        //检查左上对角线是否有皇后, 因为是从零点往右下出发，所以，判断过去的就可以，也就是左上， 左下还没放，所以不用查
        for (int m = row - 1, n = col - 1; m >= 0 && n >= 0; m--, n--) {
            if (board[m][n] == 1) {
                return false;
            }
        }

        //检查右上对角线是否有皇后，同理，只要看右上
        for (int m = row - 1, n = col + 1; m >= 0 && n < board[0].length; m--, n++) {
            if (board[m][n] == 1) {
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {
        QueenSolution solution = new QueenSolution();
        solution.putQueen(0);
    }
}
