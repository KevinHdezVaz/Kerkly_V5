package com.example.kerklyv5.vista

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kerklyv5.R
import com.example.kerklyv5.controlador.MainActivityControlador
import com.example.kerklyv5.extras.IntroSliderActivity


class PantallaInicio : AppCompatActivity() {
    private lateinit var animation: Animation
    private lateinit var ivLogo: ImageView
    private lateinit var id: String
    private lateinit var controlador: MainActivityControlador


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //abardar toda la pantalla
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pantalla_inicio)


       var context = this


          controlador = MainActivityControlador()


         id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
         controlador.verificarSesion(id, this)

         ivLogo = findViewById(R.id.img_logo_inicio)
        //Instanciamos un objeto
        //Instanciamos un objeto
        animation = AnimationUtils.loadAnimation(this, R.anim.animation)


        //Establecemos la animacion al image view
        ivLogo.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                //Este metodo es llamado cuando la animacion inicia
                //Aqui podriamos realizar algunas comprobaciones iniciales
            }

            override fun onAnimationEnd(animation: Animation) {
                   controlador.verificarSesion(id, context)
                /*val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()*/
            }

            override fun onAnimationRepeat(animation: Animation) {
                //Este metodo es llamado en cada repeticion
            }
        })

        /*val i = Intent(this@PantallaInicio, MainActivity::class.java)
        startActivity(i)
        finish()*/

    }
}