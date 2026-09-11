import java.util.*;

class Solution {

    private int N, target;
    private List<List<Integer>> answer;
    private List<Integer> list;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        N = candidates.length;
        this.target = target;
        answer = new ArrayList<>();
        
        list = new ArrayList<>();
        combination(candidates, 0, 0);
        
        return answer;
    }

    private void combination(int[] candidates, int start, int sum) {
    	if(sum > this.target) return;
    	
    	if(sum == this.target) {                
            answer.add(new ArrayList<>(list));
            return;
        }    

        for(int i = start; i < N; i++) {
            list.add(candidates[i]);            
            combination(candidates, i, sum + candidates[i]);
            list.remove(list.size() - 1);
        }
    }
}