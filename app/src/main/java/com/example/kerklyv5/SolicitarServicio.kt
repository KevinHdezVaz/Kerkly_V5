package com.example.kerklyv5

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.kerklyv5.databinding.ActivitySolicitarServicioBinding
import com.example.kerklyv5.interfaces.ObtenerClienteInterface
import com.example.kerklyv5.interfaces.SesionAbiertaInterface
import com.example.kerklyv5.modelo.serial.ClienteModelo
import com.example.kerklyv5.ui.home.HomeFragment
import com.example.kerklyv5.url.Url
import com.example.kerklyv5.vista.fragmentos.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class SolicitarServicio : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySolicitarServicioBinding
    private lateinit var b: Bundle
    private lateinit var txt_nombre: TextView
    private lateinit var txt_correo: TextView
    lateinit var telefono: String
    private lateinit var nombre: String
    private lateinit var correo: String
    private lateinit var id: String
    private lateinit var drawerLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySolicitarServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //txt_nombre = findViewById(R.id.nombre_txt)
      //  txt_correo = findViewById(R.id.correo_txt)

        setSupportActionBar(binding.appBarSolicitarServicio.toolbar)

        b = intent.extras!!
        telefono = b.getString("Telefono")!!
        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)


        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_solicitar_servicio)

        val view = navView.getHeaderView(0)
        txt_nombre = view.findViewById(R.id.nombre_txt)
        txt_correo = view.findViewById(R.id.correo_txt)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_ordenesPendientes,
                R.id.historialFragment,
                R.id.nav_mensajes
            ), drawerLayout
        )

       // sesion(correo)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setFragmentHome()
                R.id.nav_ordenesPendientes -> setFragmentOrdenesPendientes()
                R.id.historialFragment -> setFragmentHistorial()
                R.id.nav_mensajes -> setMensajesPresupuesto()

            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        getJson()

        setFragmentHome()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.solicitar_servicio, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_solicitar_servicio)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setFragmentHome() {
        val f = HomeFragment()
       // val args = Bundle()
        //args.putString("Tel", telefono)
        f.arguments = b
        var fm = supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }

    private fun setFragmentOrdenesPendientes() {
        val f = OrdenesPendientesFragment()
        // val args = Bundle()
        //args.putString("Tel", telefono)
        f.arguments = b
        var fm = supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }

    private fun setFragmentHistorial() {
        val f = HistorialFragment()
        // val args = Bundle()
        //args.putString("Tel", telefono)
        f.arguments = b
        var fm = supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }

    private fun setFragmentMensajes() {
        val f = ListaChatsFragment()
        // val args = Bundle()
        //args.putString("Tel", telefono)
        f.arguments = b
        var fm = supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }

    private fun setMensajesPresupuesto() {
        val f = MensajesPresupuestoFragment()
        f.arguments = b
        var fm = supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_content_solicitar_servicio,f).commit()
        }
    }



    private fun sesion(correo: String) {
        val ROOT_URL = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api = adapter.create(SesionAbiertaInterface::class.java)
        api.sesionAbierta(
            correo,
            id,
            object : retrofit.Callback<retrofit.client.Response?> {
                override fun success(t: retrofit.client.Response?, response2: retrofit.client.Response?) {
                    var reader: BufferedReader? = null
                    var output = ""
                    try {
                        reader = BufferedReader(InputStreamReader(t?.body?.`in`()))

                        output = reader.readLine()


                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Log.e("nosee", output)

                }

                override fun failure(error: RetrofitError) {
                    println("error $error")
                }

            }
        )
    }

    private fun getJson() {
        val ROOT_URL = Url().url
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("$ROOT_URL/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val presupuestoGET = retrofit.create(ObtenerClienteInterface::class.java)

        val call = presupuestoGET.getCliente(telefono)
        call?.enqueue(object : Callback<List<ClienteModelo?>?> {

            override fun onResponse(
                call: Call<List<ClienteModelo?>?>,
                response: Response<List<ClienteModelo?>?>
            ) {

                val postList: ArrayList<ClienteModelo> = response.body() as ArrayList<ClienteModelo>

                //carsModels = response.body() as ArrayList<presupuestok>
                Log.d("Lista", postList[0].toString())
                val n = postList[0].Nombre
                val ap = postList[0].Apellido_Paterno
                val am = postList[0].Apellido_Materno

                Log.d("nombre", n)


                nombre = "$n $ap $am"

                correo = postList[0].Correo

                txt_nombre.text = nombre
                txt_correo.text = correo

                sesion(correo)


            }

            override fun onFailure(call: Call<List<ClienteModelo?>?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Codigo de respuesta de error: $t",
                    Toast.LENGTH_SHORT
                ).show();
            }

        })
    }


}