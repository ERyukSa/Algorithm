/**
 * https://www.acmicpc.net/problem/25240
 * 구현, 자료구조(해시)
 */
import java.util.StringTokenizer

data class User(val name: String, val groups: Set<String>)
data class File(
    val name: String,
    val owner: String,
    val ownedGroup: String,
    val theOtherPermission: Set<Char>,
    val groupPermission: Set<Char>,
    val ownerPermission: Set<Char>,
)

private val users = HashMap<String, User>()
private val files = HashMap<String, File>()

fun main() {
    val (userCount, fileCount) = readln().split(" ").map(String::toInt)
    repeat(userCount) {
        val st = StringTokenizer(readln())
        val userName = st.nextToken()
        users[userName] = if (st.hasMoreTokens()) {
            val groups = st.nextToken().split(",").toHashSet().apply { add(userName) }
            User(name = userName, groups = groups)
        } else {
            User(name = userName, groups = hashSetOf(userName))
        }
    }

    repeat(fileCount) {
        val st = StringTokenizer(readln())
        addFiles(fileName = st.nextToken(), permissionNumber = st.nextToken(), owner = st.nextToken(), ownedGroup = st.nextToken())
    }

    val answer = StringBuilder()
    val questionCount = readln().toInt()
    repeat(questionCount) {
        val st = StringTokenizer(readln())
        val operationResult =
            canOperate(userName = st.nextToken(), fileName = st.nextToken(), operation = st.nextToken()[0])
        answer.append("${operationResult}\n")
    }
    print(answer.toString())
}

private fun addFiles(fileName: String, permissionNumber: String, owner: String, ownedGroup: String) {
    val ownerPermissionLetter = convertToPermissionLetter(permissionNumber[0])
    val groupPermissionLetter = convertToPermissionLetter(permissionNumber[1])
    val theOtherPermissionLetter = convertToPermissionLetter(permissionNumber[2])

    val theOtherPermission = theOtherPermissionLetter.toHashSet()
    val groupPermission = groupPermissionLetter.toHashSet()
    val ownerPermission = ownerPermissionLetter.toHashSet()

    files[fileName] =
        File(
            name = fileName,
            owner = owner,
            ownedGroup = ownedGroup,
            theOtherPermission = theOtherPermission,
            groupPermission = groupPermission,
            ownerPermission = ownerPermission
        )
}

private fun convertToPermissionLetter(number: Char): String {
    return when (number) {
        '1' -> "X"
        '2' -> "RW"
        '3' -> "RWX"
        '4' -> "R"
        '5' -> "RX"
        '6' -> "RW"
        '7' -> "RWX"
        else -> ""
    }
}

private fun canOperate(userName: String, fileName: String, operation: Char): Int {
    val user = users[userName]!!
    val file = files[fileName]!!
    val userPermission = when {
        file.owner == user.name -> file.ownerPermission
        file.ownedGroup in user.groups -> file.groupPermission
        else -> file.theOtherPermission
    }
    return if (userPermission.contains(operation)) 1 else 0
}