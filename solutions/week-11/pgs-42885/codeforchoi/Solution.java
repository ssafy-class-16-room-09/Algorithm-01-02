import java.util.*;

class Solution {
  public int solution(int[] people, int limit) {

    Arrays.sort(people);

    int left = 0;
    int right = people.length - 1;

    int count = 0;

    while (left <= right) {
      // 두 사람이 탈 수 있는 경우
      if (people[left] + people[right] <= limit) {
        left++;
      }

      right--;
      count++;
    }

    return count;
  }
}