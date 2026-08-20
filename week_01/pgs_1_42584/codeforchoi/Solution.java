import java.util.*;

class Solution {
  public int[] solution(int[] prices) {
    Deque<Integer> stack = new ArrayDeque<>();
    int n = prices.length;
    int[] result = new int[prices.length];

    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && prices[stack.peek()] > prices[i]) {
        int index = stack.pop();
        result[index] = i - index;
      }
      stack.push(i);
    }

    while (!stack.isEmpty()) {
      int index = stack.pop();
      result[index] = n - index - 1;
    }

    return result;
  }
}