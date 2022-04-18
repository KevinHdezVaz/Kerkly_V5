package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModeloRutas {
    @SerializedName("Curp")
    @Expose
    var Curp: String = ""

    @SerializedName("latitud")

    @Expose
    var latitud = 0.0

    @SerializedName("longitud")
    @Expose
    var longitud = 0.0
}