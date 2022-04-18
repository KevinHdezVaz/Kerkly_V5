package com.example.kerklyv5.controlador

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kerklyv5.R

class Notificacion(contexto:Context) {

    private val canalID = "channelID"
    private val canalNombre = "channelName"
    private lateinit var contexto: Context

    private val notificacionID = 0

    init {
        this.contexto = contexto
        crearNotificacionCanal()

        val notificacion = NotificationCompat.Builder(this.contexto, canalID).also {
            it.setContentTitle(this.contexto.getText(R.string.titulo_notificacionPresupuesto))
            it.setContentText(this.contexto.getText(R.string.cuerpo_notificacionPresupuesto))
            it.setSmallIcon(R.drawable.ic_stat_name)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        val notificationManager = NotificationManagerCompat.from(this.contexto)
        notificationManager.notify(notificacionID,notificacion)
    }

    private fun crearNotificacionCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(canalID,canalNombre,importancia).apply {
                lightColor = Color.RED
                enableLights(true)
            }

            val manager = contexto.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
}