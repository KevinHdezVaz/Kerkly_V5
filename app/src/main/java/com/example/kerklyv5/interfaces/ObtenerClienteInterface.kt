package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.ClienteModelo
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ObtenerClienteInterface {

    @FormUrlEncoded
    @GET("Obtener_cliente.php")
    open fun getCliente(@Query("telefonoCliente") telefonoCliente: String):
            Call<List<ClienteModelo?>>?
}