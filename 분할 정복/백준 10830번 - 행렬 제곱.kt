/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/10830 행렬 제곱
 * 유형: 분할 정복, 수학(행렬)
 * 행렬의 곱셈을 해야 해서 일반 숫자를 제곱하는 분할 정복 유형의 문제보다 복잡했다.
 * 언제 재귀 함수(분할)를 호출해야 하고, 언제 행렬을 제곱하는 함수(정복)를 호출해야 할지 선택하는 부분이 어려렸다.
 * 머지 소트 알고리즘과 유사하다는 것을 지금 쓰면서 깨달았다.
 * */

import java.util.StringTokenizer

const val MOD = 1000
lateinit var originalMatrix: Array<IntArray>
lateinit var resultMatrix: Array<IntArray>
lateinit var tempMatrix: Array<IntArray>

fun main() {
    var st = StringTokenizer(readln())
    val matrixSize = st.nextToken().toInt()
    val powerSize = st.nextToken().toLong()

    originalMatrix = Array(matrixSize) {
        st = StringTokenizer(readln())
        IntArray(matrixSize) {
            st.nextToken().toInt() % MOD
        }
    }
    resultMatrix = Array(matrixSize) { IntArray(matrixSize) }
    tempMatrix = Array(matrixSize) { IntArray(matrixSize) }

    printMatrix(
        getPoweredMatrix(originalMatrix, powerSize)
    )
}

fun getPoweredMatrix(matrix: Array<IntArray>, size: Long): Array<IntArray> {
    if (size == 1L) {
        return originalMatrix
    }

    val halfPoweredMatrix = getPoweredMatrix(matrix, size / 2)
    val doubleHalfPoweredMatrix = getMultipliedMatrix(halfPoweredMatrix, halfPoweredMatrix)

    return if (size % 2 == 0L) {
        doubleHalfPoweredMatrix
    } else {
        getMultipliedMatrix(originalMatrix, doubleHalfPoweredMatrix)
    }
}

fun getMultipliedMatrix(matrix1: Array<IntArray>, matrix2: Array<IntArray>): Array<IntArray> {
    for (row in matrix1.indices) {
        for (col in matrix1.indices) {
            var result = 0
            repeat(matrix1.size) { i ->
                result += (matrix1[row][i] * matrix2[i][col]) % MOD
                result %= MOD
            }
            tempMatrix[row][col] = result
        }
    }

    copyTempToResultMatrix()
    return resultMatrix
}

fun copyTempToResultMatrix() {
    for (row in originalMatrix.indices) {
        for (col in originalMatrix.indices) {
            resultMatrix[row][col] = tempMatrix[row][col]
        }
    }
}

fun printMatrix(matrix: Array<IntArray>) {
    for (row in matrix.indices) {
        for (col in matrix.indices) {
            print("${matrix[row][col]} ")
        }
        println()
    }
}