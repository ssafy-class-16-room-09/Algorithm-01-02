import java.util.*;

class Solution {
    List<List<Integer>> answer;
	
	public List<List<Integer>> combinationSum3(int k, int n) {
		answer = new ArrayList<>();
		comb(0, 1, 0, k, n, new int[k]);
		return answer;
	}
	
	private void comb(int depth, int start, int sum, int k, int target, int[] arr) {
		if(depth >= k) {
			if(sum == target) {
				List<Integer> list = new ArrayList<>();
				for(int num : arr) list.add(num);
				answer.add(new ArrayList<>(list));
			}
            return;
		}
		
		for(int i = start; i <= 9; i++) {
			if(sum + i > target) break;
			arr[depth] = i;
			comb(depth + 1, i + 1, sum + i, k, target, arr);
		}
	}
}