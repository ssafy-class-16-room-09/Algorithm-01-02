import java.util.*;

class Solution_03 {
    private static boolean[] visited;
	private static int n;
	private static String[] result;
    
    public String[] solution(String[][] tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
		
		for(String[] ticket : tickets) {
			// key값이 없으면 k (key)에 value를 PriorityQueue를 생성해서 넣어주고 
			// value를 반환하므로 offer 해줄 수 있다.
			graph.computeIfAbsent(ticket[0], k -> new PriorityQueue<>())
				.offer(ticket[1]);
		}
		
		// 앞에 삽일할 것이므로 LinkedList 선택
		LinkedList<String> route = new LinkedList<>();
		dfs("ICN", graph, route);
		return route.toArray(new String[0]);
    }
    
    private void dfs(String start, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {		
		PriorityQueue<String> pq = graph.get(start);
		
		// pq가 null인지 먼저 확인해서 NullPointException 예방
		while(pq != null && !pq.isEmpty()) {
			String next = pq.poll();
			dfs(next, graph, route);
		}
		route.addFirst(start);
	}
}