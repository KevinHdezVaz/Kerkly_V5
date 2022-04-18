package com.example.kerklyv5.express

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.kerklyv5.R
import com.example.kerklyv5.SolicitarServicio
import com.example.kerklyv5.interfaces.AceptarPresupuestoInterface
import com.example.kerklyv5.interfaces.AceptarPresupuestoNormalInterface
import com.example.kerklyv5.interfaces.ActualizarMensajeInterface
import com.example.kerklyv5.url.Url
import com.google.android.material.button.MaterialButton
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.InputStreamReader

class ContratoExpress : AppCompatActivity() {
    private lateinit var kerkly: String
    private lateinit var oficio: String
    private lateinit var cliente: String
    private var pago: Double = 0.0
    private lateinit var txt_cuerpoContrato: TextView
    private lateinit var txt_intentos: TextView
    private lateinit var txt_fecha: TextView
    private lateinit var txt_folio: TextView
    private lateinit var txt_hora: TextView
    private lateinit var txt_prueba: TextView
    private var folio: Int = 0
    private lateinit var formaPago: TextView
    private lateinit var boton_dow: MaterialButton
    private lateinit var boton_inicio: MaterialButton
    private lateinit var dialog: Dialog
    private lateinit var b: Bundle
    private var intentos = 0
    private lateinit var telefono: String
    private var band = false

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrato_express)

        txt_cuerpoContrato = findViewById(R.id.textViewCuerpoContrato)
        txt_intentos = findViewById(R.id.textViewIntentoContrato)
        formaPago = findViewById(R.id.textViewFormaPagoContrato)
        txt_fecha = findViewById(R.id.textViewLugarFechaContrato)
        txt_hora = findViewById(R.id.textViewHoraContrato)
        txt_folio = findViewById(R.id.textViewFolioContrato)
        boton_dow = findViewById(R.id.button_descargsr)
        boton_inicio = findViewById(R.id.button_inicio)
        txt_prueba = findViewById(R.id.prueba_txt_contrato)
        dialog = Dialog(this)


        boton_dow.setOnClickListener {
            dialog.setContentView(R.layout.visualizar_contrato)
            dialog.show()
        }

        boton_inicio.setOnClickListener {
            var intent: Intent
            if (band) {
                intent = Intent(this, PedirServicioExpress::class.java)
            } else {
                intent = Intent(this, SolicitarServicio::class.java)
            }
            intent.putExtras(b)
            startActivity(intent)
        }


        b = intent.extras!!
        folio = b.getInt("Folio")
        intentos = b.getInt("Intentos")
        telefono = b.get("Teléfono No Registrado").toString()
        var fecha = b.getString("Fecha")

        val hora = fecha?.substring(11,16)

        fecha = getFecha(fecha!!)

        txt_fecha.text = "En Chilpancingo a $fecha"
        txt_hora.text = "Hora: $hora"

        txt_intentos.text = "Intento 0$intentos"

        kerkly = b.getString("NombreT").toString()
        oficio = b.getString("Oficio").toString()
        cliente = b.getString("Nombre").toString()
        pago = b.getDouble("Pago total")

        val fm = b.getString("Forma de Pago")

        var cadena = "El solicitante <u> $cliente\n" +
                "    </u> pagó la cantidad de <u> \$ $pago MXN</u>,\n" +
                "    por la contratación del servicio de <u> $oficio</u> bajo\n" +
                "        el cargo de <u> $kerkly</u> en la dirección\n" +
                "        registrada"

        txt_cuerpoContrato.text = Html.fromHtml(cadena, Html.FROM_HTML_MODE_LEGACY)

        cadena = "<b>Folio: </b> $folio"
        txt_folio.text = Html.fromHtml(cadena, Html.FROM_HTML_MODE_LEGACY)

        if (fm == "oxxo") {
            formaPago.text = "Forma de pago por OXXO"
        } else {
            if (fm == "transferencia") {
               formaPago.text = "Forma de pago por transferencia"
            }
        }

        intentos += 1



        if (!band) {
            txt_prueba.visibility = View.GONE
            txt_intentos.visibility = View.GONE
            aceptarNormal()
        } else {
            acpetarP()
            actualizarMensaje()
        }

    }

    private fun aceptarNormal() {
        val ROOT_URL = Url().url
        val adaptar = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api = adaptar.create(AceptarPresupuestoNormalInterface ::class.java)
        api.Aceptar(folio.toString(), "1",
                    object : Callback<Response?> {
                        override fun success(t: Response?, response: Response?) {
                            var entrada: BufferedReader? =  null
                            var Respuesta = ""
                            try {
                                entrada = BufferedReader(InputStreamReader(t?.body?.`in`()))
                                Respuesta = entrada.readLine()
                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                            Toast.makeText(applicationContext, Respuesta, Toast.LENGTH_SHORT).show()
                        }

                        override fun failure(error: RetrofitError?) {
                            Toast.makeText(applicationContext, "error $error", Toast.LENGTH_SHORT).show()
                        }

                    }
        )

    }


    private fun getFecha (s: String): String {
        val anio = s.substring(0,4)
        val dia = s.substring(8,10)
        var mes = s.substring(5,7)

        when (mes) {
            "01" -> {
                mes = "Enero"
            }

            "02" -> {
                mes = "Febrero"
            }

            "03" -> {
                mes = "Marzo"
            }

            "04" -> {
                mes = "Abirl"
            }

            "05" -> {
                mes = "Mayo"
            }

            "06" -> {
                mes = "Junio"
            }

            "07" -> {
                mes = "Julio"
            }

            "08" -> {
                mes = "Agosto"
            }

            "09" -> {
                mes = "Septiembre"
            }

            "10" -> {
                mes = "Octubre"
            }

            "11" -> {
                mes = "Noviembre"
            }

            "12" -> {
                mes = "Diciembre"
            }
        }

        return "$dia de $mes del $anio"
    }

    private fun acpetarP (){
        val ROOT_URL = Url().url

        val adaptar = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api: AceptarPresupuestoInterface = adaptar.create(AceptarPresupuestoInterface ::class.java)
        api.Aceptar(folio.toString(),
            "1",
            intentos,
            telefono,
            object : Callback<Response?> {
                override fun success(t: Response?, response: Response?) {
                    var entrada: BufferedReader? =  null
                    var Respuesta = ""
                    try {
                        entrada = BufferedReader(InputStreamReader(t?.body?.`in`()))
                        Respuesta = entrada.readLine()
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, Respuesta, Toast.LENGTH_SHORT).show()
                }

                override fun failure(error: RetrofitError?) {
                    Toast.makeText(applicationContext, "error $error", Toast.LENGTH_SHORT).show()
                }

            })

    }

    fun descargar(view: View) {
        dialog.dismiss()
    }

    private fun actualizarMensaje() {
        val url = Url().url

        val adapter = RestAdapter.Builder().setEndpoint(url).build()
        val api = adapter.create(ActualizarMensajeInterface::class.java)
        api.mensaje(folio,
            object : Callback<Response?> {
                override fun success(t: Response?, response: Response?) {
                    var entrada: BufferedReader? =  null
                    var Respuesta = ""
                    try {
                        entrada = BufferedReader(InputStreamReader(t?.body?.`in`()))
                        Respuesta = entrada.readLine()

                        Toast.makeText(applicationContext, Respuesta, Toast.LENGTH_SHORT).show()
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }

                override fun failure(error: RetrofitError?) {
                    Toast.makeText(applicationContext, "error $error" , Toast.LENGTH_SHORT).show()
                }

            }
        )
    }
}