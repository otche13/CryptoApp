package ru.otche13.cryptoapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import ru.otche13.cryptoapp.api.CryptoRepository
import ru.otche13.cryptoapp.models.CryptoInfo
import ru.otche13.cryptoapp.models.CryptoResponse
import ru.otche13.cryptoapp.utils.Resource
import javax.inject.Inject

 @HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository): ViewModel() {

     private val _ListLiveData = MutableLiveData<Resource<CryptoResponse>>()
     val ListLiveData: LiveData<Resource<CryptoResponse>> = _ListLiveData

     private var _InfoLiveData = MutableLiveData<Resource<CryptoInfo>>()
     val InfoLiveData: MutableLiveData<Resource<CryptoInfo>> = _InfoLiveData

     private var _CuncarencyLiveData = MutableLiveData<Boolean>()
     val CuncarencyLiveData: MutableLiveData<Boolean> = _CuncarencyLiveData

     fun changeCancarancy(usd:Boolean) {
         viewModelScope.launch {
             _CuncarencyLiveData.postValue(usd)
         }
     }




    fun getCryptolistUsd() {
        viewModelScope.launch {
            _ListLiveData.postValue(Resource.Loading())
            try {
                val response = repository.getCryptsUsd()
              _ListLiveData.postValue(Resource.Success(response.body()))

            } catch (e:Exception) {
                _ListLiveData.postValue(Resource.Error<CryptoResponse>("ошибка getCryptolistUsd()"))
            }
        }
    }

    fun getCryptolistEur() {
        viewModelScope.launch {
            _ListLiveData.postValue(Resource.Loading())
            try {
            val response = repository.getCryptsEur()
            _ListLiveData.postValue(Resource.Success(response.body()))
                    Log.i("CryptoList","$ListLiveData")
                } catch(e:Exception){
               _ListLiveData.postValue(Resource.Error(message = "ошибка getCryptolistEur()"))
            }
        }
    }

     fun getCryptoInfo(crypt:String) {

         viewModelScope.launch {
             _InfoLiveData.postValue(Resource.Loading())
             try {
                 val response = repository.getCryptoInformation(coin = crypt)
                 _InfoLiveData.postValue(Resource.Success(response.body()))
                     Log.i("CryptoList","$InfoLiveData")
                 }catch (e:Exception) {
                  _InfoLiveData.postValue(Resource.Error(message = "ошибка getCryptoInfo"))
             }
         }
     }

     fun isNetworkAvailable(context: Context?): Boolean {
         if (context == null) return false
         val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
             val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
             if (capabilities != null) {
                 when {
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                         return true
                     }
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                         return true
                     }
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                         return true
                     }
                 }
             }
         } else {
             val activeNetworkInfo = connectivityManager.activeNetworkInfo
             if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                 return true
             }
         }
         return false
     }

 }