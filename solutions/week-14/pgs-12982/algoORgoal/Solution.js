// O(nlogn)
// O(n)

function solution(d, budget) {
  let current = budget;
  let total = 0;

  d.sort((a, b) => (a < b ? -1 : 1)); // 문자열로 변환 후 비교

  for (let cost of d) {
    if (current < cost) {
      break;
    }

    current -= cost;
    total += 1;
  }

  return total;
}

// 최대 몇개의 부서에 물품 지원 가능한지 반환하기
// 주어진 예산을 모두 사용할 필요는 없음
// 주어진 예산을 사용해서 구매할 수 있는 최대 물품 개수 구하기
// 1. 물건 금액 순으로 오름차순 정렬
// 2. 돌면서 확인하기
