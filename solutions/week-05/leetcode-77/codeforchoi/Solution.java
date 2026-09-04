import java.util.*;

class Solution {    
    private boolean[] visited;
    private List<List<Integer>> answer;

    public List<List<Integer>> combine(int n, int k) {
        answer = new ArrayList<>();

        visited = new boolean[n + 1];
        int[] arr = new int[k];

        for(int i = 1; i <= n; i++) {                       
            visited[i] = true;
            arr[0] = i;
            dfs(arr, n, k, 1);
            visited[i] = false;
        }
        return answer;
    }

    private void dfs(int[] arr, int n, int k, int depth) {
        if(depth == k) {
            List<Integer> list = new ArrayList<>();
            for(int num : arr) {
                list.add(num);
            }
            answer.add(list);
            return;
        } 

        for(int i = arr[depth - 1]; i <= n; i++) {
            if(!visited[i]) {
                visited[i] = true;
                arr[depth] = i;
                dfs(arr, n, k, depth + 1);
                visited[i] = false;                                
            }
        }
    }
}