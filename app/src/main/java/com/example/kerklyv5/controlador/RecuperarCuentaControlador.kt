package com.example.kerklyv5.controlador

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerklyv5.vista.RecuperarContra
import com.example.kerklyv5.interfaces.RecuperarCuentaInterface
import com.example.kerklyv5.url.Url
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class RecuperarCuentaControlador {

    fun recuperarCuenta(correo: String, contexto: AppCompatActivity) {
        val Url = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(Url)
            .build()
        val api: RecuperarCuentaInterface = adapter.create(RecuperarCuentaInterface::class.java)
        api.VerficarUsuario(correo,
            object : Callback<Response?> {
                override fun success(t: Response?, response: Response?) {
                    var entrada: BufferedReader? = null
                    var Respuesta = ""
                    try {
                        entrada = BufferedReader(InputStreamReader(t?.body?.`in`()))
                        Respuesta = entrada.readLine()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    Toast.makeText(contexto, Respuesta, Toast.LENGTH_SHORT)
                        .show()
                    var Res = "correcto";
                    if (Res.equals(Respuesta)) {
                        val intent = Intent(contexto, RecuperarContra::class.java)
                        intent.putExtra("correo", correo)
                        contexto.startActivity(intent)
                        contexto.finish()
                    }
                }

                override fun failure(error: RetrofitError?) {
                    Toast.makeText(
                        contexto,
                        "error $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        )
    }

    fun valorRandom(valores: IntRange) : Int {
        val r = Random()
        val valorRandom = r.nextInt(valores.last - valores.first) + valores.first
        return valorRandom
    }
}