package ru.netology

const val MINUTE = 60
const val HOUR = 60 * 60
const val DAY = 60 * 60 * 24

fun main() {
    println(agoToText(50))
    println(agoToText(22 * MINUTE))
    println(agoToText(HOUR * 2))
    println(agoToText(DAY + HOUR * 10))
    println(agoToText(DAY* 2 + HOUR * 10))
    println(agoToText(4 * DAY))
}

fun agoToText (seconds: Int): String {
    return "username был(а) ${when (seconds) {
        in 0..MINUTE -> "только что"
        in MINUTE + 1.. HOUR -> "${seconds/MINUTE} ${timeUnitsEnderFormat("m", seconds/MINUTE)}"
        in HOUR + 1.. DAY -> "${seconds/HOUR} ${timeUnitsEnderFormat("h", seconds/HOUR)}"
        in DAY + 1..DAY * 2 -> "сегодня"
        in DAY * 2 + 1..DAY * 3 -> "вчера"
        else -> "давно"}
    }"
}

fun timeUnitsEnderFormat (timeUnit: String, amount: Int): String {
    return when (timeUnit) {
        "h" -> hoursEnder(amount)
        "m" -> minutesEnder(amount)
        else -> {"undefined"}
    }
}

fun hoursEnder (amount: Int): String {
    return when {
        (amount % 10 == 1 && amount % 100 != 11) -> "час"
        (amount % 10 in 2..4 && amount % 100 !in 12..14) -> "часа"
        else -> "часов"
    }
}

fun minutesEnder (amount: Int): String {
    return when {
        (amount % 10 == 1 && amount % 100 != 11) -> "минуту"
        (amount % 10 in 2..4 && amount % 100 !in 12..14) -> "минуты"
        else -> "минут"
    }
}