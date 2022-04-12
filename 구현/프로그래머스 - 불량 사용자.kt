/* https://programmers.co.kr/learn/courses/30/lessons/64064 */
/* 순열, 비트마스크, 유연한 사고와 큰그림을 그릴 수 있어야 한다. */

import java.util.*

class Solution {

    lateinit var userIds: Array<String>
    lateinit var bannedIds: Array<String>
    lateinit var permutation: Array<String> // 순열 저장할 배열
    lateinit var selected: BooleanArray // 순열 고를 때 이미 선택한 원소인지 확인
    val idxMap = HashMap<String, Int>() // 아이디가 리스트 몇 번째 인덱스인지? -> 비트로 활용
    val maskSet = HashSet<Int>() // bitmask 저장
    var banSize = 0
    var count = 0
    
    fun solution(user_id: Array<String>, banned_id: Array<String>): Int {
        userIds = user_id
        bannedIds = banned_id
        selected = BooleanArray(user_id.size)
        banSize = banned_id.size
        permutation = Array(banSize){""}

        for (i in user_id.indices) {
            idxMap[user_id[i]] = i
        }
        
        findPermutation(0)
        
        return count
    }

    fun findPermutation(selectCount: Int) {
        if (selectCount >= banSize) {
            if (isValidPermutation()) {
                val bitmask = convertToBitmask()
                if (maskSet.contains(bitmask)) return // 불량 아이디 조합 중복 체크
                maskSet.add(bitmask)
                count++
            }
            return
        }
        
        for (i in userIds.indices) {
            if (selected[i]) continue
            selected[i] = true
            permutation[selectCount] = userIds[i]
            findPermutation(selectCount + 1)
            selected[i] = false
        }
    }
    
    // 순열이 각 불량 아이디와 매핑되는지 확인
    fun isValidPermutation(): Boolean {
        for (i in permutation.indices) {
            val userId = permutation[i]
            val banId = bannedIds[i]
            
            // 같은 위치의 유저 아이디와 불량 아이디가 매핑 되는지 확인
            if (isMapped(userId, banId).not()) return false 
        }
        
        return true
    }
    
    // 유저 아이디가 불량 아이디와 매핑되는지 확인
    fun isMapped(userId: String, banId: String): Boolean {
        if (userId.length != banId.length) return false
        
        for (i in userId.indices) {
            if (userId[i] != banId[i] && banId[i] != '*') {
                return false
            }
        }
        
        return true
    }
    
    fun convertToBitmask(): Int {
        var mask = 0
        
        for (id in permutation) {
            val bit = idxMap[id]!!
            mask += 1.shl(bit)
        }
        
        return mask
    }
}