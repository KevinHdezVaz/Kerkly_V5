package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NombreNoR {

    @SerializedName("nombre_noR")
    @Expose
    var nombre_noR: String = ""

    @SerializedName("apellidoP_noR")
    @Expose
    var apellidoP_noR: String = ""

    @SerializedName("apellidoM_noR")
    @Expose
    var apellidoM_noR: String = ""

    @SerializedName("numIntentos")
    @Expose
    var numIntentos = 0
}