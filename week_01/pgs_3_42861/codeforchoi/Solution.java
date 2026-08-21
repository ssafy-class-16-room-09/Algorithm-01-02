import java.util.*;

class Solution {
  private int[] parent;

  public class Edge {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
      super();
      this.u = u;
      this.v = v;
      this.cost = cost;
    }
  }

  public int solution(int n, int[][] costs) {
    int len = costs.length;
    Edge[] edges = new Edge[len];

    for (int i = 0; i < len; i++) {
      int u = costs[i][0];
      int v = costs[i][1];
      int cost = costs[i][2];
      edges[i] = new Edge(u, v, cost);
    }

    // 비용으로 간선을 오름차순으로 정렬
    Arrays.sort(edges, (o1, o2) -> Integer.compare(o1.cost, o2.cost));

    // 대표 노드 초기화
    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }

    int count = 0;
    int minCost = 0;
    for (Edge edge : edges) {
      if (count == n - 1)
        break; // MST의 간선의 개수는 (정점의 개수 - 1)
      // 대표노드가 같지 않으면 통합
      if (find(edge.u) != find(edge.v)) {
        union(edge.u, edge.v);
        minCost += edge.cost;
        count++;
      }
    }
    return minCost;
  }

  // 대표 노드 조회
  private int find(int n) {
    if (parent[n] == n)
      return n;
    return parent[n] = find(parent[n]);
  }

  // 대표 노드 갱신 (parent[u]를 변경)
  private void union(int u, int v) {
    u = find(u);
    v = find(v);
    if (u != v)
      parent[u] = v;
  }
}