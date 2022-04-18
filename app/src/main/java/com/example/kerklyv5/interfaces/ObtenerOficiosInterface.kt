package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.Oficio
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET

interface ObtenerOficiosInterface {
    @FormUrlEncoded
    @GET("obtener_oficios.php")
    open fun oficios():
            Call<List<Oficio?>?>?
}