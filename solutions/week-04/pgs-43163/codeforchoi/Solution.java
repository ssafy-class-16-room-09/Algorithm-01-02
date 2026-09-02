import java.util.*;

class Solution {
    private class WordState {
		String word;
		int count;

		public WordState(String word, int count) {
			super();
			this.word = word;
			this.count = count;
		}

	}
    
    public int solution(String begin, String target, String[] words) {
        // words에 target이 없는 경우 바로 0 반환
		boolean inWords = false;
		for (String word : words) {
			if (word.equals(target)) {
				inWords = true;
			}
		}
		if (!inWords) return 0;

		// words에 target이 있는 경우 변환 시도
		Queue<WordState> q = new ArrayDeque<>();
		boolean[] visited = new boolean[words.length];
		
		q.offer(new WordState(begin, 0));
		while (!q.isEmpty()) {
			WordState cur = q.poll();
			
			if(cur.word.equals(target)) {
				return cur.count;
			}
			
			for(int i = 0; i < words.length; i++) {
				if(!visited[i] && canChange(cur.word, words[i])) {
					visited[i] = true;
					q.offer(new WordState(words[i], cur.count + 1));
				}
			}
		}
		
		return 0;
    }
    
    private boolean canChange(String cur, String next) {
		int count = 0;
		
		for(int i = 0; i < cur.length(); i++) {
			if(cur.charAt(i) != next.charAt(i)) {
				count++;
			}
		}
		
		if(count == 1) {
			return true;
		}
	
		return false;
	}
}