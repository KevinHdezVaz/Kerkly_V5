package com.example.kerklyv5.vista.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.AdapterHistorial
import com.example.kerklyv5.interfaces.ObtenerHistorialInterface
import com.example.kerklyv5.modelo.serial.Historial
import com.example.kerklyv5.url.Url
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistorialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistorialFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: AdapterHistorial
    private lateinit var telefono: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_historial, container, false)

        recyclerview = v.findViewById(R.id.recycler_historial)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(context)
        telefono = arguments?.getString("Telefono").toString()

        getHistorial()

        return v
    }

    private fun getHistorial () {
        val ROOT_URL = Url().url
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("$ROOT_URL/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val presupuestoGET = retrofit.create(ObtenerHistorialInterface::class.java)
        val call = presupuestoGET.historia(telefono)

        call?.enqueue(object : retrofit2.Callback<List<Historial?>?> {

            override fun onResponse(
                call: Call<List<Historial?>?>,
                response: retrofit2.Response<List<Historial?>?>
            ) {
                val postList: ArrayList<Historial> = response.body()
                        as ArrayList<Historial>

                adapter = AdapterHistorial(postList)

                recyclerview.adapter = adapter


            }

            override fun onFailure(call: Call<List<Historial?>?>, t: Throwable) {

                Log.d("error del retrofit", t.toString())
                Toast.makeText(requireActivity(), t.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }
}