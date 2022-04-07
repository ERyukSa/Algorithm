/* https://programmers.co.kr/learn/courses/30/lessons/92343 */
/* 완탐 + 재귀(백트래킹), 비트마스킹+메모이제이션으로 최적화할 수 있다 */
/* 방문 순서는 중요하지 않다. 어떤 state를 방문했는지가 중요하다. */
/* 1->3->4나 1->4->3이나 같다. 양이 더 많을 때만 다음 방문을 진행하기 때문이다 */

import java.util.*
import kotlin.math.max

class Solution {
    lateinit var isWolf: IntArray
    lateinit var graph: Array<MutableList<Int>> // 인접리스트
    private val visited = HashSet<Int>() // 비트마스킹
    private var maxSheep = 1 // 정답

    fun solution(info: IntArray, edges: Array<IntArray>): Int {
        isWolf = info
        graph = Array(info.size){ mutableListOf() }

        for (edge in edges) {
            graph[edge[0]].add(edge[1])
        }

        visitNode(0, 0, 0, LinkedList(), 1)

        return maxSheep
    }

    private fun visitNode(idx: Int, sheep: Int, wolf: Int, nextNodes: LinkedList<Int>, bitMask: Int) {
        var currentSheep = sheep
        var currentWolf = wolf

        if (isWolf[idx] == 0) {
            currentSheep += 1
            maxSheep = max(maxSheep, currentSheep)
        } else {
            currentWolf += 1
        }

        // 늑대 > 양 -> 방문 불가
        if (isWolf[idx] == 1 && currentSheep <= currentWolf) return

        // 방문 가능한 노드 추가
        for (child in graph[idx]) {
            nextNodes.offer(child)
        }

        val size = nextNodes.size
        repeat(size) {
            val nextNode = nextNodes.poll()
            val newMask = bitMask + 1.shl(nextNode)
            
            // 탐색했던 state가 아니면 탐색
            if (visited.contains(newMask).not()){
                visited.add(newMask)
                visitNode(nextNode, currentSheep, currentWolf, nextNodes, newMask)
            }
            
            nextNodes.offer(nextNode)
        }
        
        // 추가했던 노드들 제거 --> 부모 노드의 다음 for문에서 중복 방문하지 않도록 하기 위함
        repeat(graph[idx].size) {
            nextNodes.removeLast()
        }
    }
}


/* 코드2 --------------------------------------------- */

class Solution {
    lateinit var isWolf: IntArray
    lateinit var graph: Array<MutableList<Int>>
    private val visited = HashSet<Int>()

    fun solution(info: IntArray, edges: Array<IntArray>): Int {
        isWolf = info
        graph = Array(info.size){ mutableListOf() }

        for (edge in edges) {
            graph[edge[0]].add(edge[1])
        }

        return findMaxSheep(0, 0, 0, LinkedList(), 1)
    }

    private fun findMaxSheep(idx: Int, sheep: Int, wolf: Int, nextNodes: LinkedList<Int>, bitMask: Int): Int {
        if (isWolf[idx] == 1 && sheep <= wolf + 1) return sheep

        // 방문 가능한 노드 추가
        for (child in graph[idx]) {
            nextNodes.offer(child)
        }

        var currentSheep = sheep
        var currentWolf = wolf

        if (isWolf[idx] == 0) {
            currentSheep += 1
        } else {
            currentWolf += 1
        }

        var maxSheep = currentSheep
        val size = nextNodes.size

        repeat(size) {
            val nextNode = nextNodes.poll()
            val newMask = bitMask + 1.shl(nextNode)
            if (visited.contains(newMask).not()){
                visited.add(newMask)
                maxSheep = max(maxSheep, findMaxSheep(nextNode, currentSheep, currentWolf, nextNodes, newMask))
            }
            nextNodes.offer(nextNode)
        }

        repeat(graph[idx].size) {
            nextNodes.removeLast()
        }

        return maxSheep
    }
}