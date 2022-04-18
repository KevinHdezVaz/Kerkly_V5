package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Historial {

    @SerializedName("nombreO")
    @Expose
    var nombreO: String = ""

    @SerializedName("idContrato")
    @Expose
    var idContrato: Int = 1

    @SerializedName("Fecha_Inicio")
    @Expose
    var Fecha_Inicio: String = ""

    @SerializedName("Fecha_Final")
    @Expose
    var Fecha_Final: String = ""

    @SerializedName("Nombre")
    @Expose
    var Nombre: String = ""

    @SerializedName("Apellido_Paterno")
    @Expose
    var Apellido_Paterno: String = ""

    @SerializedName("Apellido_Materno")
    @Expose
    var Apellido_Materno: String = ""

    @SerializedName("kerkly.Nombre")
    @Expose
    var NombreK: String = ""

    @SerializedName("kerkly.Apellido_Paterno")
    @Expose
    var Apellido_PaternoK: String = ""

    @SerializedName("kerkly.Apellido_Materno")
    @Expose
    var Apellido_MaternoK: String = ""
}