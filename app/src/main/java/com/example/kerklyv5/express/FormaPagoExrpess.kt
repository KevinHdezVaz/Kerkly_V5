package com.example.kerklyv5.express

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.kerklyv5.R
import com.example.kerklyv5.interfaces.AceptarPresupuestoInterface
import com.example.kerklyv5.url.Url
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.InputStreamReader

class FormaPagoExrpess : AppCompatActivity() {
    private lateinit var b: Bundle
    private lateinit var constraint: ConstraintLayout
    private lateinit var constraint2: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_pago_exrpess)

        b = intent.extras!!
        constraint = findViewById(R.id.oxxo_constraint)
        constraint2 = findViewById(R.id.constraint_transferencia)

        constraint.setOnClickListener {
            val intent = Intent(applicationContext, PagoExpress::class.java)
            b.putString("Forma de Pago", "oxxo")
            intent.putExtras(b)
            startActivity(intent)
        }

        constraint2.setOnClickListener {
            val intent = Intent(applicationContext, TranferenciaBancariaExpress::class.java)
            b.putString("Forma de Pago", "transferencia")
            intent.putExtras(b)
            startActivity(intent)
        }
    }


}