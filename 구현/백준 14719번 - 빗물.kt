/* https://www.acmicpc.net/problem/14719 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


lateinit var parent: IntArray

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (height, width) = readLine().split(" ").map(String::toInt)
    val blockHeights = with(StringTokenizer(readLine())) {
        IntArray(width) { nextToken().toInt() }
    }
    val blocked = Array(height) { BooleanArray(width) } // 그래프 - true: 블록, false: 빈공간
    var count = 0 // 빗물이 고이는 공간의 수

    // 블록 표시
    for (col in blockHeights.indices) {
        val h = blockHeights[col]
        for (row in 0 until h) {
            blocked[row][col] = true
        }
    }

    // 같은 행에서 블록 사이에 있는 공간들에 빗물이 고이게 된다
    for (row in 0 until height) {
        var prevCol = -1 // 같은 행에서 가장 인접한 이전 블록의 column

        for (col in 0 until width) {
            if (blocked[row][col]) {
                if (prevCol != -1) { // 첫 블록은 이전 블록이 없다
                    count += col - prevCol - 1 // 사이 공간 수
                }

                prevCol = col
            }
        }
    }

    print(count)
}