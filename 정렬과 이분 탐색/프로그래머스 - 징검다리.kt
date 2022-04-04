/* https://programmers.co.kr/learn/courses/30/lessons/43236 */
/* 레벨4, 풀었다! */

class Solution {
    lateinit var mRocks: IntArray
    var dst = 0
    var removeLimit = 0
    
    fun solution(distance: Int, rocks: IntArray, n: Int): Int {
        var start = 1
        var end = distance
        
        dst = distance
        removeLimit = n
        mRocks = rocks
        rocks.sort()
        
        while(start <= end) {
            val mid = (start + end) / 2
            
            if (isValid(mid)) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        
        return end
    }
    
    fun isValid(minDist: Int): Boolean {
        var removeCount = 0
        var prevRock = 0
        
        for (i in mRocks.indices) {
            val rock = mRocks[i]
            
            if (rock - prevRock < minDist) {
                removeCount++
            } else {
                if (dst - rock < minDist) {
                    removeCount += mRocks.size - i
                    break
                }
                
                prevRock = rock
            }
        }
        
        return if (removeCount <= removeLimit) true else false
    }
}