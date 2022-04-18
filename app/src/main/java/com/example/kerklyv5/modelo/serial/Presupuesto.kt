package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Presupuesto {

    @SerializedName("latitud")
    @Expose
    var latitud: Double = 0.0

    @SerializedName("longitud")
    @Expose
    var longitud: Double = 0.0

    @SerializedName("problema")
    @Expose
    var problema: String = ""

    @SerializedName("telefonoCliente")
    @Expose
    var telefonoCliente: String = ""

    @SerializedName("Curp")
    @Expose
    var Curp: String = ""

    @SerializedName("nombreO")
    @Expose
    var nombreO: String = ""
}