import java.util.*;

class Solution {
    class Road {
		int v, value;

		public Road(int v, int value) {
			super();
			this.v = v;
			this.value = value;
		}		
	}
	
	public int solution(int N, int[][] road, int K) {
        List<Road>[] graph = new ArrayList[N + 1];
        
        for(int i = 1; i <= N; i++) {
        	graph[i] = new ArrayList<>();
        }
        
        // 양방향 도로 저장
        for(int[] info : road) {
        	graph[info[0]].add(new Road(info[1], info[2]));
        	graph[info[1]].add(new Road(info[0], info[2]));
        }
        
        // 최단거리 배열
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        dist[1] = 0;
        
        // 비용이 작은 순으로 우선 큐
        PriorityQueue<Road> pq = new PriorityQueue<>((o1, o2) -> o1.value - o2.value);
        pq.offer(new Road(1, 0));
        
        while(!pq.isEmpty()) {
        	Road cur = pq.poll();
        	
        	// 이미 더 짧은 경로가 발견된 경우
        	if(dist[cur.v] < cur.value) continue;
        	
        	for(Road next : graph[cur.v]) {
        		int newDist = dist[cur.v] + next.value;
        		
        		if(dist[next.v] > newDist) {
        			dist[next.v] = newDist;
        			pq.offer(new Road(next.v, newDist));
        		}
        	}
        			
        }
        
        int count = 0;
        // K 이하인 거리 마을 개수 찾기
        for(int i = 1; i <= N; i++) {
        	if(dist[i] <= K) count++;
        }
        
        return count;
    }
}