from collections import deque

def solution(prices):
    answer = []
    queue = deque(prices)
    
    while queue:
        price = queue.popleft()
        num = 0
        for q in queue:
            num+=1
            if price > q:
                break
        answer.append(num)
        
    return answer