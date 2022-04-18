package com.example.kerklyv5.vista

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kerklyv5.R
import com.example.kerklyv5.SolicitarServicio
import com.example.kerklyv5.express.PedirServicioExpress
import com.example.kerklyv5.interfaces.IngresarPresupuestoClienteInterface
import com.example.kerklyv5.interfaces.IngresarPresupuestoInterface
import com.example.kerklyv5.url.Url

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var marcador: Marker
    var latitud: Double = 0.0
    var longitud: Double = 0.0
    lateinit var BotonT: Button
    lateinit var BotonEnviarU: Button
    var tipo = 1
    lateinit var mapa: String
    private lateinit var b: Bundle
    private lateinit var curp: String
    private lateinit var problema: String
    private lateinit var telefono: String
    private lateinit var oficio: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        b = intent.extras!!

        curp = b.getString("Curp Kerkly").toString()
        problema = b.getString("Problema").toString()
        telefono = b.getString("Telefono").toString()
        oficio = b.getString("Oficio").toString()

        val band: Boolean = b.getBoolean("Express")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bandK = b.getBoolean("Ker")

        getLocalizacion()

        BotonT = findViewById(R.id.button2)
        BotonT.setOnClickListener {
            when(tipo) {
                1 -> Hibrido()
                2 -> satelite()
                3 -> terreno()
                4 -> terreno2()
                else-> print(" ")
            }
            //Toast.makeText(applicationContext, mapa, Toast.LENGTH_LONG).show()

        }

        BotonEnviarU = findViewById(R.id.buttonEnviarUbicacion)
        BotonEnviarU.setOnClickListener {

            val la = java.lang.Double.toString(latitud)
            val lo = java.lang.Double.toString(longitud)

            //if (bandK!!) {
                Toast.makeText(this, "Mi latitud ${la}", Toast.LENGTH_SHORT).show()
            //} else {

                lateinit var intent: Intent
                //   intent.putExtra("dato1", la)
                // intent.putExtra("dato2", lo)

                if (band == true) {
                    intent = Intent(this, PedirServicioExpress::class.java)
                } else {
                    intent = Intent(this, SolicitarServicio::class.java)
                    ingresarPresupuesto()
                }

                //codigo que va antes de llamar al activity

                b.putString("Latitud", la)
                b.putString("Longitud", lo)
                intent.putExtras(b)
                startActivity(intent)
          //  }



        }
    }

    private fun ingresarPresupuesto() {
        val ROOT_URL = Url().url
        val adapter = RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build()
        val api = adapter.create(IngresarPresupuestoClienteInterface::class.java)
        api.presupuesto(curp, problema, telefono, oficio, latitud, longitud,
            object : Callback<Response?> {
                override fun success(t: Response?, response: Response?) {
                    var salida: BufferedReader? = null
                    var entrada = ""
                    try {
                        salida = BufferedReader(InputStreamReader(t?.body?.`in`()))
                        entrada = salida.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Toast.makeText(applicationContext, "Entre por aqui", Toast.LENGTH_LONG).show()


                    val cadena = "Datos enviados"
                    if (cadena.equals(entrada)){
                        Toast.makeText(applicationContext,"Datos enviados", Toast.LENGTH_LONG).show()
                    }
                }

                override fun failure(error: RetrofitError?) {
                    println("error$error")
                    Toast.makeText(applicationContext, "error $error", Toast.LENGTH_LONG).show()
                }

            }
        )
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true

        val locationManager = this@MapsActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val miUbicacion = LatLng(location.getLatitude(), location.getLongitude())
                latitud = location.latitude
                longitud = location.longitude

                mMap.addMarker(MarkerOptions().position(miUbicacion).title("ubicacion de Luis Luis"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion))
                val cameraPosition = CameraPosition.Builder()
                    .target(miUbicacion)
                    .zoom(14f)
                    .bearing(90f)
                    .tilt(45f)
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)

    }

    private fun getLocalizacion() {
        val permiso =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
                finish()


            }
        }
    }

    private fun Hibrido() {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa = "HÍBRIDO";
        tipo = 2;
    }
    private fun satelite() {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa = "SATÉLITE";
        tipo =3;
    }
    private fun terreno() {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mapa = "TERRENO";
        tipo = 4;
    }
    private fun terreno2() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa = "TERRENO";
        tipo = 1;
    }
}