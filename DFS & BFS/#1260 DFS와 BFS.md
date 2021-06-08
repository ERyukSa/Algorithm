## 문제 유형
DFS와 BFS의 기본 구현을 묻는 문제

---

## DFS(깊이 우선 탐색)란?
특정 노드에서 시작해서, **깊이를 우선으로** 고려하여 연결되어 있는 모든 노드를 방문하는 그래프 탐색 알고리즘. 

주로 **스택(재귀 함수)** 자료구조를 사용한다.

---

## BFS(너비 우선 탐색)란?
특정 노드에서 시작해서, DFS와 달리 **너비(인접 노드)를 우선으로** 고려하여 연결된 모든 노드를 방문하는 그래프 탐색 알고리즘.

주로 **큐** 자료구조를 사용한다.

---

## 동작 과정
* DFS
    1. 시작 노드를 스택에 넣고(재귀 함수 호출) 방문 처리를 한다.
    2. 스택의 최상단 노드에 방문하지 않은 인접 노드가 있으면, 스택에 넣고 방문처리를 한다. 방문 가능한 인접노드가 없다면 스택 최상단에서 제거한다.
    3. 2번 과정을 불가능할 때까지 반복한다.


* BFS
    1. 시작 노드를 큐에 넣고 방문 처리를 한다.
    2. 큐에서 노드를 꺼내고, 인접 노드 중 방문하지 않은 노드가 있으면 큐에 넣고 방문 처리를 한다.
    3. 2번 과정을 불가능할 때까지 반복한다.  

---

## Code

```python
from collections import deque

v, e, s = map(int, input().split()
graph = [[] for _ in range(v + 1)]
for _ in range(e):
    v1, v2 = map(int, input().split())
    graph[v1].append(v2)
    graph[v2].append(v1)

# 숫자가 작은 순서대로 방문하기 위해 오름차순 정렬
for i in range(1, v + 1):
    graph[i].sort()

visited = [False] * (v + 1)
visited[s] = True

def process_dfs(node):
    global visited
    
    print(node, end=' ')
    
    # 스택 최상단 노드(node)의 인접 노드들 중, 깊이 우선으로 방문 가능한지 검사
    for n in graph[node]:
        if visited[n] == False:
            visited[n] = True
            process_dfs(n)
            
def process_bfs(start):
    global visited
    
    q = deque()
    q.append(start)
    
    while q:
        node = q.popleft()
        print(node, end=' ')
        
        # 큐 맨 앞 노드의 인접 노드들 중 너비 우선으로 방문 가능한지 검사
        for n in graph[node]:
            if visited[n] == False:
                visited[n] = True
                q.append(n)

process_dfs(s)
visited = [False] * (v + 1)
visited[s] = True
print()
process_bfs(s)
```

## Tip
이 문제에서 스택을 재귀함수로 구현할 때, 종료조건을 넣지 않았다.

***Why?***  호출한 노드의 인접노드들 중 방문 가능한 노드가 없으면 함수는 자동 종료되기 떄문이다.

즉, 방문 여부를 확인했기 때문에 모든 노드를 한번씩만 방문하게 되므로, 방문 여부를 확인한 것이 종료 조건을 검사한 것과 같게 된다.

***But*** 호출한 이후에 방문 여부를 검사해서, 재귀 함수에 종료 조건을 넣을 수도 있다.
