// 1. number를 digits 배열로 변환
// 2. stack을 유지하고 다음을 수행
//    top > element일 때까지 pop하기.
//    만약 k번 제거했으면, 나머지 숫자 전부 붙이기.
//    만약 k번 제거 안 했으면, stack에 들어있는 것이 내림차순 정렬되어있다는 뜻이므로 담은 횟수 뒤에서 자르기
// 시간복잡도: O(n)
// 공간복잡도: O(n)

// 모든 경우의 수: nCk (n <= 1_000_000, 1 <= k <= n)

function solution(number, k) {
  const digits = number.split("").map((numStr) => Number(numStr));

  let total = 0;

  const stack = [];

  for (let i = 0; i < digits.length; i++) {
    const digit = digits[i];

    while (stack.length > 0 && stack[stack.length - 1] < digit && total < k) {
      stack.pop();
      total += 1;
    }

    stack.push(digit);

    if (total === k) {
      digits.slice(i + 1).forEach((rest) => {
        stack.push(rest);
      });
      break;
    }
  }

  if (total < k) {
    const offset = k - total;
    return stack.slice(0, stack.length - offset).join("");
  }

  return stack.join("");
}
