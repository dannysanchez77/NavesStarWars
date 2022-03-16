package com.example.navesstarwars.recycler

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navesstarwars.FiltradoActivity
import com.example.navesstarwars.databinding.RecyclerlistBinding
import com.example.navesstarwars.listas.Vehiculos

class RecyclerVehiculosAdapter (var vehiculitos: MutableList<Vehiculos> ):
    RecyclerView.Adapter<RecyclerVehiculosAdapter.TextoViewHolder>()
{

    private val colorrojo = Color.RED
    private val colorverde = Color.GREEN
    private var nave: String = ""
    fun setpersonaje(nave: String) {
        this.nave = nave
    }
    class TextoViewHolder(var itemBinding: RecyclerlistBinding) :
    RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding =
            RecyclerlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, pos: Int) {

        holder.itemBinding.name.text = vehiculitos[pos].name
        if (vehiculitos[pos].pilots.contains(nave))
            holder.itemBinding.name.setTextColor(colorverde)
        else
            holder.itemBinding.name.setTextColor(colorrojo)

        holder.itemBinding.all.setOnClickListener {
            val intent =
                Intent(holder.itemBinding.root.context, FiltradoActivity::class.java)
            intent.putExtra("vehiculo", vehiculitos[pos].url)
            holder.itemBinding.root.context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return vehiculitos.size
    }

}