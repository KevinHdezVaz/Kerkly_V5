package com.example.kerklyv5.interfaces

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST

interface IngresarPresupuestoInterface {

    @FormUrlEncoded
    @POST("/IngresarPresupuestoNoR.php")
    fun aceptar(
        @Field("Curp")  Curp: String,
        @Field("problema") problema: String,
        @Field("idNoRTelefono") idNoRTelefono: Int,
        @Field("nombreO") nombreO: String,
        callback: Callback<Response?>
    )

}
