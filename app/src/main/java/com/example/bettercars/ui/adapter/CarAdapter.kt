package com.example.bettercars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bettercars.R
import com.example.bettercars.domain.Carro

class CarAdapter(private val carros: List<Carro>, private val isFavoriteScreen: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Carro) -> Unit = {}

    // Cria uma nova View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = carros.size

    // Pega o conteudo da View e troca pelo item da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.torque.text = carros[position].torque
        holder.potencia.text = carros[position].potencia
        holder.autonomia.text = carros[position].autonomia
        if (isFavoriteScreen) {
            holder.favorite.setImageResource(R.drawable.ic_star_full)
        }
        holder.favorite.setOnClickListener {
            val carro = carros[position]
            carItemListener(carro)
            setupFavorite(carro, holder)
        }
    }

    private fun setupFavorite(
        carro: Carro,
        holder: ViewHolder
    ) {
        carro.isFavorite = !carro.isFavorite

        if (carro.isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_star_full)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_star)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val preco: TextView
        val torque: TextView
        val potencia: TextView
        val autonomia: TextView
        val favorite: ImageView

        init {
            view.apply {
                preco = findViewById(R.id.tv_preco_value)
                torque = findViewById(R.id.tv_torque_value)
                potencia = findViewById(R.id.tv_potencia_value)
                autonomia = findViewById(R.id.tv_autonomia_value)
                favorite = findViewById(R.id.iv_favorite)
            }

        }
    }

}