/* https://programmers.co.kr/learn/courses/30/lessons/92342 
   완탐 - 재귀(경우의 수)
*/
class Solution {
    
    var answer: List<Int> = emptyList()
    val lionArrow = IntArray(11)
    lateinit var apeechArrow: IntArray 
    var maxDiff = 0
    
    fun solution(n: Int, info: IntArray): IntArray {
        apeechArrow = info
        chooseCase(10, n)
        
        if (maxDiff == 0) {
            return intArrayOf(-1)
        } else {
            return answer.toTypedArray().toIntArray()
        }
    }
    
    fun chooseCase(score: Int, numOfArrow: Int) {
        if (score < 0) {
            val diff = calculateDiff()
            if (diff > 0 && diff >= maxDiff) {
                updateAnswer(diff)
            }

            return
        }

        for (i in numOfArrow downTo 0) {
            lionArrow[10 - score] = i
            chooseCase(score - 1, numOfArrow - i)
        }
    }
    
    fun calculateDiff(): Int {
        var lineScore = 0
        var apeechScore = 0
        
        for (i in 0..10) {
            if (lionArrow[i] == 0 && apeechArrow[i] == 0) continue
            if (lionArrow[i] > apeechArrow[i]) {
                lineScore += 10 - i
            } else {
                apeechScore += 10 - i
            }
        }
        
        return lineScore - apeechScore
    }
    
    fun updateAnswer(currentDiff: Int) {
        if (answer.isEmpty() || currentDiff > maxDiff) {
            maxDiff = currentDiff
            answer = lionArrow.toList()
            return
        }
        
        for (i in 10 downTo 0) {
            if (lionArrow[i] > answer[i]){
                answer = lionArrow.toList()
                return
            } else if (lionArrow[i] < answer[i]) {
                return
            }
        }
    }
}