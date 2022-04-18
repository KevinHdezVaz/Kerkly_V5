package com.example.kerklyv5

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Contrato : AppCompatActivity() {
    private lateinit var myDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrato)
    }

    fun ventana (view: View) {
        myDialog.setContentView(R.layout.activity_contrato)
        myDialog.show()

    }
}