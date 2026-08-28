class Solution {
    public int solution(int n) {

        int a = 0;
        int b = 1;

        for (int i = 0; i < n; i++) {
            int temp = (a + b) % 1234567;
            a = b;
            b = temp;
        }

        return a;
    }
}