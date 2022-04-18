package com.example.kerklyv5.vista.fragmentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.MapsActivityKer
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.AdapterKerkly
import com.example.kerklyv5.interfaces.ObtenerKerklyInterface
import com.example.kerklyv5.modelo.serial.Kerkly
import com.example.kerklyv5.url.Url
import com.example.kerklyv5.vista.MapsActivity
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
 * Use the [KerklyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KerklyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var oficio: String
    private lateinit var problema: String
    private lateinit var telefono: String
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: AdapterKerkly
    private lateinit var b: Bundle

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
        val v =  inflater.inflate(R.layout.fragment_kerkly, container, false)

        recyclerview = v.findViewById(R.id.recycler_kerkly)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager= LinearLayoutManager(context)
        oficio = arguments?.getString("Oficio").toString()
        /*
        telefono = arguments?.getString("Telefono").toString()
        problema = arguments?.getString("Problema").toString()*/
        b = requireArguments()
        getOficios()

        return v
    }

    private fun getOficios () {
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
        val presupuestoGET = retrofit.create(ObtenerKerklyInterface::class.java)
        val call = presupuestoGET.kerklys(oficio)

        call?.enqueue(object : retrofit2.Callback<List<Kerkly?>?> {

            override fun onResponse(
                call: Call<List<Kerkly?>?>,
                response: retrofit2.Response<List<Kerkly?>?>
            ) {
                val postList: ArrayList<Kerkly> = response.body()
                        as ArrayList<Kerkly>

                adapter = AdapterKerkly(postList)

                adapter.setOnClickListener {
                    Log.d("curp", postList[recyclerview.getChildAdapterPosition(it)].Curp)
                    Toast.makeText(activity, postList[recyclerview.getChildAdapterPosition(it)].Curp,
                            Toast.LENGTH_SHORT).show()

                    b.putBoolean("Ker", true)

                    var i = Intent(activity, MapsActivity::class.java)
                    //b.putString("Nombre Kerkly", postList[recyclerview.getChildAdapterPosition((it))].Nombre)
                  //  b.putString("AP Kerkly", postList[recyclerview.getChildAdapterPosition((it))].Apellido_Paterno)
                   // b.putString("AM Kerkly", postList[recyclerview.getChildAdapterPosition((it))].Apellido_Materno)
                    b.putString("Curp Kerkly", postList[recyclerview.getChildAdapterPosition((it))].Curp)

                    i.putExtras(b)
                    startActivity(i)

                }

                recyclerview.adapter = adapter


            }

            override fun onFailure(call: Call<List<Kerkly?>?>, t: Throwable) {

                Log.d("error del retrofit", t.toString())
                Toast.makeText(requireActivity(), t.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }

}