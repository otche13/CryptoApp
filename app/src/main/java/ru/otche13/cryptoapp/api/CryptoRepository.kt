package ru.otche13.cryptoapp.api

import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoService: CryptoService
        ){

    suspend fun getCryptsUsd() =cryptoService.getCryptoListUsd()

    suspend fun getCryptsEur()= cryptoService.getCryptoListEur()

    suspend fun getCryptoInformation(coin:String) =cryptoService.getCryptoInformation(coin)



}