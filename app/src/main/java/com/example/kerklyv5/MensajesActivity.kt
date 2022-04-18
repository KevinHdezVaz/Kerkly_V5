package com.example.kerklyv5

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.controlador.AdapterMensajes
import com.example.kerklyv5.express.MensajesExpress
import com.example.kerklyv5.modelo.serial.MensajesDatoss
import com.example.kerklyv5.interfaces.MensajesInterface
import com.example.kerklyv5.url.Url
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.google.gson.GsonBuilder

class MensajesActivity : AppCompatActivity() {
    lateinit var MiAdapter: AdapterMensajes
    lateinit var recyclerview: RecyclerView
    private lateinit var telefono: String
    private lateinit var b: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensajes)

        b = intent.extras!!

        telefono = b.get("Teléfono No Registrado").toString()

        recyclerview = findViewById(R.id.recycler_mensajes)
        recyclerview.setOnClickListener {

        }
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager= LinearLayoutManager(this)

        getJSON()

       // database.addValueEventListener(postListener)
    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            //val post = dataSnapshot.getValue<tablaP>()
            Toast.makeText(applicationContext, "alguien actualizo la tabla", Toast.LENGTH_LONG).show()

            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

    fun getJSON() {
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
        val presupuestoGET = retrofit.create(MensajesInterface::class.java)
        val call = presupuestoGET.EnviarT(telefono)
        call?.enqueue(object : Callback<List<MensajesDatoss?>?> {

            override fun onResponse(
                call: Call<List<MensajesDatoss?>?>,
                response: Response<List<MensajesDatoss?>?>
            ) {
                val postList: ArrayList<MensajesDatoss> = response.body()
                        as ArrayList<MensajesDatoss>



                MiAdapter = AdapterMensajes(postList)

                MiAdapter.setOnClickListener {
                    val nombre = postList[recyclerview.getChildAdapterPosition(it)].nombre_noR
                    val ap = postList[recyclerview.getChildAdapterPosition(it)].apellidoP_noR
                    val am = postList[recyclerview.getChildAdapterPosition(it)].apellidoM_noR
                    val telefonoT = postList[recyclerview.getChildAdapterPosition(it)].Telefono
                    val calle = postList[recyclerview.getChildAdapterPosition(it)].Calle
                    val colonia = postList[recyclerview.getChildAdapterPosition(it)].Colonia
                    val fecha = postList[recyclerview.getChildAdapterPosition(it)].fechaPresupuesto
                    val num_ext = postList[recyclerview.getChildAdapterPosition(it)].No_Exterior
                    val problema = postList[recyclerview.getChildAdapterPosition(it)].problema
                    val folio = postList[recyclerview.getChildAdapterPosition(it)].idPresupuestoNoRegistrado
                    val pago = postList[recyclerview.getChildAdapterPosition(it)].PagoTotal
                    val mensaje = postList[recyclerview.getChildAdapterPosition(it)].cuerpo_mensaje
                    var pagado = postList[recyclerview.getChildAdapterPosition(it)].estaPagado
                    val oficio = postList[recyclerview.getChildAdapterPosition(it)].nombreO
                    val referencia = postList[recyclerview.getChildAdapterPosition(it)].Referencia
                    val cp = postList[recyclerview.getChildAdapterPosition(it)].Codigo_Postal
                    val nombreT = postList[recyclerview.getChildAdapterPosition(it)].Nombre
                    val apT = postList[recyclerview.getChildAdapterPosition(it)].Apellido_Paterno
                    val amT = postList[recyclerview.getChildAdapterPosition(it)].Apellido_Materno

                    val n = "$nombre $ap $am"
                    val n2 = "$nombreT $apT $amT"

                    pagado = pagado.trim()

                    val i = Intent(applicationContext, MensajesExpress::class.java)

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

                recyclerview.adapter = MiAdapter
            }


            override fun onFailure(call: Call<List<MensajesDatoss?>?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Codigo de respuesta de error: $t",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("error del retrofit", t.toString())
            }

        })
    }

}