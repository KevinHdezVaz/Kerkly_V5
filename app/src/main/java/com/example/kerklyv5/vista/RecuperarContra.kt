package com.example.kerklyv5.vista

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.RecuperarContraControlador
import com.example.kerklyv5.modelo.ContraniaRecuper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RecuperarContra : AppCompatActivity() {

    private lateinit var contra1_edit: TextInputEditText
    private lateinit var contra2_edit: TextInputEditText
    private lateinit var layout_contra1: TextInputLayout
    private lateinit var layout_contra2: TextInputLayout
    private lateinit var contra2: String
    private lateinit var dialog: Dialog
    private lateinit var contraR: ContraniaRecuper
    private lateinit var controlador: RecuperarContraControlador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contra)

        contra1_edit = findViewById(R.id.edit_contra1Recuperar)
        contra2_edit = findViewById(R.id.edit_contra2Recuperar)
        layout_contra1 = findViewById(R.id.layout_contraseniaRecuperar1)
        layout_contra2 = findViewById(R.id.layout_contraseniaRecuperar2)
        dialog = Dialog(this)
        controlador = RecuperarContraControlador()

        val i = intent.extras
        if (i != null) {
            contraR = ContraniaRecuper(i.getString("correo").toString())
        }
    }
    fun recuperarContraDB(view: View) {
        contraR.setContrasenia(contra1_edit.text.toString())
        contra2 = contra2_edit.text.toString()
        var band = false

        if (contraR.getContrasenia().isEmpty()) {
            band = false
            layout_contra1.error = getText(R.string.campo_requerido)
        } else {
            band = true
            layout_contra1.error = null
        }

        if (contra2.isEmpty()) {
            band = false
            layout_contra2.error = getText(R.string.campo_requerido)
        } else {
            band = true
            layout_contra2.error = null
        }

        if (band) {
            if (controlador.validarContra(contraR.getContrasenia())) {
                layout_contra1.error = null
                if (contraR.getContrasenia().equals(contra2)) {
                    layout_contra2.error = null
                    controlador.modificarContra(contraR.getContrasenia(),
                        contra2,
                        contraR.getCorreo(),
                        this,
                        dialog)
                } else {
                    layout_contra2.error = getText(R.string.contras_no_coinciden)
                }
            } else {
                layout_contra1.error = getText(R.string.contra_invalida)
            }
        }
    }
}