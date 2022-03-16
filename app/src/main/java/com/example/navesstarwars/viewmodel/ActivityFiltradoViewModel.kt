package com.example.navesstarwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navesstarwars.listapersonajes
import com.example.navesstarwars.listas.Personajes
import com.example.navesstarwars.listas.Vehiculos
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class ActivityFiltradoViewModel: ViewModel() {
    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible
    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    private val _responseList by lazy { MediatorLiveData<List<Personajes>>() }
    val responseList: LiveData<List<Personajes>>
        get() = _responseList


    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }

    suspend fun setResponseListInMainThread(value: List<Personajes>) = withContext(Dispatchers.Main) {
        _responseList.value = value
    }


    fun descargarFiltrito() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                setResponseListInMainThread(listapersonajes!!)
                setResponseTextInMainThread("Hay "+responseList.value!!.size )
                setIsVisibleInMainThread(false)
            }
        }
    }
}