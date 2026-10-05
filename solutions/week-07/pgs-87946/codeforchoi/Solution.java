
public class Solution {

    private int N;
    private int maxCount;
    private boolean[] visited;

    public int solution(int k, int[][] dungeons) {

        maxCount = 0;
        N = dungeons.length;
        visited = new boolean[N];

        dfs(dungeons, 0, k);
        return maxCount;
    }

    private void dfs(int[][] dungeons, int count, int fatigue) {
        maxCount = Math.max(count, maxCount);
        if (count == N) {
            return;
        }

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                if (fatigue >= dungeons[i][0]) {
                    visited[i] = true;
                    dfs(dungeons, count + 1, fatigue - dungeons[i][1]);
                    visited[i] = false;
                }
            }
        }
    }
}
