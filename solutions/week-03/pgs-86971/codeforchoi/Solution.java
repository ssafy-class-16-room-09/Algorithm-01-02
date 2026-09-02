import java.util.*;

class Solution {
    private static List<Integer>[] graph;
    private static boolean[] visited;
    
    public int solution(int n, int[][] wires) {
        graph = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int[] wire : wires) {
            graph[wire[0]].add(wire[1]);
            graph[wire[1]].add(wire[0]);
        }

        int minDiff = Integer.MAX_VALUE;

        for(int[] wire : wires) {
            visited = new boolean[n + 1];

            // 간선을 짜름
            graph[wire[0]].remove(Integer.valueOf(wire[1])); // 원소 제거
            graph[wire[1]].remove(Integer.valueOf(wire[0]));

            int count = dfs(1);
            int diff = Math.abs(n - 2 * count);
            minDiff = Math.min(minDiff, diff);

            graph[wire[0]].add(wire[1]);
            graph[wire[1]].add(wire[0]);
        }
        return minDiff;
    }
    
    private int dfs(int cur) {
        visited[cur] = true;

        int count = 1;
        for(int next : graph[cur]) {
            if(!visited[next]) {
                count += dfs(next);
            }
        }
        return count;
    }
}