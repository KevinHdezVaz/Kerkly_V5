package com.example.kerklyv5.controlador

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.widget.Toast

class PresupuestoServicio : Service() {

    // private lateinit var miTarea: MyTask

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Servicio Iniciado", Toast.LENGTH_LONG).show()
        //miTarea = MyTask()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /* var i = 0
              while(i < 5 ) {
                  Toast.makeText(this, "HOLAA", Toast.LENGTH_SHORT).show()
                  i++
                  try {
                     Thread.sleep(6000)
                  } catch (e: InterruptedException) {
                      e.printStackTrace()
                  }
          }*/
        Toast.makeText(this, "HOLAA", Toast.LENGTH_SHORT).show()
        Thread {
            while (true) {


            }
        }
        //  miTarea.execute()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Servicio destruido",Toast.LENGTH_LONG).show()
        //  miTarea.cancel(true)
    }

    /* @SuppressLint("StaticFieldLeak")
     inner class MyTask: AsyncTask<String, String, String>() {
         private var band: Boolean = false
         override fun doInBackground(vararg p0: String?): String? {
             while (band) {
                 try {
                     publishProgress()
                     Thread.sleep(6000)
                 } catch (e: InterruptedException) {
                     e.printStackTrace()
                 }
             }
             return null
         }

         override fun onPreExecute() {
             super.onPreExecute()
             band = true
         }

         override fun onProgressUpdate(vararg values: String?) {
             Toast.makeText(applicationContext, "HOLA", Toast.LENGTH_LONG).show()


         }

         override fun onCancelled() {
             super.onCancelled()
             band = false
         }
     }*/
}