import java.util.*;

class Solution {
    private static List<List<Integer>> answer;
	private static boolean[] visited;
    
    public List<List<Integer>> permute(int[] nums) {
        answer = new ArrayList<>();
		
		int n = nums.length;
		visited = new boolean[n + 1];
		int[] arr = new int[n];
		dfs(nums, arr, n, 0);  
        return answer;      
    }

    private static void dfs(int[] nums, int[] arr, int n, int depth) {
		if(depth == n) {
			List<Integer> list = new ArrayList<>();
			for(int num : arr) {
				list.add(num);
			}
			answer.add(list);
			return;
		}
		
		for(int i = 0; i < n; i++) {
			if(!visited[i]) {
				visited[i] = true;
				arr[depth] = nums[i];
				dfs(nums, arr, n, depth + 1);
				visited[i] = false;				
			}
		}
	}
}