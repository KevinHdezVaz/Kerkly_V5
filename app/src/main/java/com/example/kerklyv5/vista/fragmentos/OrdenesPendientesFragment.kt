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
import com.example.kerklyv5.controlador.AdapterOrdenPendiente
import com.example.kerklyv5.interfaces.ObtenerOrdenPendienteInterface
import com.example.kerklyv5.modelo.serial.OrdenPendiente
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
 * Use the [OrdenesPendientesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdenesPendientesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: AdapterOrdenPendiente
    private lateinit var telefono: String
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
        val v = inflater.inflate(R.layout.fragment_ordenes_pendientes, container, false)

        recyclerview = v.findViewById(R.id.recycler_ordenesPendientes)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(context)

        b = requireArguments()

        telefono = b.getString("Telefono").toString()
        Log.d("telefono", telefono)
        getOrdenes()

        return v
    }

    private fun getOrdenes () {
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
        val presupuestoGET = retrofit.create(ObtenerOrdenPendienteInterface::class.java)
        val call = presupuestoGET.ordenP(telefono)

        call?.enqueue(object : retrofit2.Callback<List<OrdenPendiente?>?> {

            override fun onResponse(
                call: Call<List<OrdenPendiente?>?>,
                response: retrofit2.Response<List<OrdenPendiente?>?>
            ) {
                val postList: ArrayList<OrdenPendiente> = response.body()
                        as ArrayList<OrdenPendiente>

                adapter = AdapterOrdenPendiente(postList)

                adapter.setOnClickListener {

                    val nombre_kerkly = postList[recyclerview.getChildAdapterPosition(it)].NombreK.trim()
                    val ap_kerkly = postList[recyclerview.getChildAdapterPosition(it)].Apellido_PaternoK.trim()
                    val id = postList[recyclerview.getChildAdapterPosition(it)].idContrato

                    val nomre_completo_kerkly = "$nombre_kerkly $ap_kerkly"

                    b = Bundle()
                    b.putString("Nombre_Kerkly", nombre_kerkly)
                    b.putString("Ap_Kerkly", ap_kerkly)
                    b.putString("Nombre_completo_Kerkly", nomre_completo_kerkly)
                    b.putInt("IdContrato", id)

                    val f = MensajesFragment()
                    f.arguments = b
                    var fm = requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
                    }

                }

                recyclerview.adapter = adapter


            }

            override fun onFailure(call: Call<List<OrdenPendiente?>?>, t: Throwable) {

                Log.d("error del retrofit", t.toString())
                Toast.makeText(requireActivity(), t.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }

   // private fun setChats(n: String, ap: String, nCompleto: String, id: Int) {
    private fun setChats(b_: Bundle) {
       val f = MensajesFragment()
       f.arguments = b_
       /* f.arguments?.putInt("IdContrato", id)
        f.arguments?.putString("Nombre Kerkly", n)
        f.arguments?.putString("Ap Kerkly", ap)
        f.arguments?.putString("Nombre completo Kerkly", nCompleto)*/
        var fm = requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }
}