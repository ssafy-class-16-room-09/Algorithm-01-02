import java.util.*;

//O(nlogn)
class Solution {
    public int solution(int[] d, int budget) {
        Arrays.sort(d);
        
        int count = 0;
        for(int cost : d) {
            if(budget - cost < 0) {
                return count;
            }
            
            budget -= cost;
            count++;
        }
        
        return count; 
    }
}