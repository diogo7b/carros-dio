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
            "url"
        ),
        Carro(
            2,
            "R$ 80.000,00",
            "43 kgfm",
            "200 cv",
            "20 km/l",
            "url"
        ),
        Carro(
            3,
            "R$ 70.000,00",
            "43 kgfm",
            "135 cv",
            "20 km/l",
            "url"
        ), Carro(
            4,
            "R$ 15.000,00",
            "35 kgfm",
            "200 cv",
            "20 km/l",
            "url"
        ),
        Carro(
            5,
            "R$ 34.000,00",
            "43 kgfm",
            "200 cv",
            "23 km/l",
            "url"
        )
    )
}