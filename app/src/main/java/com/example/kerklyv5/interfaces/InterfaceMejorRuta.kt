package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.ModeloRutas
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceMejorRuta {

    @FormUrlEncoded
    @GET("obtenerCoordenadasParaSaberElKerklyMasCercano.php")
    open fun ObtenerC(@Query("oficio") oficio: String):
            Call<List<ModeloRutas?>?>?
}