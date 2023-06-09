package com.example.beoapp.ui.levelScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beoapp.network.ServerApi
import com.example.beoapp.network.model.CustomStatRecord
import kotlinx.coroutines.launch

class LevelScreenViewModel(val level: String) : ViewModel() {

    private val _statRecords = MutableLiveData<Array<CustomStatRecord>>()
    val statRecords: LiveData<Array<CustomStatRecord>> = _statRecords

    private val _errorVisible = MutableLiveData<Boolean>()
    val errorVisible: LiveData<Boolean> = _errorVisible

    private val _selectedStat = MutableLiveData<CustomStatRecord>()
    val selectedStat: LiveData<CustomStatRecord> = _selectedStat

    fun fetchStatRecords() {
        viewModelScope.launch {
            try {
                val fetchedRecords = ServerApi.currentApi?.getRecordsOf(level) ?: throw Exception("ServerApi is null")
                Log.i("LevelScreenViewModel", "fetchedRecords: ${fetchedRecords.size}")
                _statRecords.postValue(fetchedRecords)
            } catch (e: Exception) {
                _errorVisible.postValue(true)
            }
        }
    }

    fun handleStatPress(stat: CustomStatRecord) {
        this._selectedStat.value = stat
    }

    fun resetSelectedStat() {
        _selectedStat.value = null
    }

}