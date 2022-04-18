package com.example.kerklyv5.modelo

class Cliente(usuario_: String, contra_: String) {
    private var usuario: String = usuario_
    private var contra: String = contra_
    private lateinit var telefonoNoR: String
    private lateinit var nombre: String
    private lateinit var apellidoP: String
    private lateinit var apellidoM: String

    constructor(telefono: String): this ("", "") {
        telefonoNoR = telefono
    }

    constructor(): this ("", "") {}

    fun getUsuario(): String { return usuario }

    fun getContra(): String { return contra }

    fun getTelefonoNoR(): String { return telefonoNoR }

    fun getNombre(): String { return nombre }

    fun getApellidoPaterno(): String { return apellidoP }

    fun getApellidoMaterno(): String { return apellidoM }

    fun setUusario(u: String) { usuario = u }

    fun setContra(c: String) { contra = c }

    fun setTelefono(t: String) { telefonoNoR = t }

    fun setNombre(n: String) { nombre = n }

    fun setApellidoPaterno(ap: String) { apellidoP = ap }

    fun setApellidoMaterno(am: String) { apellidoM = am }
}