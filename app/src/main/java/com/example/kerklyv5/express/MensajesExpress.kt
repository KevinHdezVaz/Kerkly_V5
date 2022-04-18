package com.example.kerklyv5.express

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.kerklyv5.express.FormaPagoExrpess
import com.example.kerklyv5.R
import com.example.kerklyv5.interfaces.AceptarPresupuestoInterface
import com.example.kerklyv5.modelo.Pdf
import com.example.kerklyv5.url.Url
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import java.io.BufferedReader
import retrofit.client.Response
import java.io.InputStreamReader

class MensajesExpress : AppCompatActivity() {
    private lateinit var txt_dest: TextView
    private lateinit var txt_remi: TextView
    private lateinit var txt_fecha: TextView
    private lateinit var dialog: Dialog
    private var total = 0.0
    private lateinit var boton: MaterialButton
    private var problema = "Mi problema"
    private var cliente = "José Luis López Durán"
    private var direccion = "Mi direccion"
    private var telefono = "7474747474"
    private var correo = "josem_rl@hotmail.com"
    private var folio = 0
    private lateinit var pdf_img: ImageView
    private lateinit var imgP: ImageView
    private var header: ArrayList<String> = ArrayList<String>()
    private lateinit var database: DatabaseReference
    var lista: MutableList<MutableList<String>>? = null
    private lateinit var b: Bundle
    private lateinit var pagado: String
    private lateinit var mensaje_txt: TextView



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensajes_express)

        database = Firebase.database.reference

        pdf_img = findViewById(R.id.pdf_img)
        pdf_img.setOnClickListener {
            pdf()
        }


        txt_dest = findViewById(R.id.txt_destinatario)
        txt_remi = findViewById(R.id.txt_remitente)
        txt_fecha = findViewById(R.id.txt_fechaMensaje)
        mensaje_txt = findViewById(R.id.txt_cuerpoMensaje)

        dialog = Dialog(this)

        b = intent.extras!!

        pagado = b.getString("Pagado").toString()
        Toast.makeText(this, pagado, Toast.LENGTH_SHORT).show()

        if (pagado == "1") {
            pdf_img.visibility = View.GONE
            val mensaje = b.get("Mensaje") as String
            mensaje_txt.text = mensaje
        }

        val nombre = b.get("Nombre").toString()
        txt_remi.text = "Para: $nombre"
        txt_fecha.text = b.get("Fecha").toString()


        val nT = b.getString("NombreT")

        txt_dest.text = "De: $nT"

        folio = b.getInt("Folio")
        cliente = nombre

        problema = b.getString("Problema").toString()

        val calle = b.getString("Calle")
        val num = b.getInt("Numero exterior")
        val colonia = b.getString("Colonia")
        val ref = b.getString("Referencia")
        val cp = b.getString("CP")

        var ext = ""

        if (num == 0) {
            ext = "S/N"
        } else {
            ext = num.toString()
        }

        direccion = "$calle $colonia $ext $cp $ref"

        telefono = b.getString("Telefono").toString()

        total = b.getDouble("Pago total")


        header.add("Item")
        header.add("Concepto")
        header.add("Pago")

        database.addValueEventListener(postListener)


    }

    val postListener = object:ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var coleccion = snapshot.child("Presupuesto $folio").value as MutableList<*>
            val c = coleccion[1] as HashMap<*,*>
            var l1 = ArrayList<ArrayList<String>>()
            lista = l1.toMutableList()
            lista!!.clear()


            for(i in 1 until coleccion.size) {
                var dicc = coleccion[i] as HashMap<*,*>
                var l: ArrayList<String>? = ArrayList<String>()
                var l2 = l!!.toMutableList()
                l.add(i.toString())
                l.add(dicc.get("descripcion") as String)
                l.add(dicc.get("pago") as String)
                lista?.add(l)

            }
            Log.d("coleccion", lista.toString())
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    fun pdf() {
        dialog.setContentView(R.layout.presupuesto_listo)
        dialog.show()
    }

    fun aceptar(view: View) {
       // acpetarP()
        val intent  = Intent(applicationContext, FormaPagoExrpess::class.java)
        b.putBoolean("Express", true)
        intent.putExtras(b)
        startActivity(intent)
    }


    fun rechazar(view: View) {
        dialog.dismiss()
    }

    fun generarPDF(view: View) {
        imgP = dialog.findViewById(R.id.imageViePdf)
        val p = Pdf(cliente, direccion)
        p.telefono = telefono
        p.cabecera = header
        p.correo = correo
        p.problema = problema
        p.folio = folio
        p.total = total
        p.total = total
        p.lista = lista
        p.generarPdf()

        Toast.makeText(this, "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show()

    }

}