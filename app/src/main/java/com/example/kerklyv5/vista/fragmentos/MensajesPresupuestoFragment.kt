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
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.AdapterKerkly
import com.example.kerklyv5.controlador.AdapterMensajesNormal
import com.example.kerklyv5.express.MensajesExpress
import com.example.kerklyv5.interfaces.ObtenerKerklyInterface
import com.example.kerklyv5.interfaces.ObtenerPresupuestoNormalInterface
import com.example.kerklyv5.modelo.serial.Kerkly
import com.example.kerklyv5.modelo.serial.PresupuestoNormal
import com.example.kerklyv5.url.Url
import com.example.kerklyv5.vista.CuerpoMensajeRecibidoActivity
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MensajesPresupuestoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MensajesPresupuestoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var telefono: String
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: AdapterMensajesNormal
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
        val v = inflater.inflate(R.layout.fragment_mensajes_presupuesto, container, false)
        recyclerview = v.findViewById(R.id.recycler_mensajes_normal)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager= LinearLayoutManager(context)

        b = requireArguments()

        telefono = arguments?.getString("Telefono").toString()

        getJson()
        return v
    }

    private fun getJson() {
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
        val presupuestoGET = retrofit.create(ObtenerPresupuestoNormalInterface::class.java)
        val call = presupuestoGET.get(telefono)

        call?.enqueue(object : Callback<List<PresupuestoNormal?>?> {
            override fun onResponse(
                call: Call<List<PresupuestoNormal?>?>,
                response: Response<List<PresupuestoNormal?>?>
            ) {
                val postList: ArrayList<PresupuestoNormal> = response.body()
                        as ArrayList<PresupuestoNormal>

                adapter = AdapterMensajesNormal(postList)
                adapter.setOnClickListener{
                    /*Toast.makeText(activity,
                        postList[recyclerview.getChildAdapterPosition(it)].nombre_cliente,
                        Toast.LENGTH_SHORT).show()*/
                    val nombre = postList[recyclerview.getChildAdapterPosition(it)].nombre_cliente
                    val ap = postList[recyclerview.getChildAdapterPosition(it)].nombre_apellidoPaterno
                    val am = postList[recyclerview.getChildAdapterPosition(it)].nombre_apellidoMaterno
                    val telefonoT = postList[recyclerview.getChildAdapterPosition(it)].Telefono
                    val calle = postList[recyclerview.getChildAdapterPosition(it)].Calle
                    val colonia = postList[recyclerview.getChildAdapterPosition(it)].Colonia
                    val fecha = postList[recyclerview.getChildAdapterPosition(it)].fechaP
                    val num_ext = postList[recyclerview.getChildAdapterPosition(it)].No_Exterior
                    val problema = postList[recyclerview.getChildAdapterPosition(it)].problema
                    val folio = postList[recyclerview.getChildAdapterPosition(it)].idPresupuesto
                    val pago = postList[recyclerview.getChildAdapterPosition(it)].pago_total
                    val mensaje = postList[recyclerview.getChildAdapterPosition(it)].cuerpo_mensaje
                    var pagado = postList[recyclerview.getChildAdapterPosition(it)].estado
                    val oficio = postList[recyclerview.getChildAdapterPosition(it)].nombreO
                    val referencia = postList[recyclerview.getChildAdapterPosition(it)].Referencia
                    val cp = postList[recyclerview.getChildAdapterPosition(it)].Codigo_Postal
                    val nombreT = postList[recyclerview.getChildAdapterPosition(it)].nombreKerkly
                    val apT = postList[recyclerview.getChildAdapterPosition(it)].Apellido_Paterno
                    val amT = postList[recyclerview.getChildAdapterPosition(it)].apellidoMaterno_kerkly

                    val i = Intent(activity, CuerpoMensajeRecibidoActivity::class.java)
                    val n = "$nombre $ap $am"
                    val n2 = "$nombreT $apT $amT"

                    if (pagado !=  null) {
                        pagado = pagado.trim()
                    }

                    b.putString("Nombre", n)
                    b.putString("Telefono", telefono)
                    b.putString("Calle", calle)
                    b.putString("Colonia", colonia)
                    b.putString("Fecha", fecha)
                    b.putInt("Numero exterior", num_ext)
                    b.putString("Problema", problema)
                    b.putInt("Folio", folio)
                    b.putDouble("Pago total", pago)
                    b.putString("Oficio", oficio)
                    b.putString("Referencia", referencia)
                    b.putString("CP", cp)
                    b.putString("NombreT", n2)
                    b.putString("Pagado", pagado)

                    if (pagado == "1") {
                        b.putString("Mensaje", mensaje)
                    }

                    i.putExtras(b)

                    startActivity(i)
                }
                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<List<PresupuestoNormal?>?>, t: Throwable) {
                Log.d("error", t.toString())
            }

        }
        )
    }

}