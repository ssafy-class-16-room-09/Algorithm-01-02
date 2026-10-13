import java.util.*;

public class Solution {
    private static final int[] dr = {0, 1}; // 우 하
	private static final int[] dc = {1, 0};
	
	private static int[] parent;
	
	private static class Edge {
		int u, v, cost;

		public Edge(int u, int v, int cost) {
			super();
			this.u = u;
			this.v = v;
			this.cost = cost;
		}	
	}
	
	public int solution(int[][] land, int height) {
		
		int n = land.length;
		parent = new int[n * n];
		for(int i = 0; i < n * n; i++) {
			parent[i] = i;
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
		
		// 미리 연결 가능한 것들은 연결한다.
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				int cur = land[i][j];
				
				for(int d = 0; d < 2; d++) {
					int nr = i + dr[d];
					int nc = j + dc[d];
					
                    if(nr < 0 || nc < 0 || nr >= n || nc >= n) continue;
                    
					if(find(i * n + j) == find(nr * n + nc)) continue;
					
					int cost = Math.abs(cur - land[nr][nc]);
					if(cost <= height) {
						union(i * n + j, nr * n + nc);
					} else {
						pq.offer(new Edge(i * n + j, nr * n + nc, cost));
					}
				}
			}
		}
		
		for(int i = 0; i < n * n; i++) {
			if(parent[i] == i) count++;
		}
		
		// 모두 연결되서 사다리를 설치할 필요가 없는 경우
		if(count == 1) return 0;
		
		int minCost = 0;
		int cnt = 0;
		
		// MST 크루스칼
		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			
			if(union(edge.u, edge.v)) {
				minCost += edge.cost;
				cnt++;
				
				if(cnt == count - 1) break;
			}			
		}	
        return minCost;
    }
	
	private static int find(int n) {
		if(parent[n] == n) return n;
		return parent[n] = find(parent[n]);
	}
	
	private static boolean union(int u, int v) {
		u = find(u);
		v = find(v);
		if(u == v) return false;
		parent[v] = u;
		return true; 
	}
}