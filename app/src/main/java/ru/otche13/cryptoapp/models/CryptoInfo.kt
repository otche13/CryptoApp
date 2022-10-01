package ru.otche13.cryptoapp.models

data class CryptoInfo(
    val categories: List<String>,
    val description: Description,
    val id: String,
    val image: Image,
    val name: String,
    val symbol: String
)

data class Description(
    val en: String
)

data class Image(
    val large: String,
    val small: String,
    val thumb: String
)