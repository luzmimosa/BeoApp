package com.example.beoapp.ui.avaiableLevelsScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beoapp.network.ServerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvaiableLevelsViewModel(val context: Context): ViewModel() {

    private val _avaiableLevels = MutableLiveData<Array<String>>()
    val avaiableLevels: LiveData<Array<String>> = _avaiableLevels

    private val _errorVisible = MutableLiveData<Boolean>()
    val errorVisible: LiveData<Boolean> = _errorVisible

    private val _emptyListVisible = MutableLiveData<Boolean>()
    val emptyListVisible: LiveData<Boolean> = _emptyListVisible

    fun fetchAvaiableLevels() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val fetchedLevels = ServerApi.currentApi!!.getAvaiableLevels()
                    _avaiableLevels.postValue(fetchedLevels)
                    if (fetchedLevels.isEmpty()) {
                        _emptyListVisible.postValue(true)
                    } else {
                        _emptyListVisible.postValue(false)
                    }
                } catch (e: Exception) {
                    _errorVisible.postValue(true)
                }
            }
        }
    }

}