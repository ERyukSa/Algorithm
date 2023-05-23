/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/150366
 * 문제) 표 병합(카카오 2023 코테 문제)
 * 유형) 유니온 파인드 + 구현
 */

/*fun main() {
    val data = arrayOf("UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4")
    Solution().solution(data)
}*/

class Solution {

    private val CHART_SIZE = 50 * 50
    private val EMPTY_STATE = "EMPTY"
    private val parent = IntArray(CHART_SIZE) { it }
    private val chart = Array(CHART_SIZE) { EMPTY_STATE }

    fun solution(commands: Array<String>): Array<String> {
        var answer = mutableListOf<String>()

        commands.forEach {
            val cmd = it.split(" ")

            when (cmd[0]) {
                "UPDATE" -> {
                    if (cmd.size == 4) {
                        update(cmd[1].toInt() - 1, cmd[2].toInt() - 1, cmd[3])
                    } else {
                        update(cmd[1], cmd[2])
                    }
                }
                "MERGE" -> merge(cmd[1].toInt() - 1, cmd[2].toInt() - 1, cmd[3].toInt() - 1, cmd[4].toInt() - 1)
                "UNMERGE" -> unMerge(cmd[1].toInt() - 1, cmd[2].toInt() - 1)
                else -> answer.add(
                    getValue(cmd[1].toInt() - 1, cmd[2].toInt() - 1)
                )
            }
        }

        return answer.toTypedArray()
    }

    private fun getValue(r: Int, c: Int): String {
        val myParent = findParent(r * 50 + c)
        return chart[myParent]
    }

    private fun update(r: Int, c: Int, value: String) {
        val myParent = findParent(r * 50 + c)
        chart[myParent] = value
    }

    private fun update(targetValue: String, newValue: String) {
        for (i in 0 until CHART_SIZE) {
            if (chart[i] == targetValue) {
                chart[i] = newValue
            }
        }
    }

    private fun unMerge(r: Int, c: Int) {
        val targetParent = findParent(r * 50 + c)
        val value = chart[targetParent]
        val unmergedList = mutableListOf<Int>()

        for (i in 0 until CHART_SIZE) {
            if (findParent(i) == targetParent) {
                unmergedList.add(i)
            }
        }

        unmergedList.forEach {
            parent[it] = it
            chart[it] = EMPTY_STATE
        }

        chart[r * 50 + c] = value
    }

    private fun merge(r1: Int, c1: Int, r2: Int, c2: Int) {
        if (r1 == r2 && c1 == c2) return

        val i1 = r1 * 50 + c1
        val i2 = r2 * 50 + c2
        val parent1 = findParent(i1)
        val parent2 = findParent(i2)

        if (chart[parent1] == EMPTY_STATE && chart[parent2] != EMPTY_STATE) {
            parent[parent1] = parent2
        } else {
            parent[parent2] = parent1
        }
    }

    private fun findParent(i: Int): Int {
        if (parent[i] == i) return i
        val myParent = findParent(parent[i])
        parent[i] = myParent
        return myParent
    }
}