import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        Queue<String> cache = new ArrayDeque<>();
        
        if(cacheSize == 0) {
            return 5 * cities.length;
        }
        
        int time = 0;
        for(String name : cities) {
            String city = name.toLowerCase();
            if(cache.remove(city)) {                
                time += 1;
            } else {
                time += 5;
            }
            
            if(cache.size() >= cacheSize) {
                cache.poll();
            }
            cache.offer(city);
        }      
        
        return time;
    }
}