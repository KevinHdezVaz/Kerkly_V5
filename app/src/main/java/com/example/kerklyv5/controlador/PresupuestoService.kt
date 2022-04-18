package com.example.kerklyv5.controlador

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PresupuestoService(contexto: AppCompatActivity): Service() {
    var c = contexto
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        Toast.makeText(c,"Servicio iniciado", Toast.LENGTH_LONG).show()
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var n = Notificacion(c)
        return Service.START_STICKY
    }
}