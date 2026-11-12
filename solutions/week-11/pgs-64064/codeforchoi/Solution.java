import java.util.*;

class Solution {
    
    private boolean[] visited;
    private Set<Set<String>> result;
    
    public int solution(String[] user_id, String[] banned_id) {
        result = new HashSet<>();
        
        visited = new boolean[user_id.length];
        
        dfs(user_id, banned_id, 0, new HashSet<>());
        
        return result.size();
    }
    
    private void dfs(String[] user_id, String[] banned_id, int depth, Set<String> selected) {
        if(depth == banned_id.length) {
            result.add(new HashSet<>(selected));
            return;
        }
        
        for(int i = 0; i < user_id.length; i++) {
            if(visited[i]) continue;
            
            if(!match(user_id[i], banned_id[depth])) continue;
            
            visited[i] = true;
            selected.add(user_id[i]);
            
            dfs(user_id, banned_id, depth + 1, selected);
            
            visited[i] = false;
            selected.remove(user_id[i]);            
        }        
    }
    
    private boolean match(String user_id, String banned_id) {
        if(user_id.length() != banned_id.length()) return false;
        
        for(int i = 0; i < user_id.length(); i++) {
            if(banned_id.charAt(i) == '*') continue;
            
            if(user_id.charAt(i) != banned_id.charAt(i)){
                return false;
            }
        }
        return true;
    }
}