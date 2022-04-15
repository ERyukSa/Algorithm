/* https://programmers.co.kr/learn/courses/30/lessons/64063 */
/* 경로 압축 사용 - Union-Find의 Find에서 착안 */

import java.util.*

class Solution {
    val nextRoomOf = HashMap<Long, Long>() // 해당 번호의 다음 호텔 방 번호
    
    fun solution(k: Long, room_number: LongArray): LongArray {
        var answer = LongArray(room_number.size)
        
        for (i in room_number.indices) {
            val wantedRoom = room_number[i]
            
            // 다음 호텔 방 번호가 없다 -> 아직 해당 번호의 방은 배정이 안됐다
            if (nextRoomOf.contains(wantedRoom).not()) {
                answer[i] = wantedRoom
                // 방을 배정해주고, 그 다음 번호를 다음 배정 가능한 호텔 방으로 지정한다
                nextRoomOf[wantedRoom] = wantedRoom + 1
            } else {
                // 해당 번호는 이미 배정됐으므로, 그 다음 가능한 방을 찾는다
                val availableRoom = findNextRoom(nextRoomOf[wantedRoom]!!) // 그 다음 번호가
                answer[i] = availableRoom
                nextRoomOf[availableRoom] = availableRoom + 1
            }
        }
        
        return answer
    }
    
    fun findNextRoom(num: Long): Long {
        val nextRoom = nextRoomOf[num]
        // 호텔방 num이 아직 배정 안됐다면
        // nextRoomOf[num]은 null값을 갖는다
        if (nextRoom == null) {
            return num
        }

        // 재귀적으로 배정 가능한 다음 방 번호를 찾는다 
        // 경로 압축: 다시 처음부터 타고 올라가지 않도록 
        // 배정 가능한 다음 방 번호를 기록한다.
        nextRoomOf[num] = findNextRoom(nextRoom!!)
        return nextRoomOf[num]!!
    }
}