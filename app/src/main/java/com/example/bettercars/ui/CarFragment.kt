package com.example.bettercars.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bettercars.R
import com.example.bettercars.data.CarsApi
import com.example.bettercars.data.local.CarRepository
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_AUTONOMIA
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_TORQUE
import com.example.bettercars.data.local.CarrosContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.bettercars.data.local.CarrosContract.CarEntry.TABLE_NAME
import com.example.bettercars.data.local.CarsDBHelper
import com.example.bettercars.domain.Carro
import com.example.bettercars.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    lateinit var listaCarro: RecyclerView
    lateinit var btnRecirect: FloatingActionButton
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView
    lateinit var carsApi: CarsApi
    lateinit var btnFavorite: ImageView

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
        setupRetrofit()
        setupView(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (checkConnection(context)) {
            getAllCars()
        } else {
            emptyState()
        }
    }

    fun emptyState() {
        progress.isVisible = false
        listaCarro.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }

    fun setupView(view: View) {
        listaCarro = view.findViewById(R.id.rv_info)
        btnRecirect = view.findViewById(R.id.fab_redirect)
        progress = view.findViewById(R.id.pb_loading)
        noInternetImage = view.findViewById(R.id.iv_empty_state)
        noInternetText = view.findViewById(R.id.tv_no_wifi)
    }

    fun setupListeners() {
        btnRecirect.setOnClickListener {
            startActivity(Intent(context, CalcularPrecoTotal::class.java))
        }

    }

    fun setupList(lista: List<Carro>) {
        val carAdapterList = CarAdapter(lista)
        listaCarro.apply {
            adapter = carAdapterList
            isVisible = true
        }
        carAdapterList.carItemListener = { carro ->
            val isSaved = CarRepository(requireContext()).saveIfNotExist(carro)
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

    fun setupRetrofit() {
        val builderRetrofit = Retrofit.Builder()
            .baseUrl("https://diogo7b.github.io/cars/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carsApi = builderRetrofit.create(CarsApi::class.java)
    }

    fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Carro>> {
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if (response.isSuccessful) {
                    progress.isVisible = false
                    noInternetImage.isVisible = false
                    noInternetText.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context, "Response Error", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context, "Response Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    /*    fun initServices() {
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
                    //setupList()
                } catch (e: Exception) {
                    Log.e("Error", "Error on update progress")
                }
            }
        }*/


}