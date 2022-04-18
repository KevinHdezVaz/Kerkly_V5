package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Kerkly {
    @SerializedName("Curp")
    @Expose
    var Curp: String = ""

    @SerializedName("Nombre")
    @Expose
    var Nombre: String = ""

    @SerializedName("Apellido_Paterno")
    @Expose
    var Apellido_Paterno: String = ""

    @SerializedName("Apellido_Materno")
    @Expose
    var Apellido_Materno: String = ""
}