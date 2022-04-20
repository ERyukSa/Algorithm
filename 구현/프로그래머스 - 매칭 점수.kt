/* https://programmers.co.kr/learn/courses/30/lessons/42893 */
/* 문자열 파싱, Map 사용 */

import java.util.*

class Solution {
    
    data class Page(var url: String = "", var id: Int = -1, var sScore: Int = 0, var linkCount: Int = 0, val linkedList: MutableList<String> = mutableListOf(), var linkScore: Double = 0.0)

    val pageMap = HashMap<String, Page>()

    fun solution(word: String, pages: Array<String>): Int {
        parse(word.lowercase(), pages)
        val answer = calculateAnswer()
        return answer
    }

    fun calculateAnswer(): Int {
        return pageMap.filterNot { it.value.id == -1 } // 입력되지 않은 링크들은 제거
            .map {(url, page) ->
                var matchingScore = page.sScore.toDouble()
                for (linkedUrl in page.linkedList) {
                    val linkedPage = pageMap[linkedUrl]!!
                    matchingScore += linkedPage.linkScore
                }
                Pair(page.id, matchingScore)
            }
            .sortedWith(compareByDescending<Pair<Int, Double>>{ it.second }.thenBy{it.first}) // 매칭 점수 내림차순, id 오름차순 정렬
            .get(0).first
    }

    fun parse(word: String, pages: Array<String>) {
        for (i in pages.indices) {
            // page url 파싱 
            val html = pages[i]
            val url = html.substringAfter("<meta property=\"og:url\" content=\"").substringBefore("\"/>")
            if (pageMap.contains(url).not()) {
                pageMap[url] = Page(url)
            }
            val page = pageMap[url]!!
            page.id = i

            // 기본 점수, 링크 파싱
            val body = html.substringAfter("<body>\n").substringBefore("</body>\n").lowercase()
            var wordIdx = body.indexOf(word)

            // 기본 점수
            while (wordIdx != -1) {
                if ((wordIdx == 0 || body[wordIdx - 1].isLetter().not()) &&
                    (wordIdx + word.length == body.length || body[wordIdx + word.length].isLetter().not())){

                    page.sScore++
                }

                wordIdx = body.indexOf(word, wordIdx + word.length)
            }

            // 링크
            var linkIdx = body.indexOf("<a href=\"")

            while(linkIdx != -1) {
                page.linkCount++

                val endIdx = body.indexOf("\">", linkIdx)
                val linkUrl = body.substring(linkIdx + 9, endIdx)
                if (pageMap.contains(linkUrl).not()) {
                    pageMap[linkUrl] = Page(linkUrl)
                }

                pageMap[linkUrl]!!.linkedList.add(url)
                linkIdx = body.indexOf("<a href=\"", endIdx)
            }
            // 링크 점수
            page.linkScore = page.sScore / page.linkCount.toDouble()
        }
    }
}