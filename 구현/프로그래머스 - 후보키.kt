/* https://programmers.co.kr/learn/courses/30/lessons/42890 */
/* 순열, 해싱, 문자열 검사 */

import java.util.*

class Solution {
    lateinit var table: Array<Array<String>>
    lateinit var keySet: Array<HashSet<String>>
    
    fun solution(relation: Array<Array<String>>): Int {
        var answer = 0
        table = relation
        keySet = Array(table[0].size + 1) { HashSet<String>() }
        
        selectColumns(0, mutableListOf<Int>())
        
        for (srcLength in 1 until relation[0].size) {
            for (key in keySet[srcLength]) {
                for (length in srcLength + 1..relation[0].size) {
                    val keyList = keySet[length].toList()

                    for (i in keyList.indices) {
                        val currentKey = keyList[i]
                        var duplicated = true
                        
                        for (ch in key) {
                            if (ch !in currentKey) {
                                duplicated = false
                                break
                            }
                        }
                        if (duplicated) keySet[length].remove(currentKey)
                    }
                }
            }
        }
        
        for (length in 1..relation[0].size) {
            answer += keySet[length].size
        }
        
        return answer
    }
    
    fun selectColumns(colIdx: Int, selectedCols: MutableList<Int>) {
        if (colIdx >= table[0].size) {
            if (isValid(selectedCols)) {
                val key = makeKey(selectedCols)
                keySet[selectedCols.size].add(key)
            }
            return
        }
        
        selectedCols.add(colIdx)
        selectColumns(colIdx + 1, selectedCols)
        
        selectedCols.removeAt(selectedCols.lastIndex)
        selectColumns(colIdx + 1, selectedCols)
    }
    
    fun isValid(columns: MutableList<Int>): Boolean {
        val rowSet = HashSet<String>()
        
        for (tuple in table) {
            val sb = StringBuilder()
            
            for (selectedCol in columns){
                sb.append(tuple[selectedCol]).append(',')
            }
            rowSet.add(sb.toString())
        }
        
        return if (rowSet.size == table.size) true else false
    }
    
    fun makeKey(columns: MutableList<Int>): String {
        val sb = StringBuilder()
        columns.forEach {
            sb.append(it)
        }
        return sb.toString()
    }
}