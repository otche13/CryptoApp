package ru.otche13.cryptoapp.ui


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otche13.cryptoapp.api.CryptoRepository
import ru.otche13.cryptoapp.models.CryptoResponse
import ru.otche13.cryptoapp.utils.Resource
import javax.inject.Inject

 @HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository): ViewModel() {

    val ListLiveData: MutableLiveData<Resource<CryptoResponse>> = MutableLiveData()

    fun getCryptolistUsd() {
        viewModelScope.launch {
            ListLiveData.postValue(Resource.Loading())
            val response = repository.getCryptsUsd()
            if (response.isSuccessful) {
                response.body().let { result ->
                    ListLiveData.postValue(Resource.Success(result))
                    Log.i("Crypto","$ListLiveData")
                }
            } else {
                ListLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

    fun getCryptolistEur() {
        viewModelScope.launch {
            ListLiveData.postValue(Resource.Loading())
            val response = repository.getCryptsEur()
            if (response.isSuccessful) {
                response.body().let { result ->
                    ListLiveData.postValue(Resource.Success(result))
                    Log.i("CryptoList","$ListLiveData")
                }
            } else {
                ListLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

 }