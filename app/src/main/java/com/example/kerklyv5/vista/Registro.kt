package com.example.kerklyv5.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import com.example.kerklyv5.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/*
* Obtener y validar valores
* Mandarlos al siguiente activity
* */
class Registro : AppCompatActivity() {
    private lateinit var editNombre: TextInputEditText
    private lateinit var editApellidoP: TextInputEditText
    private lateinit var editApellidoM: TextInputEditText
    private lateinit var editTelefono: TextInputEditText
    private lateinit var spinner: Spinner
    private lateinit var layout_nombre: TextInputLayout
    private lateinit var layout_Ap: TextInputLayout
    private lateinit var layout_Am: TextInputLayout
    private lateinit var layout_telefono: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        editNombre = findViewById(R.id.edit_nombre)
        editApellidoP = findViewById(R.id.edit_apellidoP)
        editApellidoM = findViewById(R.id.edit_apellidpM)
        editTelefono = findViewById(R.id.edit_telefono)
        spinner = findViewById(R.id.spinnerRecuperarcuenta)
        layout_nombre = findViewById(R.id.layoutNombre)
        layout_Ap = findViewById(R.id.layoutAp)
        layout_Am = findViewById(R.id.layoutAm)
        layout_telefono = findViewById(R.id.layoutTelefono)
    }


    fun clickContinuarRegistro (view: View) {
        var band = false
        var nombre: String = editNombre.text.toString()
        var apellidoP: String = editApellidoP.text.toString()
        var apellidoM: String = editApellidoM.text.toString()
        var telefono: String = editTelefono.text.toString()
        var genero: String = spinner.selectedItem.toString()

        if (nombre.isEmpty()) {
            layout_nombre.error = getText(R.string.campo_requerido)
            band = true
        } else {
            band = false
            layout_nombre.error = null
        }

        if (apellidoP.isEmpty()) {
            layout_Ap.error = getText(R.string.campo_requerido)
            band = true
        } else {
            band = false
            layout_Ap.error = null
        }

        if (apellidoM.isEmpty()) {
            layout_Am.error = getText(R.string.campo_requerido)
            band = true
        } else {
            band = false
            layout_Am.error = null
        }

        if (telefono.isEmpty()) {
            layout_telefono.error = getText(R.string.campo_requerido)
            band = true
        } else {
            band = false
            layout_telefono.error = null
            if (telefono.length != 10) {
                layout_telefono.error = getText(R.string.telefono_error)
                band = true
            } else {
                band = false
                layout_telefono.error = null
            }
        }

        if (!band) {
            val bundle = Bundle()

            bundle.putString("Nombre", nombre)
            bundle.putString("Apellido Paterno", apellidoP)
            bundle.putString("Apellido Materno", apellidoM)
            bundle.putString("Teléfono", telefono)
            bundle.putString("Género", genero)

            val intent = Intent(this, Correo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }


    }
}