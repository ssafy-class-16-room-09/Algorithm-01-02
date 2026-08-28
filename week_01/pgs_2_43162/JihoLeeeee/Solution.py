from collections import deque

def solution(n, computers):
    answer = 0
    visited = [False] * n

    def bfs(start_v):
        queue = deque([start_v])
        visited[start_v] = True

        while queue:
            cur_v = queue.popleft()

            for i in range(n):
                if computers[cur_v][i] == 1 and not visited[i]:
                    visited[i] = True
                    queue.append(i)

    for i in range(n):
        if visited[i] == False:
            bfs(i)
            answer += 1

    return answer