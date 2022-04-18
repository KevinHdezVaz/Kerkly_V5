package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface EntrarSinRegistroInterface {
    @FormUrlEncoded
    @POST("/pruebaSinRegistroNumero.php")
    fun sinRegistro(
        @Field("telefonoCliente") telefonoCliente: String,
        callback: Callback<Response?>
    )
}