package com.example.kerklyv5.interfaces

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST

interface SesionAbiertaInterface {
    @FormUrlEncoded
    @POST("/dejarSesionAbierta.php")
    fun sesionAbierta(
        @Field("Correo") correo: String?,
        @Field("deviceID") deviceID: String?,
        callback: Callback<Response?>?
    )
}