/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/150368
 * 프로그래머스 2023 KAKAO BLIND RECRUITMENT
 * 완전 탐색(조합), 구현
 */
data class Result(val subscriberCount: Int, val totalPay: Int)

class `이모티콘 할인 행사` {

    private lateinit var emojiSaleRates: IntArray
    private lateinit var emojiPrices: IntArray
    private lateinit var users: Array<IntArray>
    private val possibleSaleRates = intArrayOf(10, 20, 30, 40)

    private var maxSubscriberCount = 0
    private var maxTotalPay = 0

    fun solution(_users: Array<IntArray>, emoticons: IntArray): IntArray {
        emojiSaleRates = IntArray(emoticons.size)
        users = _users
        emojiPrices = emoticons
        chooseSaleRates(0)

        return intArrayOf(maxSubscriberCount, maxTotalPay)
    }

    private fun chooseSaleRates(emojiIndex: Int) {
        if (emojiIndex == emojiPrices.size) {
            val (subscriberCount, totalPay) = calculateResult()
            if (subscriberCount > maxSubscriberCount) {
                maxSubscriberCount = subscriberCount
                maxTotalPay = totalPay
            } else if (subscriberCount == maxSubscriberCount && totalPay > maxTotalPay) {
                maxTotalPay = totalPay
            }
            return
        }

        for (saleRate in possibleSaleRates) {
            emojiSaleRates[emojiIndex] = saleRate
            chooseSaleRates(emojiIndex + 1)
        }
    }

    private fun calculateResult(): Result {
        var subscriberCount = 0
        var totalPay = 0

        for (user in users) {
            var userPay = 0

            for (i in emojiPrices.indices) {
                val userSaleRateCeil = user[0]
                val userPayCeil = user[1]

                if (emojiSaleRates[i] >= userSaleRateCeil) {
                    userPay += emojiPrices[i] * (100 - emojiSaleRates[i]) / 100
                    if (userPay >= userPayCeil) {
                        subscriberCount += 1
                        userPay = 0
                        break
                    }
                }
            }
            totalPay += userPay
        }

        return Result(subscriberCount, totalPay)
    }
}