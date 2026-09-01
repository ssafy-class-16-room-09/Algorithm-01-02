from collections import deque


def solution(begin, target, words):

    graph = {}
    for word1 in words + [begin]:
        for word2 in words + [begin]:
            count = 0
            for char1, char2 in zip(word1, word2):
                if char1 != char2:
                    count += 1

            if count == 1:
                if word1 not in graph:
                    graph[word1] = []
                graph[word1].append(word2)
    return bfs(graph, begin, set(), target)


def bfs(graph, root, visited, target):
    queue = deque([(root, 0)])
    visited.add(root)

    while len(queue) > 0:
        current, distance = queue.pop()

        if current == target:
            return distance

        if current not in graph:
            continue

        next_nodes = graph[current]
        for next_node in next_nodes:
            if next_node in visited:
                continue
            visited.add(next_node)
            queue.appendleft((next_node, distance + 1))

    return 0


# 최단거리를 구해야하므로 bfs 사용
# 그래프 구성하되, edge weight는 모두 1
# 1자리수 차이나는 것을 모두 adjancent nodes로 간주하도록 그래프 구성하면 됌
# 그래프 구성 시간복잡도 O(n ** 2)
# bfs 탐색 O(n + m) = O(n ** 2)

# 모든 경우의 수 탐색 => 50! = 30414093201713378043612608166064768844377641568960512000000000000
# 시간 초과
