/* https://programmers.co.kr/learn/courses/30/lessons/81302 */
/* 구현 or DFS/BFS 문제, DFS/BFS 풀이가 더 깔끔한듯 하여 다시 풀어보는 게 좋을 것 같다.
 *  각 방마다 지원자 간에 거리두기 규칙을 지키고 있는지 1:1로 모두 확인했다.
 */
import kotlin.math.abs

class Solution {
    data class Coord(val row: Int, val col: Int)
    
    lateinit var mPlaces: Array<Array<String>>
    
    fun solution(places: Array<Array<String>>): IntArray {
        mPlaces = places
        val candidatesArray = Array(5) {
            mutableListOf<Coord>()
        }
        
        // 각 방의 지원자들을 뽑아낸다
        for (roomNumber in 0 until 5) {
            val room = places[roomNumber]
            
            for (row in 0 until 5) {
                for (col in 0 until 5) {
                    if (room[row][col] == 'P') {
                        candidatesArray[roomNumber].add(Coord(row, col))
                    }
                }
            }
        }
        
        val isCompliedArray = checkIfComplied(candidatesArray)
        return isCompliedArray
    }
    
    /**
     * 각 방의 지원자마다 방안의 모든 지원자들과 거리두기 조건을 지키고 있는지 1vs1로 확인한다
     */
    private fun checkIfComplied(candidatesArray: Array<MutableList<Coord>>): IntArray {
        val isCompliedArray = IntArray(5) { 1 }
        
        for (roomNum in candidatesArray.indices) {
            val candidateList = candidatesArray[roomNum] // 방 안의 지원자들의 좌표가 있는 리스트
            
            for (i in candidateList.indices) {
                val cand = candidateList[i] // 지원자 좌표
                
                // 다른 지원자들과 거리두기 규칙을 만족하고 있는지 확인
                for (j in i + 1 until candidateList.size){
                    if (isComplied(roomNum, cand, candidateList[j]).not()) {
                        isCompliedArray[roomNum] = 0
                    }
                }   
            }
        }
        
        return isCompliedArray
    }
    
    /**
     * cand1: 지원자1, cand2: 지원자2,
     * 두 지원자가 규칙을 지키고 있는지 확인한다.
     */
    private fun isComplied(roomNum: Int, cand1: Coord, cand2: Coord): Boolean {
        // 맨해튼 거리가 2초과인 경우
        if (abs(cand1.row - cand2.row) + abs(cand1.col - cand2.col) > 2) {
            return true
        } else { // 맨해튼 거리가 2이하이지만, 사이에 파티션이 있는 모든 경우
            
            if (cand1.row == cand2.row){ // 같은 행
                if ((cand1.col > cand2.col && mPlaces[roomNum][cand1.row][cand1.col - 1] == 'X') || (cand1.col < cand2.col && mPlaces[roomNum][cand1.row][cand1.col + 1] == 'X')) {
                    return true
                }
            } else if (cand1.col == cand2.col) { // 같은 열
                if ((cand1.row > cand2.row && mPlaces[roomNum][cand1.row - 1][cand1.col] == 'X') || (cand1.row < cand2.row && mPlaces[roomNum][cand1.row + 1][cand1.col] == 'X')) {
                    return true
                }
            } else {
                // c1  x
                //  x c2
                if (cand1.row < cand2.row && cand1.col < cand2.col && mPlaces[roomNum][cand1.row][cand2.col] == 'X' && mPlaces[roomNum][cand2.row][cand1.col] == 'X') {
                    return true
                } 
                // c2  x
                //  x c1
                else if (cand1.row > cand2.row && cand1.col > cand2.col && mPlaces[roomNum][cand1.row][cand2.col] == 'X' && mPlaces[roomNum][cand2.row][cand1.col] == 'X') {
                    return true
                } 
                //  x c1
                // c2  x
                else if (cand1.row < cand2.row && cand1.col > cand2.col && mPlaces[roomNum][cand1.row][cand2.col] == 'X' && mPlaces[roomNum][cand2.row][cand1.col] == 'X') {
                    return true
                } 
                //  x c2
                // c1  x
                else if (cand1.row > cand2.row && cand1.col < cand2.col && mPlaces[roomNum][cand1.row][cand2.col] == 'X' && mPlaces[roomNum][cand2.row][cand1.col] == 'X') {
                    return true
                }
            }
        }
        
        return false
    }
}