package com.example.kerklyv5.controlador

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerklyv5.SolicitarServicio
import com.example.kerklyv5.express.PedirServicioExpress
import com.example.kerklyv5.modelo.Cliente
import com.example.kerklyv5.R
import com.example.kerklyv5.interfaces.*
import com.example.kerklyv5.url.Url
import com.example.kerklyv5.vista.MainActivity
import com.google.android.material.textfield.TextInputLayout
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivityControlador {
    private var b = Bundle()
    private lateinit var nombre: String
    private lateinit var apellidoP: String
    private lateinit var apellidoM: String
    private var intentos = 1
    private lateinit var tel: String

    fun verficiarUsuario(usuario: Cliente, contexto: AppCompatActivity) {
        val Url = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(Url)
            .build()
        val api: LoginInterface = adapter.create(LoginInterface::class.java)
        api.VerficarUsuario(usuario.getUsuario(),
            usuario.getContra(),
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
                    var Res = "Bienvenido";
                    if (Res.equals(Respuesta)){
                        val  intent = Intent(contexto, SolicitarServicio::class.java)
                        intent.putExtra("Telefono", usuario.getUsuario())
                        contexto.startActivity(intent)
                        contexto.finish()


                    }
                    Toast.makeText(contexto, Respuesta, Toast.LENGTH_SHORT).show()

                }

                override fun failure(error: RetrofitError) {
                    Toast.makeText(contexto, "error $error" , Toast.LENGTH_SHORT).show()
                }

            }
        )
    }


    fun pruebaRegistrarNumero(usuario: Cliente, contexto: Context, layoutTelefono: TextInputLayout) {
        val ROOT_URL = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api: EntrarSinRegistroInterface = adapter.create(EntrarSinRegistroInterface::class.java)
        api.sinRegistro(usuario.getTelefonoNoR(),
            object : Callback<Response?> {
                override fun success(t: Response?, response: Response?) {
                    var salida: BufferedReader? = null
                    var entrada = ""
                    try {
                        salida = BufferedReader(InputStreamReader(t?.body?.`in`()))
                        entrada = salida.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    //  Toast.makeText(this@MainActivity, entrada, Toast.LENGTH_SHORT).show()
                    var cadena: String = "El número ya existe"
                    if (cadena == entrada) {
                        layoutTelefono.error = contexto.getText(R.string.numeroRegistrado_error)
                        return
                    } else {
                        if (entrada == usuario.getTelefonoNoR()) {
                            layoutTelefono.error = null
                            val i = Intent(contexto, PedirServicioExpress::class.java)


                            b.putString("Teléfono No Registrado", usuario.getTelefonoNoR())


                            i.putExtras(b)
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            contexto.startActivity(i)
                        } else {
                            Toast.makeText(contexto, "Error de registro", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun failure(error: RetrofitError?) {
                    println("error $error")

                }

            })
    }

    @SuppressLint("HardwareIds")
    fun verificarNumeroTelNoR(contexto: AppCompatActivity): Boolean {
        var band = false
        var usuario = Cliente(Settings.Secure.getString(contexto.contentResolver, Settings.Secure.ANDROID_ID))

        return band
    }

    fun verificarSesion(id: String, contexto: AppCompatActivity) {
        val ROOT_URL = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api = adapter.create(VerificarSesionInterface::class.java)
        api.sesionAbierta(
            id,
            object : retrofit.Callback<retrofit.client.Response?> {
                override fun success(t: retrofit.client.Response?, response2: retrofit.client.Response?) {
                    var reader: BufferedReader? = null
                    var output = ""
                    try {
                        reader = BufferedReader(InputStreamReader(t?.body?.`in`()))

                        output = reader.readLine()


                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    if(output == "0") {
                        val  intent = Intent(contexto, MainActivity::class.java)
                        intent.putExtra("Telefono", output)
                        contexto.startActivity(intent)
                        contexto.finish()
                    } else {
                        val  intent = Intent(contexto, SolicitarServicio::class.java)
                        intent.putExtra("Telefono", output)
                        contexto.startActivity(intent)
                        contexto.finish()
                    }

                }

                override fun failure(error: RetrofitError) {
                    println("error $error")
                }

            }
        )
    }

}