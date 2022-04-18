package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface ModificarContrasenaInterface {
    @FormUrlEncoded
    @POST("/RecuperarCuentaContra.php")
    fun VerficarUsuario(
        @Field("Correo") Correo: String,
        @Field("Contrasena") Contrasena: String,
        callback: Callback<Response?>
    )
}