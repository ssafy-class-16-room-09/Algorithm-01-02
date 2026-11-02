class Solution {    
    public int solution(int [][]board) {
        int N = board.length;
        int M = board[0].length;
        
        int maxlen = 0;
        int[][] dp = new int[N + 1][M + 1];
        
        for(int r = N - 1; r >= 0; r--) {
            for(int c = M - 1; c >= 0; c--) {
                if(board[r][c] == 1) {
                    dp[r][c] = Math.min(dp[r + 1][c], Math.min(dp[r][c + 1], dp[r + 1][c + 1])) + 1;
                }                
                maxlen = Math.max(maxlen, dp[r][c]);
            }
        }
        
        return maxlen * maxlen;
    }
}