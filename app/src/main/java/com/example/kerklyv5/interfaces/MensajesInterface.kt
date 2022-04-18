package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.MensajesDatoss
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.*

interface MensajesInterface {
    @FormUrlEncoded
    @GET("PresupuestoRecibido.php")
    open fun EnviarT(@Query("telefono_NoR") telefono_NoR: String):
            Call<List<MensajesDatoss?>?>?
}
