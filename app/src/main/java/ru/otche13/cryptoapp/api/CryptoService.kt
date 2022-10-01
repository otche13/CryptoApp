package ru.otche13.cryptoapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.otche13.cryptoapp.models.CryptoInfo
import ru.otche13.cryptoapp.models.CryptoResponse
import ru.otche13.cryptoapp.utils.Constans


interface CryptoService {

    @GET("v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&sparkline=false")
    suspend fun getCryptoListUsd(): Response<CryptoResponse>

    @GET("v3/coins/markets?vs_currency=eur&order=market_cap_desc&per_page=20&page=1&sparkline=false")
    suspend fun getCryptoListEur(): Response<CryptoResponse>

    @GET("v3/coins/{coin}")
    suspend fun getCryptoInformation(
        @Path("coin") coin:String,
        @Query("apikey") apikey: String = Constans.API_KEY
    ):Response<CryptoInfo>

}