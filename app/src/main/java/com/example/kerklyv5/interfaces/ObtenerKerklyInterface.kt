package com.example.kerklyv5.interfaces

import com.example.kerklyv5.modelo.serial.Kerkly
import retrofit.http.FormUrlEncoded
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ObtenerKerklyInterface {

    @FormUrlEncoded
    @GET("ObtenerKerklys.php")
    open fun kerklys(@Query("nombreO") nombreO: String):
            Call<List<Kerkly?>?>?

}