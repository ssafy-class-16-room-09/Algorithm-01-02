import java.util.*;

class Solution_02 {
	private static boolean[] visited;
	private static int n;
	private static String[] result;

	public String[] solution(String[][] tickets) {
		Arrays.sort(tickets, (o1, o2) -> o1[1].compareTo(o2[1]));

		n = tickets.length;

		visited = new boolean[n];
		result = new String[n + 1];
		result[0] = "ICN";

		dfs(tickets, "ICN", 0);
		return result;
	}

	private boolean dfs(String[][] tickets, String start, int depth) {
		if (depth == n) {
			return true;
		}

		for (int i = 0; i < n; i++) {
			if (!visited[i] && tickets[i][0].equals(start)) {
				visited[i] = true;
				result[depth + 1] = tickets[i][1];

				if (dfs(tickets, tickets[i][1], depth + 1)) {
					return true;
				}
				visited[i] = false; // 탐색이 끝나면 복구
			}
		}
		return false;
	}
}