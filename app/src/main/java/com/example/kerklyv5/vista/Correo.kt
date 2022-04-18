package com.example.kerklyv5.vista

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.Login
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern



/*
* Obtener correo y contras
* Validar los valores
* Mandar los daros a la base
*/

class Correo : AppCompatActivity() {
    private lateinit var editCorreo: TextInputEditText
    private lateinit var editContra1: TextInputEditText
    private lateinit var editContra2: TextInputEditText
    private lateinit var correo: String
    private lateinit var contra1: String
    private lateinit var contra2: String
    private val PASSWORD_PATTERN: String = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
    private lateinit var correo_contenedor: TextInputLayout
    private lateinit var nombre: String
    private lateinit var apellidoP: String
    private lateinit var apellidoM: String
    private lateinit var telefono: String
    private lateinit var genero: String
    private lateinit var contra1_contendor: TextInputLayout
    private lateinit var contra2_layot: TextInputLayout
    private lateinit var dialog: Dialog
    private lateinit var id: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correo)

        editCorreo = findViewById(R.id.edit_correo)
        editContra1 = findViewById(R.id.edit_contra1)
        editContra2 = findViewById(R.id.edit_contra2)
        correo_contenedor = findViewById(R.id.layout_correo)
        contra1_contendor = findViewById(R.id.layout_contra1)
        contra2_layot = findViewById(R.id.layout_contra2)
        dialog = Dialog(this)

        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val b = intent.extras
        nombre = b?.getString("Nombre").toString()
        apellidoP = b?.getString("Apellido Paterno").toString()
        apellidoM = b?.getString("Apellido Materno").toString()
        telefono = b?.getString("Teléfono").toString()
        genero = b?.getString("Género").toString()

        Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show()

        contra1_contendor.setOnClickListener{
            contra1_contendor.error = null
        }
    }

    private fun obtenerValores() {
        correo = editCorreo.text.toString()
        contra1 = editContra1.text.toString()
        contra2 = editContra2.text.toString()
    }

    fun sendToDB(view: View) {
        obtenerValores()
        var band = false

        if (correo.isEmpty()) {
            band = false
            correo_contenedor.error = getString(R.string.campo_requerido)
        } else {
            band = true
            correo_contenedor.error = null
        }

        if (contra1.isEmpty()) {
            band = false
            contra1_contendor.error = getString(R.string.campo_requerido)
        } else {
            band = true
            contra1_contendor.error = null
        }

        if (contra2.isEmpty()) {
            contra2_layot.error = getString(R.string.campo_requerido)
            band = false
        } else {
            contra2_layot.error = null
            band = true
        }

        if (band) {
            if (validarCorreo()) {
                correo_contenedor.error = null
                if (validarContra()) {
                    contra1_contendor.error = null
                    if (contra1.equals(contra2)) {
                        contra2_layot.error = null

                        val g = genero[0].toString()

                        var login = Login()
                        login.InsertarMysql(correo,nombre,apellidoP,apellidoM,telefono,g,
                            contra1, "0", id)

                        dialog.setContentView(R.layout.cuenta_creada)
                        dialog.show()
                    } else {
                        contra2_layot.error = getString(R.string.contras_no_coinciden)
                    }
                } else {
                    contra1_contendor.error = getString(R.string.contra_invalida)
                }
            }else {
                correo_contenedor.error = getString(R.string.correo_error)
            }

        }
    }

    private fun validarCorreo():Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    private fun validarContra(): Boolean {
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(contra1)
        return matcher.matches()
    }

    fun iniciarSesion (view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}