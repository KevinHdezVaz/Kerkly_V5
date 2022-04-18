package com.example.kerklyv5.interfaces

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST

interface VerificarSesionInterface {

    @FormUrlEncoded
    @POST("/VerificarSesion.php")
    fun sesionAbierta(
        @Field("deviceID") deviceID: String?,
        callback: Callback<Response?>?
    )
}