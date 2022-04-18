package com.example.kerklyv5.vista

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.RecuperarCuentaControlador
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.activation.DataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.activation.DataHandler

/*
* Obtener correo                                -listo
* Enviar email con el código de seguridad       -lsito
* Ingresar código de seguridad en el activity   -listo
*/

class RecuperarCuenta : AppCompatActivity() {
    private lateinit var editCorreo: TextInputEditText
    private lateinit var layoutCorreo: TextInputLayout
    private lateinit var session: Session
    private lateinit var context: Context
    private val correoRem: String = "josem.rl32@gmail.com"
    private val contraRem: String = "LG-V202V"
    private lateinit var correo: String
    private lateinit var barra: ProgressDialog
    private var numero: Int = 0
    private lateinit var codigoS: String
    private lateinit var editCodigo: TextInputEditText
    private lateinit var layoutCodigp: TextInputLayout
    private lateinit var controlador: RecuperarCuentaControlador


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_cuenta)

        editCorreo = findViewById(R.id.edit_correoRecuperar)
        layoutCorreo = findViewById(R.id.layout_correoRecuperar)
        editCodigo = findViewById(R.id.codigoSeguridad_edit)
        layoutCodigp = findViewById(R.id.layout_codigoSeguridad)
        controlador = RecuperarCuentaControlador()
    }

    fun enviarEmail(view: View) {
        correo = editCorreo.text.toString()

        if (correo.isEmpty()) {
            layoutCorreo.error = getText(R.string.campo_requerido)
        } else {
            layoutCorreo.error = null
            if (validarCorreo()) {
                numero = controlador.valorRandom(100000..1000000)
                codigoS = numero.toString()

                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)


                var propiedades = Properties()
                propiedades["mail.smtp.host"] = "smtp.gmail.com"
                propiedades["mail.smtp.socketFactory.port"] = "465"
                propiedades["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                propiedades["mail.smtp.auth"] = "true"
                propiedades["mail.smtp.port"] = "465"
                propiedades["mail.imap.ssl.enable"] = "true"
                propiedades["mail.smtp.socketFactory.fallback"] = "false"
                propiedades.setProperty("mail.smtp.quitwait", "false")


                session = Session.getDefaultInstance(propiedades, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(correoRem, contraRem)
                    }
                })

                barra = ProgressDialog.show(this, "", "Enviando Correo...", true)
                val task = RetreiveFeedTask()
                task.execute()
                //sendCorreoCodigo()
                layoutCorreo.error = null
            } else {
                layoutCorreo.error = getText(R.string.correo_error)
            }

        }
    }

    private fun validarCorreo():Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    private fun sendCorreoCodigo() {
        try {
            //val handler = DataHandler(ByteArrayDataSource(codigoS.byte, "Text/plain"))
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(correoRem))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo))
            message.subject = "Asunto"
            //message.setContent(" $codigoS", "text/html; charset=utf-8")
            message.setText(codigoS)
//            Toast.makeText(this, "todo bien", Toast.LENGTH_SHORT).show()
            Transport.send(message)
            //Transport.send(message, correoRem, contraRem)

        } catch (e: MessagingException) {
            Log.d("Error", e.toString())
        }

        /*var i = Intent(Intent.ACTION_SEND)
         i.putExtra(Intent.EXTRA_EMAIL, correo)
         i.putExtra(Intent.EXTRA_SUBJECT, "Asunto")
         i.putExtra(Intent.EXTRA_TEXT, codigoS)
         i.setType("message/rfc822")
         startActivity(Intent.createChooser(i,"Elije un cliente de correo"))*/

    }

    @SuppressLint("StaticFieldLeak")
    inner class RetreiveFeedTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String? {
            sendCorreoCodigo()
            return null
        }

        override fun onPostExecute(result: String?) {
            barra.dismiss()
            Toast.makeText(applicationContext, "Correo enviado", Toast.LENGTH_SHORT).show()
        }
    }

    fun enviarCodigo(view: View) {
        var band = false
        correo = editCorreo.text.toString()
        var codigo2 = editCodigo.text.toString()

        if (correo.isEmpty()) {
            layoutCorreo.error = getText(R.string.campo_requerido)
            band = false
        } else {
            layoutCorreo.error =  null
            band = true
        }

        if (codigo2.isEmpty()) {
            layoutCodigp.error = getText(R.string.campo_requerido)
            band = false
        } else {
            layoutCodigp.error = null
            band = true
        }

        //  band = validarCodigo(codigo2,codigoS)

        if (band) {
            //controlador.recuperarCuenta(correo, this)
            Toast.makeText(this,"todo bien", Toast.LENGTH_SHORT).show()
            if (validarCodigo(codigo2,codigoS)) {
                controlador.recuperarCuenta(correo, this)
            }
        }
    }

    private fun validarCodigo(c1: String, c2: String): Boolean {
        return c1.equals(c2)
    }

    fun cancelar(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}

class ByteArrayDataSource : DataSource {
    private var data: ByteArray
    private var type: String? = null

    constructor(data: ByteArray, type: String?) : super() {
        this.data = data
        this.type = type
    }

    constructor(data: ByteArray) : super() {
        this.data = data
    }

    fun setType(type: String?) {
        this.type = type
    }

    override fun getInputStream(): InputStream {
        return ByteArrayInputStream(data);
    }

    override fun getOutputStream(): OutputStream {
        throw IOException("Not Supported");
    }

    override fun getContentType(): String {
        return if (type == null) "application/octet-stream" else type!!
    }

    override fun getName(): String {
        return "ByteArrayDataSource"
    }
}

