package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.NombreNoR
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ObtenerNombresNoRInterface {
    @FormUrlEncoded
    @GET("obtener_nombreClienteNoR.php")
   open fun nombres(@Query("telefono_NoR") telefono_NoR: String):
                Call<List<NombreNoR?>?>?
}