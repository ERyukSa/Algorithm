/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/118669
 * 예외처리 안할 수 있도록 그래프 커스터마이징, 다익스트라 응용 (Hard)
 */
import java.util.PriorityQueue

class `등산코스 정하기` {

    data class Path(val to: Int, val time: Int)
    data class Route(val place: Int, val intensity: Int)

    fun solution(n: Int, paths: Array<IntArray>, gates: IntArray, summits: IntArray): IntArray {
        val graph: Array<MutableList<Path>> = getGraph(n, paths, gates, summits)
        val minIntensityTable: IntArray = getMinIntensityTableByDijkstra(graph, gates)

        val minSummitAndIntensity = intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE)
        summits.sort()
        summits.forEach {
            if (minSummitAndIntensity[1] > minIntensityTable[it]) {
                minSummitAndIntensity[0] = it
                minSummitAndIntensity[1] = minIntensityTable[it]
            }
        }

        return minSummitAndIntensity
    }

    private fun getGraph(
        placeCount: Int,
        paths: Array<IntArray>,
        gates: IntArray,
        summits: IntArray
    ): Array<MutableList<Path>> {
        val graph = Array(placeCount + 1) { mutableListOf<Path>() }
        val gateSet = gates.toHashSet()
        val summitSet = summits.toHashSet()

        paths.forEach {
            val place1 = it[0]
            val place2 = it[1]
            val time = it[2]

            if (place1 !in summitSet && place2 !in gateSet) {
                graph[place1].add(Path(place2, time))
            }
            if (place2 !in summitSet && place1 !in gateSet) {
                graph[place2].add(Path(place1, time))
            }
        }

        return graph
    }

    private fun getMinIntensityTableByDijkstra(graph: Array<MutableList<Path>>, gates: IntArray): IntArray {
        val minIntensityTable = IntArray(graph.size + 1) { Int.MAX_VALUE }
        val q = PriorityQueue<Route>(compareBy { it.intensity })
        gates.forEach {
            q.offer(Route(it, 0))
        }

        while (q.isNotEmpty()) {
            val (place, intensity) = q.poll()
            if (intensity > minIntensityTable[place]) {
                continue
            }

            for ((nextPlace, nextTime) in graph[place]) {
                val nextIntensity = maxOf(intensity, nextTime)
                if (nextIntensity < minIntensityTable[nextPlace]) {
                    minIntensityTable[nextPlace] = nextIntensity
                    q.offer(Route(nextPlace, nextIntensity))
                }
            }
        }

        return minIntensityTable
    }
}