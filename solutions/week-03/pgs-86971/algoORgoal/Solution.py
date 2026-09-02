# 1. disjoint set 만들기
# 2. 모든 edge에 대하여 union 수행하기
# 3. 모든 노드에 대하여 find 연산 수행하고, 대표 노드의 개수 세기


class DisjointSet:
    def __init__(self, nodes):
        self.parent = { node: node for node in nodes }
        
    def find(self, node):
        if node == self.parent[node]:
            return node
        self.parent[node] = self.find(self.parent[node])
        return self.parent[node]
    
        
    
    def union(self, node1, node2):
        root1 = self.find(node1)
        root2 = self.find(node2)
        
        if root1 == root2:
            return
        
        self.parent[root1] = root2


def solution(n, computers):
    nodes = [ node for node in range(0, n) ]
    disjoint_set = DisjointSet(nodes)
    
    for i in range(0, len(computers)):
        for j in range(0, len(computers[i])):
            if computers[i][j] == 1:
                disjoint_set.union(i, j)
                
    root_nodes = set()
    
    for node in nodes:
        root_node = disjoint_set.find(node)
        root_nodes.add(root_node)
    
    return len(root_nodes)
    
    
