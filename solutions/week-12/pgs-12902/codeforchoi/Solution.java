class Solution {
    final int MOD = 1_000_000_007;

    public int solution(int n) {
        if (n % 2 == 1)
            return 0;

        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[2] = 3;

        for (int i = 4; i <= n; i += 2) {
            dp[i] = (4 * dp[i - 2] - dp[i - 4] + MOD) % MOD;
        }

        return (int) dp[n];
    }

}