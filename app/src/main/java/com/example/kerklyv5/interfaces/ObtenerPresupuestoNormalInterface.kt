package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.PresupuestoNormal
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ObtenerPresupuestoNormalInterface {

    @FormUrlEncoded
    @GET("presupuestoRecibidoNormal.php")
    open fun get(@Query("telefonoCliente") telefonoCliente: String):
            Call<List<PresupuestoNormal?>?>?
}