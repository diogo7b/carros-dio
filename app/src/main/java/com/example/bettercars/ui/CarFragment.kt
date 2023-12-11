package com.example.bettercars.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bettercars.R
import com.example.bettercars.data.CarFactory
import com.example.bettercars.domain.Carro
import com.example.bettercars.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL


class CarFragment : Fragment() {

    lateinit var lista: RecyclerView
    lateinit var btnRecirect: FloatingActionButton
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView

    var carrosArray: ArrayList<Carro> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        initServices()
        checkConnection(context)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (checkConnection(context)) {
            initServices()
        } else {
            emptyState()
        }
    }

    fun emptyState() {
        progress.isVisible = false
        lista.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }

    fun setupView(view: View) {
        lista = view.findViewById(R.id.rv_info)
        btnRecirect = view.findViewById(R.id.fab_redirect)
        progress = view.findViewById(R.id.pb_loading)
        noInternetImage = view.findViewById(R.id.iv_empty_state)
        noInternetText = view.findViewById(R.id.tv_no_wifi)
    }

    fun setupList() {
        val carAdapterList = CarAdapter(carrosArray)
        lista.apply {
            adapter = carAdapterList
            isVisible = true
        }
        // Essa opção pode ser configurada direto no xml.
        //lista.layoutManager = LinearLayoutManager(this)

    }

    fun setupListeners() {
        btnRecirect.setOnClickListener {
            startActivity(Intent(context, CalcularPrecoTotal::class.java))
        }

    }

    fun checkConnection(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATE")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATE")
            return networkInfo.isConnected
        }
    }

    fun initServices() {
        val baseUrl: String = "https://diogo7b.github.io/cars/cars.json"
        Task().execute(baseUrl)
        progress.isVisible = true
    }

    inner class Task : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null
            try {
                var urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Error", "Serviço indiponivel ... ")
                }

            } catch (ex: Exception) {
                Log.e("Error", "Error to publish")
            } finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    val torque = jsonArray.getJSONObject(i).getString("torque")
                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    val autonomia = jsonArray.getJSONObject(i).getString("autonomia")
                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")

                    val model = Carro(
                        id = id.toInt(),
                        preco = preco,
                        torque = torque,
                        potencia = potencia,
                        autonomia = autonomia,
                        urlPhoto = urlPhoto
                    )
                    carrosArray.add(model)
                }
                progress.isVisible = false
                noInternetImage.isVisible = false
                noInternetText.isVisible = false
                setupList()
            } catch (e: Exception) {
                Log.e("Error", "Error on update progress")
            }
        }
    }
}