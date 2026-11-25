import java.util.*;

class Solution {
    private int N, M;    
    
    private int[] dr = {-1, 1, 0, 0}; // 상하좌우
    private int[] dc = {0, 0, -1, 1};
        
    public int solution(String[] board) {
        N = board.length;
        M = board[0].length();
        
        int[][] dist = new int[N][M];
        for(int[] row : dist) {
            Arrays.fill(row, -1); // 미방문 표시: -1
        }
        
        Queue<int[]> q = new ArrayDeque<>();
        
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(board[i].charAt(j) == 'R') {
                    q.offer(new int[] {i, j});
                    dist[i][j] = 0;
                    break;
                }
            }
        }
        
        while(!q.isEmpty()) {
            int[] cur = q.poll();            
            
            for(int d = 0; d < 4; d++) {
                int nr = cur[0];
                int nc = cur[1];
                int count = dist[nr][nc];
                
                if(board[nr].charAt(nc) == 'G') {
                    return count;
                }
                
                while(true) {
                    int pr = nr + dr[d];
                    int pc = nc + dc[d];
                    
                    if(pr < 0 || pc < 0 || pr >= N || pc >= M || board[pr].charAt(pc) == 'D') break;
                    
                    nr = pr;
                    nc = pc;
                }
                
                if(dist[nr][nc] != -1) continue;
                
                dist[nr][nc] = count + 1;
                q.offer(new int[] {nr, nc});
            }
        } 
        
        return -1;
    }
}