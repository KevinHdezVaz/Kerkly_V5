package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrdenPendiente {

    @SerializedName("nombreO")
    @Expose
    var nombreO: String = ""

    @SerializedName("idContrato")
    @Expose
    var idContrato: Int = 1

    @SerializedName("Fecha_Inicio")
    @Expose
    var Fecha_Inicio: String = ""

    @SerializedName("cliente_nombre")
    @Expose
    var Nombre: String = ""

    @SerializedName("cliente_ap")
    @Expose
    var Apellido_Paterno: String = ""

    @SerializedName("cliente_am")
    @Expose
    var Apellido_Materno: String = ""

    @SerializedName("Nombre")
    @Expose
    var NombreK: String = ""

    @SerializedName("Apellido_Paterno")
    @Expose
    var Apellido_PaternoK: String = ""

    @SerializedName("Apellido_Materno")
    @Expose
    var Apellido_MaternoK: String = ""
}