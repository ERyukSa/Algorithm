/* https://programmers.co.kr/learn/courses/30/lessons/67258 */
/* 이진 탐색 + 투포인터, Map 활용 */

import java.util.*

class Solution {
    val gemSet = HashSet<String>()
    
    fun solution(gems: Array<String>): IntArray {
       
        for (gem in gems) {
            gemSet.add(gem)
        }
        
        var start = gemSet.size // 최소 길이: 보석 종류의 수
        var end = gems.size // 최대 길이: 진열대 길이
        // 정답
        var rangeLeft = 0 
        var rangeRight = 0 
        
        while (start <= end) {
            val mid = (start + end) / 2
            // mid 구간으로 전체 보석을 포함시킬 수 있는지 확인
            val result = findValidRange(gems, mid)
            
            if (result != null) { // 가능하면 정답을 저장하고 최소 길이를 찾는다
                end = mid - 1
                rangeLeft = result.first
                rangeRight = result.second
            } else { // 불가능하면 길이를 더 늘려서 찾아본다
                start = mid + 1
            }
        }
        
        return intArrayOf(rangeLeft, rangeRight)
    }
    
    fun findValidRange(gems: Array<String>, length: Int): Pair<Int, Int>? {
        var start = 0
        var end = 0
        val countOfGem = HashMap<String, Int>() // [start, end) 범위 내 보석 count
        var gemInRange = 0 // [start, end) 범위에 있는 보석 종류의 개수
        
        while (end < gems.size) {
            val newGem = gems[end++]
            
            if (countOfGem.contains(newGem).not()) {
                    countOfGem[newGem] = 0
            }
                
            countOfGem[newGem] = countOfGem[newGem]!! + 1
            if (countOfGem[newGem]!! == 1) { // 없었는데 추가될 경우
                gemInRange++
            }
                
            if (end - start >= length) {
                // 범위 안의 보석 종류가 전체 보석을 포함하면 리턴
                if (gemInRange == gemSet.size) {
                    return Pair(start + 1, end)
                }
                
                val removedGem = gems[start++]

                countOfGem[removedGem] = countOfGem[removedGem]!! - 1

                // 범위 안에 해당 보석이 전부 없어진 경우 
                if (countOfGem[removedGem]!! == 0) {
                    gemInRange--
                }
            }
        }
        
        return null
    }
}