package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response


interface RegistroInterface {

    @FormUrlEncoded
    @POST("/Login.php")
    fun insertUser(
        @Field("Correo") correo: String?,
        @Field("Nombre") Nombre: String?,
        @Field("Apellido_Paterno") apellidpP: String?,
        @Field("Apellido_Materno") apellidoM: String?,
        @Field("telefonoCliente") telefono: String?,
        @Field("generoCliente") genero: String?,
        @Field("Contrasena") contra: String?,
        @Field("fue_NoRegistrado") isRegistrado: String?,
        @Field("deviceID") deviceID: String?,
        callback: Callback<Response?>?
    )

}