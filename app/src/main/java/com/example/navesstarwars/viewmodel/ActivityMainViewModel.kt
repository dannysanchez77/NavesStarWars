package com.example.navesstarwars.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navesstarwars.listas.ListaPersonajes
import com.example.navesstarwars.listas.Personajes
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class ActivityMainViewModel : ViewModel() {
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

    suspend fun setResponseListInMainThread(value: List<Personajes>) =
        withContext(Dispatchers.Main) {
            _responseList.value = value
        }


    fun descargarPersonas(url: String) {
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

                            val personas = gson.fromJson(body, ListaPersonajes::class.java)
                            println(personas)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setIsVisibleInMainThread(false)

                                setResponseListInMainThread(personas.results)

                                setResponseTextInMainThread("Hemos obtenido ${personas.results.count()} ")
                            }
                        }
                    }
                })
            }
        }
    }
}


