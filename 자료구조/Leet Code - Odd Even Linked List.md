## 문제
https://leetcode.com/problems/odd-even-linked-list/

## 풀이
문제)
- 입력: 단방향 연결리스트가 주어진다.
- 요구사항: 홀수 인덱스 노드들을 앞으로, 짝수를 뒤로 보내라
- 조건: 
1. 인덱스는 1부터 시작한다
2. 각 그룹 안에서의 순서는 처음 입력 그대로 유지한다
3. 공간 복잡도: O(1) -> 새 배열을 선언하지 않고 변수 몇 개만 사용 하라는 것 같다.
4. 시간 복잡도: O(n) 


아이디어) 
- odd 연결리스트, even 연결리스트를 만들고, odd 연결리스트의 끝을 even 연결리스트 헤드에 연결한다. 

- 새로운 연결리스트의 생성은 포인터 변수(head, tail)만 새로 생성하면 된다. 
  - odd 헤드, odd 테일, even 헤드, even 테일 4개의 변수가 필요하다.

절차)
1. odd 헤드,테일이 1번째를, even 헤드,테일은 2번째 노드를 가리킨다.
2. 인덱스에 따라서 odd/even tail.next가 현재 노드를 가리키도록 바꾸고, tail도 현재 노드를 가리키도록 업데이트 한다. 
3. 반복문이 다 끝나면 각 tail.next를 null로 바꾸고, odd의 tail을 even의 head에 연결한다.

## 코드
```kotlin
/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class Solution {
    fun oddEvenList(head: ListNode?): ListNode? {
        var oddHead: ListNode? = head
        var oddTail: ListNode? = head
        var evenHead: ListNode? =  head?.next ?: null
        var evenTail: ListNode? = head?.next ?: null
        var currentNode: ListNode?  = evenTail?.next ?: null
        var idx = 3 // 현재 노드의 인덱스, 1부터 시작
      
        while (currentNode != null) {
            if (idx++ % 2 == 1) { // 홀수 인덱스
                oddTail?.next = currentNode
                oddTail = currentNode
            } else { // 짝수 인덱스
                evenTail?.next = currentNode
                evenTail = currentNode
            }
            
            currentNode = currentNode.next
        }
        
        oddTail?.next = evenHead
        evenTail?.next = null // 이걸 안해주면 노드가 홀수 개 있을 때 evenTail이 oddTail을 가리키고 있어서 연결리스트에 사이클이 생긴다.
        
        return oddHead
    }
}
```