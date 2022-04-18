package com.example.kerklyv5.express

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kerklyv5.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PagoExpress : AppCompatActivity() {
    private var pago: Double = 0.0
    private lateinit var pago_Txt: TextView
    private lateinit var edit_titular: TextInputEditText
    private lateinit var edit_banco: TextInputEditText
    private lateinit var edit_tarjeta: TextInputEditText
    private lateinit var b: Bundle
    private val peso_signo: String = "$"
    private lateinit var btn: MaterialButton
    private var band = false
    private lateinit var layout: LinearLayout


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_express)

        pago_Txt = findViewById(R.id.textViewPagoExpress)
        edit_titular = findViewById(R.id.edit_titularExpress)
        btn = findViewById(R.id.subir_voucher_btn)
        edit_banco = findViewById(R.id.edit_banco)
        edit_tarjeta = findViewById(R.id.edit_tarjeta)
        layout = findViewById(R.id.layout_linear_pago)

        edit_titular.isEnabled = false
        edit_banco.isEnabled = false
        edit_tarjeta.isEnabled = false

        edit_titular.setText("JOSE MANUEL ROSAS LOPEZ")
        edit_banco.setText("BANCO AZTECA")
        edit_tarjeta.setText("5512 3823 8934 5416")

        b = intent.extras!!
        pago = b.getDouble("Pago total")
        band = b.getBoolean("Express")

        val pagoS = "$peso_signo $pago"
        pago_Txt.text = pagoS

        btn.setOnClickListener {
            val i = Intent(applicationContext, ComprobantePagoExpress::class.java)
            i.putExtras(b)
            startActivity(i)
        }

        if (!band) {
            layout.visibility = View.GONE
        }
    }
}