package com.example.bettercars.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bettercars.R

class CalcularPrecoTotal : AppCompatActivity() {
    private lateinit var etPrecoCombustivel: EditText
    private lateinit var etDistanciaPerc: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvVolumeCombustivel: TextView
    private lateinit var tvValorAPagar: TextView
    private lateinit var btnVoltar: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_total)
        setupView()
        setupListener()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val volumeCalculado = getVolumeSharedPref()
        val valorCalculado = getValorSharedPref()
        tvVolumeCombustivel.text = volumeCalculado
        tvValorAPagar.text = valorCalculado
    }

    private fun setupView() {
        etPrecoCombustivel = findViewById(R.id.et_preço_combustível)
        etDistanciaPerc = findViewById(R.id.et_distancia_perc)
        btnCalcular = findViewById(R.id.btn_calcular)
        tvVolumeCombustivel = findViewById(R.id.tv_vol_comb_value)
        tvValorAPagar = findViewById(R.id.tv_valor_pagar_value)
        btnVoltar = findViewById((R.id.btn_voltar))
    }

    private fun setupListener() {
        btnCalcular.setOnClickListener {
            calcularPrecoTotal()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun calcularPrecoTotal() {
        val precoCombustivel = etPrecoCombustivel.text.toString().toFloat()
        val distanciaAPercorrer = etDistanciaPerc.text.toString().toFloat()

        val volumeCombustivel = distanciaAPercorrer / 15
        val valorAPagar = precoCombustivel * volumeCombustivel

        tvVolumeCombustivel.text = volumeCombustivel.toString()
        tvValorAPagar.text = valorAPagar.toString()
        saveSharedPref(volumeCombustivel, valorAPagar)
    }

    private fun saveSharedPref(volume: Float, valor: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_volume), volume)
            putFloat(getString(R.string.saved_valor), valor)
            apply()
        }
    }

    private fun getVolumeSharedPref(): String {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_volume), 0.0f).toString()
    }

    private fun getValorSharedPref(): String {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_valor), 0.0f).toString()
    }
}