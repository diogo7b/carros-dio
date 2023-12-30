package com.example.bettercars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bettercars.R
import com.example.bettercars.data.local.CarRepository
import com.example.bettercars.domain.Carro
import com.example.bettercars.ui.adapter.CarAdapter

class FavFragments : Fragment() {

    lateinit var listaCarroFavorito: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    private fun getCarsFromLocalDb(): MutableList<Carro> {
        val repository = CarRepository(requireContext())
        val carList = repository.getAllCars()
        return carList
    }

    fun setupList() {
        val carList = getCarsFromLocalDb()
        val carAdapterList = CarAdapter(carList, true)
        listaCarroFavorito.apply {
            adapter = carAdapterList
            isVisible = true
        }
        carAdapterList.carItemListener = { carro ->
            //  val isSaved = CarRepository(requireContext()).saveIfNotExist(carro)
        }
    }

    fun setupView(view: View) {
        listaCarroFavorito = view.findViewById(R.id.rv_fav_cars)
    }

}