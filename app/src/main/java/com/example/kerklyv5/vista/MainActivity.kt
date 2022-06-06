package com.example.kerklyv5.vista

/*import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest*/
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.CheckoutActivity
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.MainActivityControlador
import com.example.kerklyv5.controlador.Notificacion
import com.example.kerklyv5.extras.IntroSliderActivity
import com.example.kerklyv5.modelo.Cliente
 import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.stripe.android.PaymentConfiguration


/*
* Validar credenciales de acceso -listo
* Crear cuenta                   -listo
* Restablecer la contraseña      -listo
* Prueba sin registro            -listo
*
* */

class MainActivity : AppCompatActivity() {
    private lateinit var editUsuario: TextInputEditText
    private lateinit var editContra: TextInputEditText
    private lateinit var layoutUsuario: TextInputLayout
    private lateinit var layoutContra: TextInputLayout
    private lateinit var usuario: Cliente
    private lateinit var editTelefono: TextInputEditText
    private lateinit var layoutTelefono: TextInputLayout
    private lateinit var dialog: Dialog
    private lateinit var barra: ProgressDialog
    private lateinit var controlador: MainActivityControlador
    private lateinit var layout_nombre: TextInputLayout
    private lateinit var layout_ap: TextInputLayout
    private lateinit var layout_am: TextInputLayout
    private lateinit var edit_nombre: TextInputEditText
    private lateinit var edit_ap: TextInputEditText
    private lateinit var edit_am: TextInputEditText
    private lateinit var id: String
    //video fondo
    protected lateinit var vv_fondo: VideoView
    protected lateinit var mMediaPlayer: MediaPlayer
    protected var mCurrentVideoPosition: Int = 0
    private lateinit var ivLogo: ImageView
    private lateinit var animation: Animation
    private lateinit var animation2: Animation
    private lateinit var animation3: Animation
    private lateinit var animation4: Animation
    private lateinit var boton: Button
    private lateinit var epicDialog2: Dialog
    private lateinit var seguir: Button
    private lateinit var buttonclosed: ImageButton




    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        controlador = MainActivityControlador()

        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

      // controlador.verificarSesion(id, this)


        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51KnpjyGC5CZPzSfGrv3rhilqycFro7CnKiCD3Tq0Kk6ybIse3gfcCHF2y10cT9wQJDdsffgd4cHgEIzyU1vOqCrp00WAeTQXg7"
        )

        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
            vv_fondo = findViewById<VideoView>(R.id.vv_fondo)


//fondo de video
        val uri = Uri.parse("android.resource://"
                +packageName
                +"/"
                +R.raw.fondokerklyy)
        vv_fondo.setVideoURI(uri)
        vv_fondo.start()
        vv_fondo.setOnPreparedListener { mp ->
            mMediaPlayer = mp
            mMediaPlayer.isLooping = true
            if(mCurrentVideoPosition != 0){
                mMediaPlayer.seekTo(mCurrentVideoPosition)
                mMediaPlayer.start()
                mMediaPlayer.setVolume(0F, 0F);
            }
        }



        editUsuario = findViewById(R.id.input_user)


        boton = findViewById(R.id.button)

        ivLogo = findViewById(R.id.logokerkly)
        editContra = findViewById(R.id.input_password)
        layoutUsuario = findViewById(R.id.textnputUser)
        layoutContra = findViewById(R.id.textnputPassword)
        dialog = Dialog(this)


        //animaciones
        animation = AnimationUtils.loadAnimation(this, R.anim.advanced_inicio)
        ivLogo.startAnimation(animation)

        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_right_0)
        layoutUsuario.startAnimation(animation2)

        animation3 = AnimationUtils.loadAnimation(this, R.anim.from_left_advanced)
        layoutContra.startAnimation(animation3)

        animation4 = AnimationUtils.loadAnimation(this, R.anim.from_right_0)
        boton.startAnimation(animation4)


//si es la primera vez que se inicia la app, se envia al IntroSlider
     //si no, se mantiene en la ventana actual
        if(isFirstTime()){
            //cambiar a introslider
            startActivity(Intent(this@MainActivity, IntroSliderActivity::class.java))
            finish();
        }else{
        }

        //  val constraints = Constraints.Builder().setRequiresCharging(true).build()
        //val uploadWork: WorkRequest = OneTimeWorkRequestBuilder<PresupuestoWorker>().setConstraints(constraints).build()
        //WorkManager.getInstance(this).enqueue(uploadWork)
        //startService(Intent(this,PresupuestoService::class.java))
        //startService(Intent(this, PresupuestoServicio::class.java))
    }

    fun click (view: View) {
        val usuario = editUsuario.text.toString()

        if(usuario.isEmpty()) {
            layoutUsuario.error = getText(R.string.campo_requerido)
        } else {
            layoutUsuario.error = null
        }

        val contra = editContra.text.toString()

        if (contra.isEmpty()) {
            layoutContra.error = getText(R.string.campo_requerido)
        } else {
            layoutContra.error = null
        }

        if (!usuario.isEmpty() && !contra.isEmpty()) {
            if (usuario.length != 10) {
                layoutUsuario.error = getText(R.string.telefono_error)

            } else {
                layoutUsuario.error = null

                //val b = Bundle()
                // b.putString("Telefono", usuario)
                // val i = Intent(this, SolicitarServicio::class.java)
                // i.putExtras(b)


                val u = Cliente(editUsuario.text.toString(), editContra.text.toString())
                controlador.verficiarUsuario(u, this)
                //startActivity(i)
            }
        }
        //  usuario = Cliente(editUsuario.text.toString(), editContra.text.toString())
        // controlador.verficiarUsuario(usuario, this)

    }

    @SuppressLint("StaticFieldLeak")
    inner class RetreiveFeedTask: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            //controlador.getNombreNoR(usuario)
            controlador.pruebaRegistrarNumero(usuario,
                applicationContext, layoutTelefono)
            return null
        }

        override fun onPostExecute(result: String?) {
            barra.dismiss()
        }

    }


    fun crearCuenta(view: View) {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }

    fun restablecerContrasenia (view: View) {
        val intent = Intent(this, RecuperarCuenta::class.java)
        startActivity(intent)
    }

    fun servicioExpress (view: View) {
        var notificacion = Notificacion(this)
        dialog.setContentView(R.layout.telefono_no_registrado_confirmar)
        dialog.show()
    }


    //modificar este metodo par que solo ingrese el numero
    fun aceptarNoRegistrado (view: View) {

        editTelefono = epicDialog2.findViewById(R.id.edit_telefonoNoRegistrado)
        layoutTelefono = epicDialog2.findViewById(R.id.layoutTelefonoNoRegistrado)

        var band = false

        if (!controlador.verificarNumeroTelNoR(this)) {
            editTelefono.isEnabled = true
            usuario = Cliente(editTelefono.text.toString())

            if (usuario.getTelefonoNoR().isEmpty()) {
                layoutTelefono.error = getString(R.string.campo_requerido)
            } else {
                layoutTelefono.error = null
                if (usuario.getTelefonoNoR().length != 10) {
                    layoutTelefono.error = getText(R.string.telefono_error)
                } else {
                    layoutTelefono.error = null
                }

            }
        } else {
            editTelefono.isEnabled = false
        }

        if (!(usuario.getTelefonoNoR().isEmpty())) {
            barra = ProgressDialog.show(this, "", "Ingresando...")
            val task = RetreiveFeedTask()
            task.execute()
        }
    }


    fun mostrarSalir(view: View) {
        var notificacion = Notificacion(this)
        epicDialog2 = Dialog(this)
        epicDialog2.setContentView(R.layout.about2)
        buttonclosed = epicDialog2.findViewById<View>(R.id.buttonclosed) as ImageButton


        buttonclosed.setOnClickListener(View.OnClickListener { epicDialog2.dismiss() })



        epicDialog2.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        epicDialog2.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun isFirstTime(): Boolean {
        val preferences = getPreferences(MODE_PRIVATE)
        val ranBefore = preferences.getBoolean("RanBefore", false)
        if (!ranBefore) {
            // first time
            val editor = preferences.edit()
            editor.putBoolean("RanBefore", true)
            editor.commit()
        }
        return !ranBefore
    }


    override fun onPause() {
        super.onPause()
        mCurrentVideoPosition = mMediaPlayer.currentPosition
        vv_fondo.pause()
    }



    override fun onResume() {
        super.onResume()
        vv_fondo.start()

    }

    fun pagame(view: View){
        startActivity(Intent(this@MainActivity, CheckoutActivity::class.java))
    }
    
}