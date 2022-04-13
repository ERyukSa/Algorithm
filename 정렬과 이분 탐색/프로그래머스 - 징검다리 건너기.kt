/* https://programmers.co.kr/learn/courses/30/lessons/64062 */
/* 최대 몇 명이 건널 수 있는지를 기준으로 이분 탐색한다. */

import kotlin.math.max

class Solution {
    lateinit var mStones: IntArray
    
    fun solution(stones: IntArray, k: Int): Int {
        mStones = stones
        
        var start = 1
        var end = 200_000_000
        
        while (start <= end) {
            val mid = (start + end) / 2
            
            // {mid}명이 건널 수 있다면
            if (isCrossable(mid, k)) {
                start = mid + 1 // 최대 값 찾기 위해 범위를 오른쪽으로 이동
            } else {
                end = mid - 1
            }
        }
        
        return end
    }
    
    fun isCrossable(numOfPeople: Int, jumpDistance: Int): Boolean {
        var sequentZero = 0 // 사라진 다리(디딤돌) 길이
        
        for (stone in mStones) {
            // {numOfPeople}명이 건너기 전에 디딤돌이 사라진다면
            if (stone - numOfPeople < 0) {
                // 사라진 다리 길이가 k보다 크거나 같으면 건널 수 없음
                if (++sequentZero >= jumpDistance) {
                    return false
                }
            } else {
                sequentZero = 0
            }
        }
        
        return true
    }
}