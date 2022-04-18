package com.example.kerklyv5.interfaces

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import retrofit.Callback
import retrofit.client.Response

interface direccionCoordenadasInterface {
    @FormUrlEncoded
    @POST("/EnviarPruebaSinRegistro.php")
    fun sinRegistro(
        @Field("nombreO") TipoServicio: String,
        @Field("problema") Problematica: String,
        @Field("latitud") latitud: String,
        @Field("longitud") longitud: String,
        @Field("Calle") Calle: String,
        @Field("Colonia") Colonia: String,
        @Field("No_Interior") No_Interior: String,
        @Field("No_Exterior") No_Exterior: String,
        @Field("Ciudad") Ciudad: String,
        @Field("Estado") Estado: String,
        @Field("Pais") Pais: String,
        @Field("Codigo_Postal") Codigo_Postal: String,
        @Field("Referencia") Referencia: String,
        @Field("numeroRP") Numero: String,
        @Field("nombre_noR") nombre_NoR: String,
        @Field("apellidoP_noR") apellidoP_noR: String,
        @Field("apellidoM_noR") apellidoM_noR: String,

        callback: Callback<Response?>
    )
}