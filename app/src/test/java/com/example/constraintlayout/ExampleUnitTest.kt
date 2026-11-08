package com.example.constraintlayout

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class CurrencyConverterTest {

    private val taux = mapOf(
        "EUR" to 1.0,
        "USD" to 1.17,
        "TND" to 3.40,
        "GBP" to 0.87,
        "JPY" to 172.50,
        "CAD" to 1.62
    )

    private fun convertir(montant: Double, source: String, cible: String): Double {
        val tauxSource = taux[source] ?: 1.0
        val tauxCible = taux[cible] ?: 1.0
        return (montant / tauxSource) * tauxCible
    }

    private fun formatResult(value: Double): String {
        return String.format(Locale.US, "%.2f", value)
    }

    @Test
    fun testConversion_120_USD_to_EUR() {
        val result = convertir(120.0, "USD", "EUR")
        assertEquals("102.56", formatResult(result))
    }

    @Test
    fun testConversion_100_EUR_to_TND() {
        val result = convertir(100.0, "EUR", "TND")
        assertEquals("340.00", formatResult(result))
    }

    @Test
    fun testConversion_50_GBP_to_USD() {
        val result = convertir(50.0, "GBP", "USD")
        assertEquals("67.24", formatResult(result))
    }

    @Test
    fun testConversion_10_EUR_to_EUR() {
        val result = convertir(10.0, "EUR", "EUR")
        assertEquals("10.00", formatResult(result))
    }
}