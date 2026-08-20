import java.util.*;

class Solution {
  public int solution(int n, int[][] computers) {
    boolean[] visited = new boolean[n];
    int answer = 0;

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfs(i, computers, visited);
        answer++;
      }
    }
    return answer;
  }

  private void dfs(int cur, int[][] computers, boolean[] vistied) {
    vistied[cur] = true;

    for (int i = 0; i < computers.length; i++) {
      if (computers[cur][i] == 1 && !vistied[i]) {
        dfs(i, computers, vistied);
      }
    }
  }
}