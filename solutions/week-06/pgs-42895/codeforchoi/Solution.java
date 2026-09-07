import java.util.*;

class Solution {
    public int solution(int N, int number) {
        // 계산결과가 중복되는 수 제거하기 위한 HashSet 배열
		Set<Integer>[] dp = new HashSet[9];
		
		for(int i = 1; i <= 8; i++) {
			dp[i] = new HashSet<>();
		}
		
		int concat = 0;
		for(int i = 1; i <= 8; i++) {
			// N, NN, NNN, ... 넣기
			concat = concat * 10 + N;
			dp[i].add(concat);
			
			// dp[j]와 dp[i - j] 조합해서 만들 수 있는 수들 넣기
			for(int j = 1; j < i; j++) {
				for(int a : dp[j]) {
					for(int b : dp[i - j]) {
						dp[i].add(a + b);
						dp[i].add(a - b);
						dp[i].add(a * b);
						
						if(b != 0) {
							dp[i].add(a / b);
						}
					}
				}
			}
			if(dp[i].contains(number)) {
				return i;
			}
		}
		return -1;
    }
}