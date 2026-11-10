import java.util.*;

class Solution {
    
    private int N, K, maxCount;
    private List<Info> graph[];
    private boolean[] infected;

    private class Info {
        int v, type;
        
        public Info(int v, int type) {
            this.v = v;
            this.type = type;
        }
    }
    
    public int solution(int n, int infection, int[][] edges, int k) {
        this.N = n;
        this.K = k;
        
        graph = new ArrayList[n + 1];
        
        for(int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int type = edge[2];
            
            graph[a].add(new Info(b, type));
            graph[b].add(new Info(a, type));
        }
        
        maxCount = 0;
        infected = new boolean[n + 1];
        infected[infection] = true;
        
        dfs(infected, 0);
        
        return maxCount;
    }
    
    private void dfs(boolean[] infected, int depth) {
        if(depth == K) {
            int count = 0;
            for(boolean flag : infected) {
                count += flag ? 1 : 0;
            }
            maxCount = Math.max(maxCount, count);
            return;
        }
        
        for(int i = 1; i <= 3; i++) {
            boolean[] next = bfs(infected, i);
            dfs(next, depth + 1);
        }
    }
    
    private boolean[] bfs(boolean[] infected, int type) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        boolean[] next = infected.clone();
        
        for(int i = 1; i <= N; i++) {
            if(infected[i]) {
                visited[i] = true;
                q.offer(i);
            }    
        }
        
        while(!q.isEmpty()) {
            int cur = q.poll();
            
            for(Info edge : graph[cur]) {
                if(!visited[edge.v] && edge.type == type) {
                    visited[edge.v] = true;
                    next[edge.v] = true;
                    q.offer(edge.v);
                }
            }
        }
        
        return next;
    }
}