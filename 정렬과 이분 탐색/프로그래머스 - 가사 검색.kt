/* https://programmers.co.kr/learn/courses/30/lessons/60060 */
/* 문자열 탐색 문제 -> 카카오 해설에서는 트라이를 사용하지만, 파라매트릭 서치로도 풀 수 있었다. 
   '?'가 뒤에서부터 시작할 수도 있기 때문에 그에 따라 정렬 순서를 달리 해야했고,
   그리고 문자열의 길이도 고려해야 했다. 이처럼 신경써야 할 것들이 많아서 어려웠다.
*/

class Solution {
    fun solution(words: Array<String>, queries: Array<String>): IntArray {
        val answer = IntArray(queries.size)
        var idx = 0
        // 각 단어들을 뒤집어서 문자열 길이와 알파벳 오름차순으로 정렬 - '?'가 앞에 있는 문자열 탐색에 사용
        val rOrderedWords = words.map{ it.reversed() }.sortedWith(compareBy<String>{it.length}.thenBy{it}).toTypedArray()
        // 문자열 길이, 알파벳 오름차순 정렬
        words.sortWith(compareBy<String>{it.length}.thenBy{it})

        for (q in queries){
            val wordList = if (q.startsWith("?")) rOrderedWords else words
            val query = if (q.startsWith("?")) q.reversed() else q 
            val left = lowerBound(wordList, query)
            // query와 매칭되는 문자열이 없는 경우
            if (left == -2) {
                continue
            }

            val right = upperBound(wordList, query)
            answer[idx++] = right - left - 1
        }

        return answer
    }

    // 매칭되는 문자열들보다 오른쪽에 있는 문자열 중 가장 왼쪽에 있는 인덱스 반환
    fun upperBound(wordList: Array<String>, query: String): Int {
        var start = 0
        var end = wordList.lastIndex
        var included = false

        while (start <= end) {
            val mid = (start + end) / 2
            val word = wordList[mid]

            if (word.length < query.length) {
                start = mid + 1
            } else if (word.length > query.length) {
                end = mid - 1
            } else {
                val result = isMatched(query, word)
                if (result > 0){
                    start = mid + 1
                } else if (result == 0) {
                    start = mid + 1
                    included = true
                } else {
                    end = mid - 1
                }
            }
        }

        return if (included) {
            start
        } else {
            -2
        }
    }

    // 매칭 문자열들보다 왼쪽 문자열들 중 가장 오른른 쪽의 인덱스 반환
    fun lowerBound(wordList: Array<String>, query: String): Int {
        var start = 0
        var end = wordList.lastIndex
        var included = false

        while (start <= end) {
            val mid = (start + end) / 2
            val word = wordList[mid]

            if (word.length < query.length) {
                start = mid + 1
            } else if (word.length > query.length) {
                end = mid - 1
            } else {
                val result = isMatched(query, word)
                if (result > 0){
                    start = mid + 1
                } else if (result == 0) {
                    end = mid - 1
                    included = true
                } else {
                    end = mid - 1
                }
            }
        }

        return if (included) {
            end
        } else {
            -2
        }
    }

    fun isMatched(query: String, word: String): Int {
        for (i in query.indices) {
            if (query[i] == '?' || query[i] == word[i]) continue
            if (query[i] > word[i]) {
                return 1
            } else {
                return -1
            }
        }

        return 0
    }
}