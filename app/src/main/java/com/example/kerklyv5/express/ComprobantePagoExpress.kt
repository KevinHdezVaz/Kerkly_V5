package com.example.kerklyv5.express

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.kerklyv5.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.lang.Integer.min

class ComprobantePagoExpress : AppCompatActivity() {
    private lateinit var btn: MaterialButton
    private lateinit var btn_tomarFoto: MaterialButton
    private lateinit var btn_SeleccionarFoto: MaterialButton
    private lateinit var b: Bundle
    private val select_picture = 400
    val REQUEST_IMAGE_CAPTURE = 1
    private var folio = 0
    lateinit var storageReference: StorageReference
    lateinit var referenceImage: StorageReference
    private lateinit var imagen: ImageView
    private lateinit var U: Uri
    lateinit var dialog: Dialog
    lateinit var progressBar: ProgressBar
    val  REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    private lateinit var txt: TextView
    private var band = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprobante_pago_express)

        storageReference = FirebaseStorage.getInstance().reference
        txt = findViewById(R.id.txt_comprobante_pago_label)

        b = intent.extras!!

        folio = b.getInt("Folio")
        Log.d("folio", folio.toString())

        band = b.getBoolean("Express")

        btn = findViewById(R.id.btn_contrato)
        btn_tomarFoto = findViewById(R.id.tomarFoto_btn)
        btn_SeleccionarFoto = findViewById(R.id.seleccionarFoto_btn)
        imagen = findViewById(R.id.imageView_foto)

        btn.setOnClickListener {
            var i = Intent(application, ContratoExpress::class.java)
            i.putExtras(b)
            startActivity(i)
        }

        btn_tomarFoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        btn_SeleccionarFoto.setOnClickListener {
            val opcion = arrayOf<CharSequence>("Elegir de galeria", "Cancelar")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Elije una Opcion ")
            builder.setItems(
                opcion
            ) { dialog, posicion ->
                if (opcion[posicion] === "Elegir de galeria") {
                    cargarimagen()
                } else {
                    if (opcion[posicion] === "Cancelar") {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        }

        if (!band) {
            txt.visibility = View.GONE
        }
    }

    private fun cargarimagen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecciona la imagen"), select_picture)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //codigo para guardar la foto tomada por nuestra camara
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setProgressDialog()

            referenceImage = storageReference.child("Comprobante ${folio.toString()}")
            referenceImage.putFile(U).addOnSuccessListener(this,
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->

                    val targetW: Int = imagen.width
                    val targetH: Int = imagen.height

                    val bmOptions = BitmapFactory.Options().apply {
                        // Get the dimensions of the bitmap
                        inJustDecodeBounds = true
                        val photoW: Int = outWidth
                        val photoH: Int = outHeight
                        val scaleFactor: Int = min(photoW / targetW, photoH / targetH)
                        inJustDecodeBounds = false
                        inSampleSize = scaleFactor
                    }
                    BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                        imagen.setImageBitmap(bitmap)
                        dialog.dismiss()
                    }


                })

        } else {

            if (resultCode == RESULT_OK) {
                setProgressDialog()
                U = data!!.data!!
                // imagen.setImageURI(U)
                referenceImage = storageReference.child("Comprobante ${folio.toString()}")
                referenceImage.putFile(U).addOnSuccessListener(this,
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        /* val uriTask = taskSnapshot.storage.downloadUrl
                         while (!uriTask.isSuccessful);
                         val DescargarUrl = uriTask.result
                         urlImage = DescargarUrl!!*/
                        imagen.setImageURI(U)
                        dialog.dismiss()
                        //Toast.makeText(this, "Comprobante enviado", Toast.LENGTH_SHORT).show()

                    })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setProgressDialog() {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        progressBar = ProgressBar(this)

        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = "Subiendo..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)
        dialog = builder.create()
        dialog.show()
        val window: Window? = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()

                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.kerklyv5",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    U = photoURI

                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}