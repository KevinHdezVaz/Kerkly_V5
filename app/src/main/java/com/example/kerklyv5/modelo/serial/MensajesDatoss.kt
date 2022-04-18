package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MensajesDatoss {
    @SerializedName("idPresupuestoNoRegistrado")
    @Expose
    var idPresupuestoNoRegistrado: Int = 0

    @SerializedName("fechaPresupuesto")
    @Expose
    var fechaPresupuesto: String = ""

    @SerializedName("Nombre")
    @Expose
    var Nombre: String = ""

    @SerializedName("Apellido_Paterno")
    @Expose
    var Apellido_Paterno: String = ""

    @SerializedName("Apellido_Materno")
    @Expose
    var Apellido_Materno: String = ""

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

    @SerializedName("problema")
    @Expose
    var problema: String = ""

    @SerializedName("PagoTotal")
    @Expose
    var PagoTotal: Double = 0.0

    @SerializedName("cuerpo_mensaje")
    @Expose
    var cuerpo_mensaje = ""

    @SerializedName("estaPagado")
    @Expose
    var estaPagado = ""

    @SerializedName("nombre_noR")
    @Expose
    var nombre_noR = ""

    @SerializedName("apellidoP_noR")
    @Expose
    var apellidoP_noR = ""

    @SerializedName("apellidoM_noR")
    @Expose
    var apellidoM_noR = ""

    @SerializedName("nombreO")
    @Expose
    var nombreO = ""
}