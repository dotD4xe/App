package com.example.applicationforcustomerinformation.viewmodels

import androidx.lifecycle.*
import com.example.applicationforcustomerinformation.network.BankApi
import com.example.applicationforcustomerinformation.network.ClientData
import com.example.applicationforcustomerinformation.data.Item
import com.example.applicationforcustomerinformation.data.ItemDao
import kotlinx.coroutines.launch


class OverviewViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class OverviewViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private val _data = MutableLiveData<ClientData>()
    val data: LiveData<ClientData> = _data

    private val _bin = MutableLiveData<String>()
    val bin: LiveData<String> = _bin

    fun getClientData(bin: String) {
        viewModelScope.launch {
            try {
                _bin.value = bin
                _data.value = BankApi.retrofitService.getUser(bin)
            } catch (e: Exception) {
                _data.value = ClientData(null, null,null,null,null,null,null)
            }
        }
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(bin: String): Item {
        return Item(
            bin = bin
        )
    }

    fun addNewItem(bin: String) {
        val newItem = getNewItemEntry(bin)
        insertItem(newItem)
    }
}