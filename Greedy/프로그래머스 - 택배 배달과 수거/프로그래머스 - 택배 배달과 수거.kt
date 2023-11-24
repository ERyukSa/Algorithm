package `프로그래머스 - 택배 배달과 수거`

class Solution {
    fun solution(cap: Int, n: Int, deliveries: IntArray, pickups: IntArray): Long {
        var giveIndex = -1
        var pickupIndex = -1
        var distance = 0L

        for (i in n - 1 downTo 0) {
            if (deliveries[i] > 0 && giveIndex == -1) giveIndex = i
            if (pickups[i] > 0 && pickupIndex == -1) pickupIndex = i
            if (giveIndex > -1 && pickupIndex > -1) break
        }

        while (giveIndex >= 0 || pickupIndex >= 0) {
            distance += (maxOf(giveIndex, pickupIndex) + 1) * 2

            var boxCount = cap
            while (giveIndex >= 0) {
                if (boxCount >= deliveries[giveIndex]) {
                    boxCount -= deliveries[giveIndex]
                    deliveries[giveIndex] = 0
                    giveIndex--
                } else {
                    deliveries[giveIndex] -= boxCount
                    break
                }
            }

            var capacity = cap
            while (pickupIndex >= 0) {
                if (capacity >= pickups[pickupIndex]) {
                    capacity -= pickups[pickupIndex]
                    pickups[pickupIndex] = 0
                    pickupIndex--
                } else {
                    pickups[pickupIndex] -= capacity
                    break
                }
            }
        }

        return distance
    }
}