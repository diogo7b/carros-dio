package com.example.bettercars.data.local

import android.provider.BaseColumns

object CarrosContract {


    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_CAR_ID = "car_id"
        const val COLUMN_NAME_PRECO = "preco"
        const val COLUMN_NAME_TORQUE = "torque"
        const val COLUMN_NAME_POTENCIA = "potencia"
        const val COLUMN_NAME_AUTONOMIA = "autonomia"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
    }

    const val TABLE_CAR =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_CAR_ID} TEXT," +
                "${CarEntry.COLUMN_NAME_PRECO} TEXT," +
                "${CarEntry.COLUMN_NAME_TORQUE} TEXT," +
                "${CarEntry.COLUMN_NAME_POTENCIA} TEXT," +
                "${CarEntry.COLUMN_NAME_AUTONOMIA} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}