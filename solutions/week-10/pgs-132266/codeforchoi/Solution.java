import java.util.*;

class Solution {
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
		
		List<Integer>[] graph = new ArrayList[n + 1];
		for(int i = 1; i <= n; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for(int[] road : roads) {
			graph[road[0]].add(road[1]);
			graph[road[1]].add(road[0]);
		}
		
		int[] dist = new int[n + 1];
		Arrays.fill(dist, -1);
		
		Queue<Integer> q = new ArrayDeque<>();
		
		// 목적지에서 각 부대원까지의 최단거리를 구한다.
		q.offer(destination);
		dist[destination] = 0;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			for(int next : graph[cur]) {
				if(dist[next] != -1) continue;
				
				dist[next] = dist[cur] + 1;
				q.offer(next);
			}
		}
		
		int[] answer = new int[sources.length];
		
        for (int i = 0; i < sources.length; i++) {
            answer[i] = dist[sources[i]];
        }
        return answer;
    }
}