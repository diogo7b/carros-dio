package com.example.bettercars.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bettercars.data.local.CarrosContract.SQL_DELETE_ENTRIES
import com.example.bettercars.data.local.CarrosContract.TABLE_CAR

class CarsDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(TABLE_CAR)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(database)
    }

    override fun onDowngrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(database, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DbCar.db"
    }
}