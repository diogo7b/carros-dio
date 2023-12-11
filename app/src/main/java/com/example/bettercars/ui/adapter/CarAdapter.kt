package com.example.bettercars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bettercars.R
import com.example.bettercars.domain.Carro

class CarAdapter(private val carros: List<Carro>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

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
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val preco: TextView
        val torque: TextView
        val potencia: TextView
        val autonomia: TextView

        init {
            view.apply {
                preco = findViewById(R.id.tv_preco_value)
                torque = findViewById(R.id.tv_torque_value)
                potencia = findViewById(R.id.tv_potencia_value)
                autonomia = findViewById(R.id.tv_autonomia_value)
            }

        }
    }

}