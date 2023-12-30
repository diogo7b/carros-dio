package com.example.bettercars.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_AUTONOMIA
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_TORQUE
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.bettercars.domain.Carro

class CarRepository(private val context: Context) {

    fun save(carro: Carro): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDBHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(COLUMN_NAME_PRECO, carro.preco)
                put(COLUMN_NAME_TORQUE, carro.torque)
                put(COLUMN_NAME_POTENCIA, carro.potencia)
                put(COLUMN_NAME_AUTONOMIA, carro.autonomia)
                put(COLUMN_NAME_URL_PHOTO, carro.urlPhoto)
            }

            val inserted = db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)
            if (inserted != null) {
                isSaved = true
            }
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e("Error db", it)
            }
        }
        return isSaved

    }

    fun findCarById(id: Int): Carro {
        val dbHelper = CarsDBHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_TORQUE,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_AUTONOMIA,
            COLUMN_NAME_URL_PHOTO
        )
        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())
        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //nome da tabela
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )
        var itemID: Long = 0
        var preco: String = ""
        var torque: String = ""
        var potencia: String = ""
        var autonomia: String = ""
        var urlPhoto: String = ""

        with(cursor) {
            while (moveToNext()) {
                itemID = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                Log.d("ID ->", itemID.toString())

                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("preço ->", preco)

                torque = getString(getColumnIndexOrThrow(COLUMN_NAME_TORQUE))
                Log.d("preço ->", preco)

                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                Log.d("preço ->", preco)

                autonomia = getString(getColumnIndexOrThrow(COLUMN_NAME_AUTONOMIA))
                Log.d("preço ->", preco)

                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("preço ->", preco)

            }

        }
        cursor.close()
        return Carro(
            id = itemID.toInt(),
            preco = preco,
            torque = torque,
            potencia = potencia,
            autonomia = autonomia,
            urlPhoto = urlPhoto,
            isFavorite = true
        )
    }

    fun saveIfNotExist(carro: Carro) {
        val car = findCarById(carro.id)
        if (car.id == ID_WHEN_NO_CAR) {
            save(carro)
        }
    }

    fun getAllCars(): MutableList<Carro> {

        val dbHelper = CarsDBHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_TORQUE,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_AUTONOMIA,
            COLUMN_NAME_URL_PHOTO
        )

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //nome da tabela
            columns,
            null,
            null,
            null,
            null,
            null
        )
        var itemID: Long = 0
        var preco: String = ""
        var torque: String = ""
        var potencia: String = ""
        var autonomia: String = ""
        var urlPhoto: String = ""
        val carros = mutableListOf<Carro>()
        with(cursor) {
            while (moveToNext()) {
                itemID = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                Log.d("ID ->", itemID.toString())

                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("preço ->", preco)

                torque = getString(getColumnIndexOrThrow(COLUMN_NAME_TORQUE))
                Log.d("torque ->", torque)

                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                Log.d("potencia ->", potencia)

                autonomia = getString(getColumnIndexOrThrow(COLUMN_NAME_AUTONOMIA))
                Log.d("autonomia ->", autonomia)

                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("urlPhoto ->", urlPhoto)
                carros.add(
                    Carro(
                        id = itemID.toInt(),
                        preco = preco,
                        torque = torque,
                        potencia = potencia,
                        autonomia = autonomia,
                        urlPhoto = urlPhoto,
                        isFavorite = true
                    )
                )
            }

        }
        cursor.close()
        return carros
    }
    companion object{
        const val ID_WHEN_NO_CAR = 0
    }
}


