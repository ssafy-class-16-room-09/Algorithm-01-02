import java.util.*;

public class Solution {
	
	private static final int WALL = -1;
	private static final int ROAD = 0;
	private static final int LEVER = 1;
	private static final int EXIT = 2;			
	
	private static final int[] dx = {-1, 1, 0, 0}; // 상하좌우
	private static final int[] dy = {0, 0, -1, 1};
	
	private int N, M;
	private int[][] map;
	private boolean[][] visited;
	
	private Queue<Point> q;
	
	private class Point {
		int x, y, time;

		public Point(int x, int y, int time) {
			super();
			this.x = x;
			this.y = y;
			this.time = time;
		}		
	}
	
	public int solution(String[] maps) {
		N = maps.length;
		M = maps[0].length();
		
		map = new int[N][M];
		
		Point start = new Point(0, 0, 0);
		Point lever = new Point(0, 0, 0);
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				char ch = maps[i].charAt(j);
				
				switch(ch) {
				case 'S':
					start.x = i;
					start.y = j;
					map[i][j] = ROAD;
					break;
				case 'E':
					map[i][j] = EXIT;
					break;
				case 'L':
					lever.x = i;
					lever.y = j;
					map[i][j] = LEVER;
					break;
				case 'O':
					map[i][j] = ROAD;
					break;
				case 'X':
					map[i][j] = WALL;					
					break;
				}				
			}
		}
		
		int minLeverTime = bfs(start ,LEVER); // 시작점에서 레버까지의 최소거리
        if(minLeverTime == -1) {
            return -1;
        }
		int minExitTime = bfs(lever ,EXIT); // 레버에서부터 탈출지점까지의 최소거리
		
        return (minExitTime == -1) ? -1 : minLeverTime + minExitTime;
    }
	
	private int bfs(Point start, int target) {	
		
		q = new ArrayDeque<>();
		visited = new boolean[N][M];
					
		q.offer(start);
        visited[start.x][start.y] = true;
		
		while(!q.isEmpty()) {
			Point cur = q.poll();
			
			for(int d = 0; d < 4; d++) {
				int time = cur.time;
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];
				
				// 맵을 넘어가거나 벽이면 불가능하다.
				if(isRangeAndWall(nx, ny)) continue;
				
				if(map[nx][ny] == target) {
					time++;
					return time;
				}
				
				if(!visited[nx][ny]) {					
					visited[nx][ny] = true;
					q.offer(new Point(nx, ny, time + 1));	
				}			
			}
		}
		return -1;
	}
	
	private boolean isRangeAndWall(int x, int y) {
		return x < 0 || y < 0 || x >= N || y >= M || map[x][y] == WALL;
	}
}
