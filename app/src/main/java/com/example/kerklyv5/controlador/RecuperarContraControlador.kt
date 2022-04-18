package com.example.kerklyv5.controlador

import android.app.Dialog
import android.app.ProgressDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerklyv5.R
import com.example.kerklyv5.url.Url
import com.example.kerklyv5.interfaces.ModificarContrasenaInterface
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern

class RecuperarContraControlador {
    private val PASSWORD_PATTERN: String = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

    fun validarContra(contra: String): Boolean {
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(contra)
        return matcher.matches()
    }

    fun modificarContra(contra1: String,
                        contra2: String,
                        correo: String,
                        contexto: AppCompatActivity,
                        dialog: Dialog
    ) {
        //primero validamos que la contraseña sean igual
        if (contra1.equals(contra2)) {
            val Url = Url().url
            val adapter = RestAdapter.Builder()
                .setEndpoint(Url)
                .build()
            val api: ModificarContrasenaInterface = adapter.create(ModificarContrasenaInterface::class.java)
            api.VerficarUsuario(correo,
                contra1,

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

                        Toast.makeText(contexto, Respuesta, Toast.LENGTH_SHORT).show()
                        val Res = "Contraseña restablecida";

                        if (Res.equals(Respuesta)) {
                            dialog.setContentView(R.layout.contrasenia_modificada)
                            dialog.show()
                        }
                    }

                    override fun failure(error: RetrofitError?) {
                        Toast.makeText(
                            contexto,
                            "error $error" ,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            )
        } else{
            Toast.makeText(contexto, "La contraseña no son iguales", Toast.LENGTH_SHORT).show();
        }

    }
}