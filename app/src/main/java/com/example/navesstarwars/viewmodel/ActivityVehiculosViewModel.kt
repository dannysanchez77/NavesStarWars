package com.example.navesstarwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navesstarwars.listas.ListaVehiculos
import com.example.navesstarwars.listas.Vehiculos
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class ActivityVehiculosViewModel : ViewModel(){
    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible
    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText


    private val _responsePelisList by lazy { MediatorLiveData<List<Vehiculos>>() }
    val responsePelisList: LiveData<List<Vehiculos>>
        get() = _responsePelisList
    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }
    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }

    suspend fun setResponsePelisListInMainThread(value: List<Vehiculos>) =
        withContext(Dispatchers.Main) {
            _responsePelisList.value = value
        }


    fun descargarVehiculos(url: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                val client = OkHttpClient()
                val request = Request.Builder()
                request.url(url)
                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                            val vehiculos = gson.fromJson(body, ListaVehiculos::class.java)

                            println(vehiculos)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setIsVisibleInMainThread(false)
                                setResponsePelisListInMainThread(vehiculos.results)
                                setResponseTextInMainThread("Hemos obtenido ${vehiculos.results.count()} ")
                            }
                        }
                    }
                })
            }
        }
    }
}