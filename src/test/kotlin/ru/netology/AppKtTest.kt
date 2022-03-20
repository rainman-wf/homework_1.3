package ru.netology

import org.junit.Test

import org.junit.Assert.*

class AppKtTest {

    @Test
    fun transferTax_default() {
        val  amount = 10_000_00

        val result = transferTax(
            amount = amount
        )

        assertEquals(0, result)
    }

    @Test
    fun transferTax_Mastercard() {
        val  amount = 10_000_00
        val type = "Mastercard"

        val result = transferTax(
            amount = amount,
            transferType = type
        )

        assertEquals(0, result)
    }

    @Test
    fun transferTax_VISA() {
        val  amount = 10_000_00
        val type = "VISA"

        val result = transferTax(
            amount = amount,
            transferType = type
        )

        assertEquals(75_00, result)
    }

    @Test
    fun transferTax_Mastercard_over_action_limit() {
        val  amount = 80_000_00
        val type = "Mastercard"

        val result = transferTax(
            amount = amount,
            transferType = type
        )

        assertEquals(500_00, result)
    }

    @Test
    fun transferTax_over_day_limit() {
        val  amount = 20_000_00

        val result = transferTax(
            amount = amount
        )

        assertEquals(-2, result)
    }

    @Test
    fun transferTax_over_month_limit() {
        val  amount = 10_000_00
        val sum = 50_000_00

        val result = transferTax(
            amount = amount,
            prevTransfersSum = sum
        )

        assertEquals(-1, result)
    }

    @Test
    fun monthLimit_default() {
        val result = monthLimit()
        assertEquals(40_000_00, result)
    }

    @Test
    fun dayLimit_default() {
        val result = dayLimit()
        assertEquals(15_000_00, result)
    }

    @Test
    fun calculatedTax() {
        val minimal = 35_00
        val percent = 0.0075
        val amount = 10_000_00

        val result = calculatedTax(
            minimalTax = minimal,
            percent = percent,
            amount = amount
        )

        assertEquals(75_00, result)
    }

    @Test
    fun transactionDetails_default() {

        val amount = 10_000_00

        val expended = """
        Перевод выполнен
        Платежная система: VK Pay
        Сумма перевода: 10000 руб. 
        Комиссия: 0 руб. 

        """.trimIndent()

        val result = transactionDetails(
            amount = amount
        )

        assertEquals(expended, result)
    }

    @Test
    fun transactionDetails_over_day_limit() {

        val amount = 20_000_00

        val expended = """
            Перевод не выполнен
            Причина: превышен дневной лимит
            Платежная система: VK Pay
            
        """.trimIndent()

        val result = transactionDetails(
            amount = amount
        )

        assertEquals(expended, result)
    }

    @Test
    fun transactionDetails_over_month_limit() {

        val sum = 50_000_00
        val amount = 10_000_00

        val expended = """
            Перевод не выполнен
            Причина: превышен месячный лимит
            Платежная система: VK Pay
            
        """.trimIndent()

        val result = transactionDetails(
            amount = amount,
            prevTransfersSum = sum
        )

        assertEquals(expended, result)
    }

    @Test
    fun currencyFormat_has_not_pennies() {
        val amount = 100_00
        val result = currencyFormat(
            amount = amount
        )

        assertEquals("100 руб. ", result)
    }

    @Test
    fun currencyFormat_has_pennies() {
        val amount = 100_10
        val result = currencyFormat(
            amount = amount
        )

        assertEquals("100 руб. 10 коп.", result)
    }
}