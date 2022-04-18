package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.Historial
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ObtenerHistorialInterface {

    @FormUrlEncoded
    @GET("ObtenerHistorialCliente.php")
    open fun historia(@Query("telefonoCliente") telefonoCliente: String):
            Call<List<Historial?>?>?
}