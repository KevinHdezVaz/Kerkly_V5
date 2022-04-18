package com.example.kerklyv5.interfaces


import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface RecuperarCuentaInterface {
    @FormUrlEncoded
    @POST("/RecuperarCuenta.php")
    fun VerficarUsuario(
        @Field("Correo") Correo: String,
        callback: Callback<Response?>
    )
}
