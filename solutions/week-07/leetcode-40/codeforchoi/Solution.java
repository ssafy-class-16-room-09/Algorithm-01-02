import java.util.*;

class Solution {
    List<List<Integer>> answer;

	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		answer = new ArrayList<>();
		Arrays.sort(candidates);
		comb(candidates, target, 0, 0, new ArrayList<>());
		return answer;
	}

	private void comb(int[] candidates, int target, int start, int sum, List<Integer> list) {
		if (sum == target) {
			answer.add(new ArrayList<>(list));
			return;
		}		

		for (int i = start; i < candidates.length; i++) {
			// 같은 깊이에서 중복이 되면 제거
			if(i > start && candidates[i] == candidates[i - 1]) continue;
			
			// target보다 합이 커지면 더 볼 필요가 없음
			if(sum + candidates[i] > target) break;
			
			list.add(candidates[i]);
			comb(candidates, target, i + 1, sum + candidates[i], list);
			list.remove(list.size() - 1);			
		}
	}
}