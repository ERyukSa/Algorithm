import kotlin.math.abs
import kotlin.math.max
import java.util.*

class Solution {
    val numberList = mutableListOf<Long>()
    val operatorList = mutableListOf<Char>()
    val operateOrders = arrayOf("+-*", "+*-", "-+*", "-*+", "*+-", "*-+")
    var answer = 0L
    
    fun solution(expression: String): Long {
        parseExpression(expression)
        operateAllCases()
        return answer
    }
    
    fun operateAllCases() {
        for (opOrder in operateOrders) {
            operate(opOrder)
        }
    }
    
    fun operate(order: String) {
        val numbers = LinkedList<Long>()
        val operators = LinkedList<Char>()
        numbers.addAll(numberList)
        operators.addAll(operatorList)
        
        for (i in order.indices) {
            val opInOrder = order[i]
            val operatorSize = operators.size
            
            repeat(operatorSize) {
                val currentOp = operators.poll()
                
                if (currentOp != opInOrder) {
                    operators.offer(currentOp)
                    numbers.offer(numbers.poll())
                } else {
                    val num1 = numbers.poll()
                    val num2 = numbers.poll()
                    val result = calculate(num1, num2, currentOp)
                    numbers.offerFirst(result)
                }
            }
            
            numbers.offer(numbers.poll())
        }
        
        answer = max(answer, abs(numbers.peek()))
    }
    
    fun calculate(num1: Long, num2: Long, op: Char): Long {
        return when(op) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            else -> num1 * num2
        }
    }
    
    fun parseExpression(expression: String){
        var num = 0L
        
        for (i in expression.indices) {
            val ch = expression[i]
            
            if (ch.isDigit()) {
                num = num * 10 + (ch - '0')
            } else {
                numberList.add(num)
                num = 0
                operatorList.add(ch)
            }
        }
        
        numberList.add(num) // 마지막 숫자
    }
}