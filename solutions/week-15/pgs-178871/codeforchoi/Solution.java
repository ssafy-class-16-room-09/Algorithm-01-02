import java.util.*;

class Solution {
    public String[] solution(String[] players, String[] callings) { 
        Map<String, Integer> rank = new HashMap<>();
        
        for(int i = 0; i < players.length; i++) {
            rank.put(players[i], i);
        }
        
        for(String name : callings) {
            int index = rank.get(name);
            String front = players[index - 1];
            
            players[index - 1] = name;
            players[index] = front;
            
            rank.put(name, index - 1);
            rank.put(front, index);           
        }
        
        return players;
    }
}