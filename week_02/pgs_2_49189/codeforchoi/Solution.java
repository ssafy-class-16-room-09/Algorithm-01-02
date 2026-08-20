import java.util.*;

class Solution {
  public int solution(int n, int[][] edge) {
    List<Integer>[] graph = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int[] e : edge) {
      graph[e[0]].add(e[1]);
      graph[e[1]].add(e[0]);
    }

    boolean[] visited = new boolean[n + 1];
    int[] dist = new int[n + 1];

    Queue<Integer> q = new ArrayDeque<>();
    q.offer(1);
    visited[1] = true;
    int maxDist = 0;

    while (!q.isEmpty()) {
      int cur = q.poll();

      for (int v : graph[cur]) {
        if (visited[v]) {
          continue;
        } else {
          visited[v] = true;
          q.offer(v);
          dist[v] = dist[cur] + 1;
          maxDist = Math.max(maxDist, dist[v]);
        }
      }
    }

    int count = 0;
    for (int i = 1; i <= n; i++) {
      if (dist[i] == maxDist)
        count++;
    }
    return count;
  }
}