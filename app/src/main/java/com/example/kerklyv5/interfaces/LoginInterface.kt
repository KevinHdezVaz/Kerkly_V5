package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface LoginInterface {
    @FormUrlEncoded
    @POST("/InicioSesion.php")
    fun VerficarUsuario(
        @Field("telefonoCliente") telefonoCliente: String,
        @Field("Contrasena") Contrasena: String,
        callback: Callback<Response?>
    )
}