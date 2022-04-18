package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface ActualizarMensajeInterface {

    @FormUrlEncoded
    @POST("/actualizarMensaje.php")
    fun mensaje (
        @Field("idPresupuestoNoRegistrado") idPresupuestoNoRegistrado: Int,
        callback: Callback<Response?>?
    )


}