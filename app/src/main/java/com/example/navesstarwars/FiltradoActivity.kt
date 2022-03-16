package com.example.navesstarwars

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navesstarwars.databinding.ActivityFiltradoBinding
import com.example.navesstarwars.listas.Personajes
import com.example.navesstarwars.recycler.RecyclerFiltradoAdapter
import com.example.navesstarwars.viewmodel.ActivityFiltradoViewModel


var dataFiltro: String? = ""
class FiltradoActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFiltradoBinding
    private val viewModel: ActivityFiltradoViewModel  by viewModels()
    private var listaFinal: MutableList<Personajes>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltradoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataFiltro = intent.getStringExtra("vehiculo")
        //println(data)
        viewModel.descargarFiltrito()
        initObserver()
    }
    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this) { responseText ->
            showToast(responseText)
        }

        viewModel.responseList.observe(this) {
            setRecycler(it as MutableList<Personajes>)
        }
    }

    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }

    private fun showToast(text: String) {
        Toast.makeText(this@FiltradoActivity, text, Toast.LENGTH_SHORT).show()

    }
    private fun setRecycler(listaPilotos: MutableList<Personajes>) {

        val adapter = RecyclerFiltradoAdapter(listaPilotos)

        adapter.setPersonaje(dataFiltro!!)
        if (this.listaFinal == null)
            this.listaFinal = listaPilotos
        binding.recycler.layoutManager = LinearLayoutManager(this@FiltradoActivity)
        binding.recycler.adapter = adapter
    }

}