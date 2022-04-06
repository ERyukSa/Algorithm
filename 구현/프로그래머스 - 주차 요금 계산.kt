/* https://programmers.co.kr/learn/courses/30/lessons/92341 
   카카오 2022 공채 문제
   구현, 자료구조, 문자열 처리
*/
import java.util.*
import kotlin.math.max

data class Car(val id: String, val inMinute: Int)

const val LAST_MINUTE = 1439

class Solution {
    var defaultMinute = 0; var defaultFee = 0
    var unitMinute = 0; var unitFee = 0
    val timeMap = HashMap<String, Int>()
    val carMap = HashMap<String, Car>()

    fun solution(fees: IntArray, records: Array<String>): IntArray {
        val answer = mutableListOf<Int>()
        defaultMinute = fees[0]; defaultFee = fees[1]
        unitMinute = fees[2]; unitFee = fees[3]

        records.forEach{ record ->
            val parseRecord = record.split(" ")
            val carId = parseRecord[1]
            val minute = parseRecord[0].substring(0, 2).toInt() * 60 + parseRecord[0].substring(3).toInt()
            val isIn = parseRecord[2] == "IN"

            if (isIn) {
                val car = Car(carId, minute)
                carMap[carId] = car
            } else {
                setUsedMinute(carId, minute)
                carMap.remove(carId)
            }
        }

        for (car in carMap.values) {
            setUsedMinute(car.id, LAST_MINUTE)
        }

        for (id in timeMap.keys.sorted()) {
            val fee = calculateFee(timeMap[id]!!)
            answer.add(fee)
        }

        return answer.toIntArray()
    }

    fun setUsedMinute(carId: String, outMinute: Int) {
        val car = carMap[carId]!!
        val usedMinute = outMinute - car.inMinute

        if (timeMap.contains(carId)) {
            timeMap[carId] = timeMap[carId]!! + usedMinute
        } else {
            timeMap[carId] = usedMinute
        }
    }

    fun calculateFee(minute: Int): Int {
        val extraMinute = minute - defaultMinute
        var fee = defaultFee + if (extraMinute <= 0) 0 else extraMinute / unitMinute * unitFee
        // 올림
        if (extraMinute > 0 && extraMinute % unitMinute != 0) {
            fee += unitFee
        }

        return fee
    }
}