package com.example.kerklyv5.express

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kerklyv5.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class TranferenciaBancariaExpress : AppCompatActivity() {
    private lateinit var b: Bundle
    private lateinit var edit_nombre: TextInputEditText
    private lateinit var edit_clabe: TextInputEditText
    private lateinit var boton: MaterialButton

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tranferencia_bancaria_express)

        b = intent.extras!!

        edit_nombre = findViewById(R.id.edit_titularExpressBanco)
        edit_clabe = findViewById(R.id.edit_CLABE)
        boton = findViewById(R.id.btn_subirV)

        edit_nombre.isEnabled = false
        edit_clabe.isEnabled = false

        edit_clabe.setText("1272 6001 7084 6328 67")
        edit_nombre.setText("JOSE MANUEL ROSAS LOPEZ")

        boton.setOnClickListener {
            val i = Intent(application, ComprobantePagoExpress::class.java)
            i.putExtras(b)
            startActivity(i)
        }
    }
}