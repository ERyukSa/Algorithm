/**
 * https://leetcode.com/problems/add-two-numbers/description/
 * 연결리스트 활용, 생각보다 구현을 떠올리기 어려웠다
 */

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class SolutionAddTwoNumbers {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var list1 = l1
        var list2 = l2
        val head = ListNode(-1)
        var listNode = head
        var addition = 0

        while (list1 != null && list2 != null) {
            val sum = list1.`val` + list2.`val` + addition
            addition = sum / 10

            listNode.next = ListNode(sum % 10)
            listNode = listNode.next!!
            list1 = list1.next
            list2 = list2.next
        }

        var remainList = list1 ?: list2
        while (remainList != null) {
            val sum = remainList.`val` + addition
            addition = sum / 10

            listNode.next = ListNode(sum % 10)
            listNode = listNode.next!!
            remainList = remainList.next
        }
        if (addition == 1) {
            listNode.next = ListNode(1)
        }

        return head.next
    }
}
