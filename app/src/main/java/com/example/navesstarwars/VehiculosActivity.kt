package com.example.navesstarwars

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navesstarwars.databinding.ActivityVehiculosBinding
import com.example.navesstarwars.listas.Vehiculos
import com.example.navesstarwars.recycler.RecyclerVehiculosAdapter
import com.example.navesstarwars.viewmodel.ActivityVehiculosViewModel


var data: String? = ""

class VehiculosActivity :  AppCompatActivity(){
    private lateinit var binding: ActivityVehiculosBinding
    private val viewModel: ActivityVehiculosViewModel by viewModels()
    private var listaVehiculos: MutableList<Vehiculos>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url="https://swapi.dev/api/vehicles/"
        binding = ActivityVehiculosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getStringExtra("personaje")
        println(data)
        viewModel.descargarVehiculos(url)
        initObserver()
    }

    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this) { responseText ->
            showToast(responseText)
        }

        viewModel.responsePelisList.observe(this) {
            setRecycler(it as MutableList<Vehiculos>)
        }
    }

    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }

    private fun showToast(text: String) {
        Toast.makeText(this@VehiculosActivity, text, Toast.LENGTH_SHORT).show()

    }

    private fun setRecycler(lista: MutableList<Vehiculos>) {

        val adapter = RecyclerVehiculosAdapter(lista)
        adapter.setpersonaje(data!!)

        if (this.listaVehiculos == null)
            this.listaVehiculos = lista
        binding.recycler.layoutManager = LinearLayoutManager(this@VehiculosActivity)
        binding.recycler.adapter = adapter
    }
}