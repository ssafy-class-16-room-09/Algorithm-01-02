import java.util.*;

class Solution {
    public int solution(int n, int[][] wires) {
        int answer = n;
        int diff = 0;

        for (int i = 0; i < wires.length; i++) {
            List<Integer> sec1 = new ArrayList<>();
            List<Integer> sec2 = new ArrayList<>();
            int a = wires[i][0];
            int b = wires[i][1];

            sec1.add(a);
            sec2.add(b);
            
            boolean check=true;
            
            while (check) {
                check = false;
                for (int j = 0; j < wires.length; j++) {
                    int aa = wires[j][0];
                    int bb = wires[j][1];

                    if (i != j) {
                        if (sec1.contains(aa) && !sec1.contains(bb)) {
                            sec1.add(bb);
                            check = true;

                        } else if (sec2.contains(aa) && !sec2.contains(bb)) {
                            sec2.add(bb);
                            check = true;

                        } else if (sec1.contains(bb) && !sec1.contains(aa)) {
                            sec1.add(aa);
                            check = true;

                        } else if (sec2.contains(bb) && !sec2.contains(aa)) {
                            sec2.add(aa);
                            check = true;
                        }
                    }
                }
            }

            diff = Math.abs(sec1.size() - sec2.size());
            answer = Math.min(answer, diff);
            System.out.print(i);
            System.out.print(sec1);
            System.out.println(sec2);
            System.out.println("i = " + i + ", diff = " + diff);
        }

        return answer;
    }
}