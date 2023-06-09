package com.example.beoapp.ui.hostScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beoapp.network.ServerApi

class HostScreenViewModel(val context: Context) {

    private val _serverHostInput = MutableLiveData<String>()
    val serverHostInput: LiveData<String> = _serverHostInput

    fun setServerHostInput(serverHostInput: String) {
        _serverHostInput.value = serverHostInput
    }

    fun onServerHostError() {

    }

    fun handleSubmitButton(): Boolean {
        Log.i("HostScreenViewModel", "creating api with host ${_serverHostInput.value ?: "uwu.uwu"}")
        ServerApi.createApi(_serverHostInput.value ?: "uwu.uwu")

        return true;
    }

}