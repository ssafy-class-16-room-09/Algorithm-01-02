import java.util.*;

class Solution {
    public String solution(String number, int k) {
        
        Deque<Integer> dq = new ArrayDeque<>();
        
        int count = 0;
        for(char ch : number.toCharArray()) {
            int num = ch - '0';           
            
            while(!dq.isEmpty() && count < k && dq.peekLast() < num) {
                dq.pollLast();
                count++;
            }
            
            dq.offer(num);
        }
        
        StringBuilder sb = new StringBuilder();
        while(!dq.isEmpty()) {
            sb.append(dq.pollFirst());
        }
        return sb.substring(0, number.length() - k);
    }
}