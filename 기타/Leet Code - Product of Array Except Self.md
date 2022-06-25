## 문제
https://leetcode.com/problems/product-of-array-except-self/

## 코드1 (나눗셈 풀이)

```kotlin
class Solution {
    fun productExceptSelf(nums: IntArray): IntArray {
        var indexOfZero = - // 원소 0의 인덱스
        var product = 1     // 0을 제외한 모든 곱
        val answer = IntArray(nums.size)
        
        for (i in nums.indices) {
            // 0이 2개 이상 -> 모든 값이 0이 되기 때문에 stop
            if (nums[i] == 0 && indexOfZero != -1) {
                product = 0
                break
            } else if (nums[i] == 0 && indexOfZero == -1) { // 0을 처음 발견했을 때
                indexOfZero = i
                continue
            }
            
            product *= nums[i]
        }
        
        // 0이 하나라면, answer[indexOfZero] = 나머지 원소의 곱, 나머지는 0
        if (product != 0 && indexOfZero != -1) {
            answer[indexOfZero] = product
        } 
        // 0이 없을 때
        else if (indexOfZero == -1) {
            for (i in answer.indices) {
                answer[i] = product / nums[i]
            }
        }
        
        return answer
    }
}
```

## 코드2 (prefix 풀이)
```kotlin
/**
 * answer[i] = 왼쪽누적곱[i-1] * 오른쪽누적곱[i+1]
 */
class Solution {
    fun productExceptSelf(nums: IntArray): IntArray {
        val answer = IntArray(nums.size)
        val leftPreProducts = IntArray(nums.size)
        val rightPreProducts = IntArray(nums.size)
        
        leftPreProducts[0] = nums[0]
        rightPreProducts[nums.lastIndex] = nums.last()
        
        // 오른쪽 방향으로 누적곱
        for (i in 1 until nums.size) {
            leftPreProducts[i] = leftPreProducts[i-1] * nums[i]
        }
        // 끝에서 왼쪽 방향으로 누적곱
        for (i in nums.lastIndex - 1 downTo 0) {
            rightPreProducts[i] = rightPreProducts[i + 1] * nums[i]
        }
        
        for (i in 1 until answer.lastIndex) {
            answer[i] = leftPreProducts[i - 1] * rightPreProducts[i + 1]
        }
        answer[0] = rightPreProducts[1]
        answer[answer.lastIndex] = leftPreProducts[answer.lastIndex - 1]
        
        return answer
    }
}
```