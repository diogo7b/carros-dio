package com.example.bettercars.data

import com.example.bettercars.domain.Carro

// Esta classe está simulando uma requisição de dados externa
object CarFactory {
    val listaDeCarros = listOf<Carro>(
        Carro(
            1,
            "R$ 70.000,00",
            "43 kgfm",
            "200 cv",
            "20 km/l",
            "url",
            isFavorite = false
        ),
    )
}