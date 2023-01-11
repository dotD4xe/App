package com.example.applicationforcustomerinformation

import com.example.applicationforcustomerinformation.network.BankApi
import com.example.applicationforcustomerinformation.network.ClientData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class BankApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BankApiStatus>()
    val status: LiveData<BankApiStatus> = _status

    //для хранение MarsPhoto обьектов
    private val _data = MutableLiveData<ClientData>()
    val data: LiveData<ClientData> = _data

    private val _bin = MutableLiveData<String>()
    val bin: LiveData<String> = _bin

    fun getClientData(bin: String) {
        viewModelScope.launch {
            _status.value = BankApiStatus.LOADING
            try {
                _bin.value = bin
                _data.value = BankApi.retrofitService.getUser(bin)
                _status.value = BankApiStatus.DONE
            } catch (e: Exception) {
                _status.value = BankApiStatus.ERROR
                _data.value = ClientData(null, null,null,null,null,null,null)
            }
        }
    }
}