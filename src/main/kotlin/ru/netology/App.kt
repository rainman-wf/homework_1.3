package ru.netology

import kotlin.math.roundToInt

const val OVER_MONTH_LIMIT_CODE = -1
const val OVER_DAY_LIMIT_CODE = -2

fun main() {
    println(transactionDetails(amount = 14_351_00))
    println(transactionDetails("Mastercard" , 600_001_00, 14_351_00))
    println(transactionDetails("VISA" , amount = 14_351_00))
}

fun transferTax(transferType: String = "VK Pay", prevTransfersSum: Int = 0, amount: Int): Int {
    return when {
        (amount > dayLimit(transferType)) -> OVER_DAY_LIMIT_CODE
        (prevTransfersSum > monthLimit(transferType)) -> OVER_MONTH_LIMIT_CODE
        else -> {
            when (transferType) {
                "Mastercard", "Maestro" -> if (amount <= 75_000_00) 0 else (amount * 0.006).roundToInt() + 20_00
                "VISA", "MIR" -> calculatedTax(35_00, 0.0075, amount)
                else -> 0
            }
        }
    }
}

fun monthLimit(transferType: String = "VK Pay") = if (transferType == "VK Pay") 40_000_00 else 600_000_00

fun dayLimit(transferType: String = "VK Pay") = if (transferType == "VK Pay") 15_000_00 else 150_000_00

fun calculatedTax(minimalTax: Int, percent: Double, amount: Int): Int {
    val percentTax = (amount * percent).roundToInt()
    return percentTax.coerceAtLeast(minimalTax)
}

fun transactionDetails(transferType: String = "VK Pay", prevTransfersSum: Int = 0, amount: Int): String {
    return when (val tax = transferTax(transferType, prevTransfersSum, amount)) {
        OVER_MONTH_LIMIT_CODE -> """
            Перевод не выполнен
            Причина: превышен месячный лимит
            Платежная система: $transferType
            
        """.trimIndent()
        OVER_DAY_LIMIT_CODE -> """
            Перевод не выполнен
            Причина: превышен дневной лимит
            Платежная система: $transferType
            
        """.trimIndent()
        else -> """
            Перевод выполнен
            Платежная система: $transferType
            Сумма перевода: ${currencyFormat(amount)}
            Комиссия: ${currencyFormat(tax)}
            
    """.trimIndent()
    }
}

fun currencyFormat(amount: Int): String {
    val rubles = amount / 100
    val pennies = amount % 100
    return "$rubles руб. " +
            if (pennies != 0) "${"%02d".format(pennies)} коп." else ""
}

