package com.example.kerklyv5


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class Pago : AppCompatActivity() {
    private lateinit var pago_txt: TextView
    private lateinit var edit_titular: TextInputEditText
    private lateinit var edit_tarjeta: TextInputEditText
    private lateinit var mes_edit: TextInputEditText
    private lateinit var anio_edit: TextInputEditText
    private lateinit var ccv_edit: TextInputEditText
    private lateinit var error: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)

        pago_txt = findViewById(R.id.txt_pago)
        edit_tarjeta = findViewById(R.id.edit_tarjeta)
        edit_titular = findViewById(R.id.edit_titular)
        mes_edit = findViewById(R.id.edit_mes)
        anio_edit = findViewById(R.id.edit_anio)
        ccv_edit = findViewById(R.id.edit_ccv)
    }

    fun pago (view: View) {

        if (validar()) {
            var intent = Intent(this, Contrato::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

    }

    private fun validar(): Boolean {
        var band = false
        var titular = edit_titular.text.toString()
        var tarjeta = edit_tarjeta.text.toString()
        var mes = mes_edit.text.toString()
        var anio = anio_edit.text.toString()
        var ccv = ccv_edit.text.toString()

        var aux: Int = mes.toInt()

        if (aux <= 12 && aux >= 1){
            band = true
        } else {
            error = "Mes de vencimiento inválido"
        }

        return band
    }


}