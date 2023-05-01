/**
 * https://www.acmicpc.net/problem/9367
 * 빡구현, 입력 정보가 많다
 */
import kotlin.math.ceil

data class Car(val name: String, val originalPrice: Int, val rentPrice: Int, val morePrice: Int)
data class Event(val time: Int, val personName: String, val type: EventType)

sealed interface EventType
data class PickUp(val carName: String) : EventType
data class Return(val distance: Int) : EventType
data class Accident(val destroyed: Int) : EventType

fun main() {
    val sb = StringBuilder()

    repeat(readln().toInt()) {
        val (carCount, eventCount) = readln().split(" ").map(String::toInt)
        val events = HashMap<String, MutableList<Event>>() // events[요원이름] = 이벤트리스트
        val cars = HashMap<String, Car>() // car[carName] = Car

        repeat(carCount) {
            val car = readln().split(" ").run {
                Car(name = this[0], originalPrice = this[1].toInt(), rentPrice=this[2].toInt(), morePrice = this[3].toInt())
            }
            cars[car.name] = car
        }

        repeat(eventCount) {
            val event = readln().split(" ").run {
                when(this[2]) {
                    "p" -> Event(time = this[0].toInt(), personName = this[1], type = PickUp(carName = this[3]))
                    "r" -> Event(time = this[0].toInt(), personName = this[1], type = Return(distance = this[3].toInt()))
                    else -> Event(time = this[0].toInt(), personName = this[1], type = Accident(destroyed = this[3].toInt()))
                }
            }
            if (events.containsKey(event.personName).not()) {
                events[event.personName] = mutableListOf()
            }
            events[event.personName]!!.add(event)
        }

        for (personName in events.keys.sorted()) {
            val eventList = events[personName]!!
            var fee = 0
            var inConsistent = false
            var car: Car? = null

            for (i in eventList.indices) {
                val event = eventList[i]

                when (event.type) {
                    is PickUp -> {
                        if (car != null) {
                            inConsistent = true
                            break
                        }
                        car = cars[event.type.carName]!!
                        fee += car.rentPrice
                    }
                    is Return -> {
                        if (car?.name == null) {
                            inConsistent = true
                            break
                        }
                        fee += event.type.distance * car.morePrice
                        car = null
                    }
                    is Accident -> {
                        if (car == null) {
                            inConsistent = true
                            break
                        }
                        fee += ceil(car.originalPrice * event.type.destroyed / 100.0).toInt()
                    }
                }
            }

            if (inConsistent || car != null) {
                sb.append("$personName INCONSISTENT\n")
            } else {
                sb.append("$personName $fee\n")
            }
        }
    }

    print(sb.toString())
}