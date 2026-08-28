class Solution {
    int answer = 0;
    char[] vowels = {'A', 'E', 'I', 'O', 'U'};
    String target;
    boolean found = false;
        
    public int solution(String word) {
        target = word;
        dfs("");
        return answer;
    }
    
    void dfs(String current){
        if (found){ //찾으면 끝
            return;
        }
        if (!current.equals("")){
            answer++;
        }
        if (current.equals(target)){
            found = true;
            return;
        }
        if (current.length() == 5){
            return;
        }
        for (char voewl : vowels){
            if (found) return;
            // System.out.println(current + voewl);
            
            dfs(current+voewl);
        }
    }
}