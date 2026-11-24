import java.util.*;

class Solution {
    public int solution(int[] order) {
        Deque<Integer> stack = new ArrayDeque<>();
        
        int count = 0;
        
        for(int i = 1; i <= order.length; i++) {
            stack.push(i);
            
            while(!stack.isEmpty() && stack.peek() == order[count]) {
                stack.pop();
                count++;
            }
        }
        
        return count;       
    }
}