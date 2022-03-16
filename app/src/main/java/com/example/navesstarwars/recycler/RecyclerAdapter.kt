package com.example.navesstarwars.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.navesstarwars.VehiculosActivity
import com.example.navesstarwars.databinding.RecyclerlistBinding
import com.example.navesstarwars.listas.Personajes


class RecyclerAdapter(var personas: MutableList<Personajes>) :
    RecyclerView.Adapter<RecyclerAdapter.TextoViewHolder>() {


    class TextoViewHolder(var itemBinding: RecyclerlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding =
            RecyclerlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, pos: Int) {

        holder.itemBinding.name.text = personas[pos].name
        holder.itemBinding.all.setOnClickListener {
            val intent = Intent(holder.itemBinding.root.context, VehiculosActivity::class.java)
            intent.putExtra("personaje", personas[pos].url)
            holder.itemBinding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return personas.size
    }

}