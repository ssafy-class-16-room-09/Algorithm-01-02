import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        // 끝 좌표로 정렬
		Arrays.sort(targets, (o1, o2) -> Integer.compare(o1[1], o2[1]));
		
		int count = 0;
		int end = -1;
		
		for(int[] target : targets) {
			int s = target[0];
			int e = target[1];
			
			// 끝 좌표 안으로 요격이 안되면 하나 더 쏨
			if(s >= end) {
				count++;
				end = e;
			}						
		}
       
        return count;
    }
}