package com.example.kerklyv5.modelo

class ContraniaRecuper(contrasenia: String, correo: String) {
    private var c = correo
    private var con = c

    constructor(corr: String): this("", corr) {
        c = corr
    }

    fun getCorreo(): String {
        return c
    }

    fun getContrasenia(): String{
        return con
    }

    fun setCorreo(correo: String) {
        c = correo
    }

    fun setContrasenia(contrasenia: String) {
        con = contrasenia
    }
}