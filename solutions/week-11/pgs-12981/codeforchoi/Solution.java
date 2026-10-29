import java.util.*;

class Solution {
  public int[] solution(int n, String[] words) {
    Set<String> set = new HashSet<>();

    for (int i = 0; i < words.length; i++) {
      String word = words[i];

      // 이전에 사용된 적이 있다면 탈락
      if (set.contains(word)) {
        return new int[] { i % n + 1, i / n + 1 };
      }

      if (i > 0) {
        String prev = words[i - 1];

        // 이전 단어의 끝글자로 시작하지 않으면 탈락
        if (prev.charAt(prev.length() - 1) != word.charAt(0)) {
          return new int[] { i % n + 1, i / n + 1 };
        }
      }

      set.add(word);
    }

    return new int[] { 0, 0 };
  }
}