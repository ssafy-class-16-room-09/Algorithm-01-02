class Solution {
  public int solution(int n) {
    int divide = 1234567;
    int[] fibo = new int[n + 1];

    fibo[0] = 0;
    fibo[1] = 1;

    for (int i = 2; i <= n; i++) {
      fibo[i] = (fibo[i - 2] + fibo[i - 1]) % divide;
    }

    return fibo[n];
  }
}