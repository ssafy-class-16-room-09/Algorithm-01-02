import java.util.*;

class Solution {
  public boolean solution(String[] phone_book) {
    Arrays.sort(phone_book);

    for (int i = 0; i < phone_book.length - 1; i++) {
      String cur = phone_book[i];
      String next = phone_book[i + 1];

      if (cur.length() > next.length())
        continue;

      boolean isPerfix = true;

      for (int j = 0; j < cur.length(); j++) {
        if (cur.charAt(j) != next.charAt(j)) {
          isPerfix = false;
          break;
        }
      }

      if (isPerfix) {
        return false;
      }
    }

    return true;
  }
}