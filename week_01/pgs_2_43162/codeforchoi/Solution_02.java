import java.util.*;

class Solution {
  private static int[] parent;

  public static int solution(int n, int[][] computers) {
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && computers[i][j] == 1) {
          graph[i].add(j);
        }
      }
    }

    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }

    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int v : graph[i]) {
        if (count == n - 1)
          break;
        if (find(i) != find(v)) {
          union(i, v);
          count++;
        }
      }
    }

    return n - count;
  }

  private static int find(int n) {
    if (parent[n] == n)
      return n;
    return parent[n] = find(parent[n]);
  }

  private static void union(int u, int v) {
    u = find(u);
    v = find(v);
    if (u != v)
      parent[u] = v;
  }
}