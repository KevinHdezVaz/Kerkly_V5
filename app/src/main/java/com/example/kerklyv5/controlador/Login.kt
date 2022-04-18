package com.example.kerklyv5.controlador

import com.example.kerklyv5.interfaces.RegistroInterface
import com.example.kerklyv5.url.Url

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response


class Login {
    val ROOT_URL = Url().url

    fun InsertarMysql(
        correo: String,
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        telefono: String,
        genero: String,
        contra: String,
        isRegistrado: String,
        id: String
    ) {
        val adapter = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api: RegistroInterface = adapter.create(RegistroInterface::class.java)
        api.insertUser(
            correo,
            nombre,
            apellidoP,
            apellidoM,
            telefono,
            genero,
            contra,
            isRegistrado,
            id,
            object : Callback<Response?> {
                override fun success(t: Response?, response2: Response?) {
                    var reader: BufferedReader? = null
                    var output = ""
                    try {
                        reader = BufferedReader(InputStreamReader(t?.body?.`in`()))

                        output = reader.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    //  Toast.makeText(Correo, output, Toast.LENGTH_LONG).show();
                    System.out.println("Todo bien $output")
                    System.out.println("Correo $correo")
                    System.out.println("Nombre $nombre")
                    System.out.println("ApellidoP $apellidoP")
                    System.out.println("ApellidoM $apellidoM")
                    System.out.println("Telefono $telefono")
                    System.out.println("Genero $genero")
                    System.out.println("Contraseña $contra")
                    System.out.println("Registrado $isRegistrado")

                }

                override fun failure(error: RetrofitError) {
                    println("error $error")
                }

            }
        )
    }

}