class Solution {
    public int solution(int n) {
        final int P = 1_000_000_007;
        
        if(n % 2 == 1) return 0;
    
        long[] dp = new long[n + 2];
        dp[0] = 1;
        dp[2] = 3;
        
        for(int i = 4; i <= n; i += 2) {
            dp[i] = ((dp[i - 2] * 3) % P + (dp[i - 2] - dp[i - 4]) % P) % P;
        }
        
        // 9 + 2 = 11
        // 33 + 8 = 41
        
        return (int)dp[n];
    }
}