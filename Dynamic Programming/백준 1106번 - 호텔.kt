/*
 * https://www.acmicpc.net/problem/1106
 * 다이나믹 프로그래밍
 */
data class City(val cost: Int, val addedCount: Int)

private lateinit var minCostTable: IntArray
private lateinit var cities: Array<City>

fun main() {
    val (wantedCount, cityCount) = readln().split(" ").map(String::toInt)
    minCostTable = IntArray(wantedCount + 1) { -1 }
    cities = Array(cityCount) {
        readln().split(" ").map(String::toInt).run {
            City(cost = this[0], addedCount = this[1])
        }
    }
    getMinCost(wantedCount).let { print(it) }
}

/*
 * f(k) = k명 이상을 유치할 수 있는 최소 비용
 * f(k) = min(city[i].cost + f(k - city[i].addedCount)), (0 <= i < cityCount) 
 */
private fun getMinCost(wantedCount: Int): Int{
    if (wantedCount <= 0) {
        return 0
    }
    if (minCostTable[wantedCount] != -1) { // 메모이제이션
        return minCostTable[wantedCount]
    }

    var minCost = Int.MAX_VALUE
    for (city in cities) {
        minCost = minOf(minCost, city.cost + getMinCost(wantedCount - city.addedCount)) 
    }

    minCostTable[wantedCount] = minCost
    return minCost
}
