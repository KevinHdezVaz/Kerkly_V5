package com.example.kerklyv5.interfaces

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST

interface IngresarPresupuestoClienteInterface {

    @FormUrlEncoded
    @POST("/IngresarPresupuestoInicial.php")
    fun presupuesto(
        @Field("Curp")  Curp: String,
        @Field("problema") problema: String,
        @Field("telefonoCliente") telefonoCliente: String,
        @Field("nombreO") nombreO: String,
        @Field("latitud") latitud: Double,
        @Field("longitud") longitud: Double,
        callback: Callback<Response?>
    )

}