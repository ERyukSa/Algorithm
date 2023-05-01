/**
 * https://www.acmicpc.net/problem/2792
 * 이분 탐색, 적용 방법을 떠올리는 것이 어려웠음
 */
private var studentCount = 0
private lateinit var jewelCounts: IntArray

fun main() {
    val (_studentCount, colorCount) = readln().split(" ").map(String::toInt)
    studentCount = _studentCount
    jewelCounts = IntArray(colorCount) { readln().toInt() }
    jewelCounts.sortDescending()

    var start = 1
    var end = jewelCounts[0]

    while (start <= end) {
        val mid = (start + end) / 2

        // 모든 보석을 [mid]개로 나눴을 때 나뉜 (꾸러미) 개수가 학생 수보다 작거나 같으면,
        // 더 작은 개수로 나눌 수 있는지 확인한다
        if (canDistribute(jealousy = mid)) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    print(end + 1)
}

private fun canDistribute(jealousy: Int): Boolean {
    var splitCount = 0 // 학생마다 [jealousy]개를 나눠줄 때 나오는 보석 꾸러미 개수

    for (jewelCnt in jewelCounts) {
        if (splitCount > studentCount) {
            break
        }

        splitCount += jewelCnt / jealousy
        if (jewelCnt % jealousy != 0) { // [jealousy]개가 안되면 어쩔 수 없이 남은 보석만 준다
            splitCount += 1
        }
    }

    return splitCount <= studentCount
}