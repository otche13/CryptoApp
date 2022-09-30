package ru.otche13.cryptoapp.api

import retrofit2.Response
import retrofit2.http.GET
import ru.otche13.cryptoapp.models.CryptoResponse



interface CryptoService {

    @GET("v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&sparkline=false")
    suspend fun getCryptoListUsd(): Response<CryptoResponse>

    @GET("v3/coins/markets?vs_currency=eur&order=market_cap_desc&per_page=20&page=1&sparkline=false")
    suspend fun getCryptoListEur(): Response<CryptoResponse>

}