/* https://programmers.co.kr/learn/courses/30/lessons/42892# */
/* 이진 트리 만들기, 그래프 순회 */

import java.util.*

class Solution {
    data class Node(val row: Int, val col: Int, val id: Int, var left: Node? = null, var right: Node? = null)

    val levelNodes = mutableListOf<Queue<Node>>()
    val preList = mutableListOf<Int>()
    val postList = mutableListOf<Int>()

    fun solution(nodeInfo: Array<IntArray>): Array<IntArray> {
        val nodeList = LinkedList<Node>()
        var rootNode: Node? = null

        for (i in nodeInfo.indices) {
            val info = nodeInfo[i]
            val row = info[1]; val col = info[0]
            val node = Node(row, col, i + 1)
            nodeList.add(node)
        }

        nodeList.sortWith(compareByDescending<Node>{it.row}.thenBy{it.col})
        rootNode = nodeList.peek()

        levelNodes.add(LinkedList<Node>())
        levelNodes[0].offer(rootNode)

        for (node in nodeList) {
            if (levelNodes.last().peek().row != node.row) {
                levelNodes.add(LinkedList<Node>())
            }
            levelNodes.last().offer(node)
        }

        makeTree(rootNode, 0, 0, 100_000)
        preOrder(rootNode)
        postOrder(rootNode)

        return arrayOf(preList.toIntArray(), postList.toIntArray())
    }

    fun makeTree(node: Node, level: Int, leftLimit: Int, rightLimit: Int) {
        if (level >= levelNodes.lastIndex) return

        val queue = levelNodes[level + 1]

        if (queue.isNotEmpty() && queue.peek().col in leftLimit until node.col){
            node.left = queue.poll()
            makeTree(node.left!!, level + 1, leftLimit, node.col - 1)
        }
        if (queue.isNotEmpty() && queue.peek().col in node.col + 1..rightLimit){
            node.right = queue.poll()
            makeTree(node.right!!, level + 1, node.col + 1, rightLimit)
        } 

    }

    fun preOrder(node: Node) {
        preList.add(node.id)

        if (node.left != null) {
            preOrder(node.left!!)
        }
        if (node.right != null) {
            preOrder(node.right!!)
        }
    }

    fun postOrder(node: Node) {
        if (node.left != null) {
            postOrder(node.left!!)
        }
        if (node.right != null) {
            postOrder(node.right!!)
        }

        postList.add(node.id)
    }
}