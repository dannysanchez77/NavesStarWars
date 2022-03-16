package com.example.navesstarwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navesstarwars.databinding.ActivityMainBinding
import com.example.navesstarwars.listas.Personajes
import com.example.navesstarwars.recycler.RecyclerAdapter
import com.example.navesstarwars.viewmodel.ActivityMainViewModel

var listapersonajes: List<Personajes>? = null
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "https://swapi.dev/api/people/"
        viewModel.descargarPersonas(url)
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
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()

    }

    private fun setRecycler(lista: MutableList<Personajes>) {

        val adapter = RecyclerAdapter(lista)
        listapersonajes = lista
        binding.recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recycler.adapter = adapter
    }
}