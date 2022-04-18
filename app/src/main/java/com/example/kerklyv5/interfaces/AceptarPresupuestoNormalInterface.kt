package com.example.kerklyv5.interfaces

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST

interface AceptarPresupuestoNormalInterface {

    @FormUrlEncoded
    @POST("/Aceptar_presupuesto_normal.php")
    fun Aceptar(
        @Field("idPresupuesto")  idPresupuesto: String,
        @Field("aceptoCliente") aceptoCliente: String,
        callback: Callback<Response?>
    )
}