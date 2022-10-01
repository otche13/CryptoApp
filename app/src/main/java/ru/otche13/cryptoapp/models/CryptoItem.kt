package ru.otche13.cryptoapp.models

import java.io.Serializable

data class CryptoItem(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val ath_change_percentage: Double,
):Serializable