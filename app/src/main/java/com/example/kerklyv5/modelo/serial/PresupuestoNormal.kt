package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PresupuestoNormal {

    @SerializedName("idPresupuesto")
    @Expose
    var idPresupuesto: Int = 0

    @SerializedName("fechaP")
    @Expose
    var fechaP: String = ""

    @SerializedName("problema")
    @Expose
    var problema: String = ""

    @SerializedName("pago_total")
    @Expose
    var pago_total: Double = 0.0

    @SerializedName("cuerpo_mensaje")
    @Expose
    var cuerpo_mensaje: String = ""

    @SerializedName("estado")
    @Expose
    var estado: String = ""

    @SerializedName("Nombre")
    @Expose
    var nombreKerkly: String = ""

    @SerializedName("Apellido_Paterno")
    @Expose
    var Apellido_Paterno: String = ""

    @SerializedName("Apellido_Materno")
    @Expose
    var apellidoMaterno_kerkly: String = ""

    @SerializedName("Telefono")
    @Expose
    var Telefono: String = ""

    @SerializedName("Calle")
    @Expose
    var Calle: String = ""

    @SerializedName("Colonia")
    @Expose
    var Colonia: String = ""

    @SerializedName("No_Exterior")
    @Expose
    var No_Exterior: Int = 0

    @SerializedName("Referencia")
    @Expose
    var Referencia: String = ""

    @SerializedName("Codigo_Postal")
    @Expose
    var Codigo_Postal: String = ""

    @SerializedName("nombre_cliente")
    @Expose
    var nombre_cliente: String = ""

    @SerializedName("apellidoPaterno_cliente")
    @Expose
    var nombre_apellidoPaterno: String = ""

    @SerializedName("apellidoNaterno_cliente")
    @Expose
    var nombre_apellidoMaterno: String = ""

    @SerializedName("nombreO")
    @Expose
    var nombreO: String = ""
}