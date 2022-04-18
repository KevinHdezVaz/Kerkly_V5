package com.example.kerklyv5.modelo

import android.os.Environment
import android.util.Log
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfCell
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import harmony.java.awt.Color
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Pdf (nombreC: String, direccionC: String) {

    var nombre = nombreC
    var direccion = direccionC
    lateinit var telefono: String
    var correo = ""
    lateinit var problema: String
    var folio = 0
    var total = 0.0
    private val NOMBRE_DIRECTORIO = "MiPdf3"
    private val NOMBRE_DOCUMENTO = "prueba3.pdf"
    private val ETIQUETA_ERROR = "ERROR"
    var cabecera = ArrayList<String>()
    var lista: MutableList<MutableList<String>>? = null


    fun getRuta(): File? {
        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        var ruta: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                .getExternalStorageState()
        ) {
            ruta = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                NOMBRE_DIRECTORIO
            )
            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null
                    }
                }
            }
        }
        return ruta
    }

    @Throws(IOException::class)
    fun crearFichero(nombreFichero: String?): File? {
        val ruta = getRuta()
        var fichero: File? = null
        if (ruta != null) fichero = File(ruta, nombreFichero)
        return fichero
    }

    fun generarPdf() {

        // Creamos el documento.
        val documento = Document()
        try {
            val f = crearFichero(NOMBRE_DOCUMENTO)

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            val ficheroPdf = FileOutputStream(
                f?.getAbsolutePath()
            )

            // Asociamos el flujo que acabamos de crear al documento.
            val writer = PdfWriter.getInstance(documento, ficheroPdf)

            // Incluimos el pie de pagina y una cabecera
            val cabecera = HeaderFooter(
                Phrase(
                    "KERKLY"
                ), false
            )
            cabecera.setAlignment(HeaderFooter.ALIGN_CENTER)

            val pie = HeaderFooter(
                Phrase(
                    "KERKLY"
                ), false
            )
            pie.setAlignment(HeaderFooter.ALIGN_CENTER)

            documento.setHeader(cabecera)
            documento.setFooter(pie)

            // Abrimos el documento.
            documento.open()


            /* val date = Calendar.getInstance()

             var dia = date.get
             var mes = date.MONTH
             var anio = date.YEAR*/

            var c = Calendar.getInstance()
            var mes = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            var primeraLetra = mes.substring(0,1)
            primeraLetra = primeraLetra.toUpperCase()
            mes = mes.substring(1,mes.length)
            mes = primeraLetra+mes

            val dia = c.get(Calendar.DAY_OF_MONTH)

            val anio = c.get(Calendar.YEAR)

            val fecha = "Chilpancingo, Gro. a $dia de $mes del $anio"
            var f1 = FontFactory.getFont(FontFactory.HELVETICA)
            var p = Paragraph(fecha, f1)
            p.alignment = Paragraph.ALIGN_RIGHT

            documento.add(p)

            var font: Font? = FontFactory.getFont(
                FontFactory.HELVETICA, 28f,
                Font.BOLD, harmony.java.awt.Color.RED
            )
            // documento.add(Paragraph("Titulo personalizado", font))
            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            var f2 = FontFactory.getFont(FontFactory.HELVETICA)
            var ph2 = Phrase("Empresa Kerkly", f2)
            var ph1 = Phrase("De: ", f1)
            p = Paragraph(ph1)
            p.add(ph2)
            documento.add(p)

            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            f2 = FontFactory.getFont(FontFactory.HELVETICA)
            ph2 = Phrase(nombre, f2)
            ph1 = Phrase("Para: ", f1)
            p = Paragraph(ph1)
            p.add(ph2)
            documento.add(p)

            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            f2 = FontFactory.getFont(FontFactory.HELVETICA)
            ph2 = Phrase(direccion, f2)
            ph1 = Phrase("Dirección : ", f1)
            p = Paragraph(ph1)
            p.add(ph2)
            documento.add(p)

            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            f2 = FontFactory.getFont(FontFactory.HELVETICA)
            ph2 = Phrase(telefono, f2)
            ph1 = Phrase("Teléfono: ", f1)
            p = Paragraph(ph1)
            p.add(ph2)
            documento.add(p)

            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            f2 = FontFactory.getFont(FontFactory.HELVETICA)
            ph2 = Phrase(correo, f2)
            ph1 = Phrase("Correo: ", f1)
            p = Paragraph(ph1)
            p.add(ph2)
            documento.add(p)

            f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD)
            p = Paragraph(problema, f1)
            p.alignment = Paragraph.ALIGN_CENTER

            documento.add(p)


            var presupuestp = "Presupuesto No. $folio"
            p = Paragraph(presupuestp)
            p.alignment = Paragraph.ALIGN_RIGHT

            documento.add(p)

            documento.add(Paragraph("En el presente documento se le hace llegar la cotización" +
                    "de sericios solicitados que se de detallan a continuación."))

            documento.add(Paragraph("\n"))

            /*// Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.

            val bitmap = BitmapFactory.decodeResource(
                this.resources,
                R.drawable.logo
            )
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val imagen: Image = Image.getInstance(stream.toByteArray())
            documento.add(imagen)*/

            //val lista = tablaDinamica.getData()
            val num = lista?.size

            // Insertamos una tabla.
            val tabla = PdfPTable(3)

            var phrase = Phrase(this.cabecera[0])
            val pdfCell = PdfPCell(phrase)
            pdfCell.backgroundColor = Color(162,225,140)
            pdfCell.borderColor = Color(0,0,0)
            pdfCell.verticalAlignment = PdfPCell.ALIGN_CENTER
            pdfCell.horizontalAlignment = PdfCell.ALIGN_CENTER
            tabla.addCell(pdfCell)

            phrase = Phrase(this.cabecera[1])
            pdfCell.phrase = phrase
            tabla.addCell(pdfCell)

            phrase = Phrase(this.cabecera[2])
            pdfCell.phrase = phrase
            tabla.addCell(pdfCell)

            var cambio = false

            for (i in 0 until num!!) {
                var l = lista?.get(i)
                cambio =! cambio

                for (j in 0 until l!!.size) {
                    var p = Phrase(l[j])
                    var pdf = PdfPCell(p)
                    pdf.verticalAlignment = PdfPCell.ALIGN_CENTER
                    pdf.horizontalAlignment = PdfPCell.ALIGN_CENTER

                    if (cambio) {
                        pdf.backgroundColor = Color(187,255,163)
                    } else {
                        pdf.backgroundColor = Color(216,255,203)
                    }

                    tabla.addCell(pdf)
                }
                //tabla.addCell("Celda $i")
            }
            documento.add(tabla)

            var pago = "\nSUBTOTAL: $total"
            p = Paragraph(pago)
            p.alignment = Paragraph.ALIGN_RIGHT
            documento.add(p)

            pago = "IVA: 16%"
            p = Paragraph(pago)
            p.alignment = Paragraph.ALIGN_RIGHT
            documento.add(p)

            pago = "Total: ${total * 1.16}\n"
            p = Paragraph(pago)
            p.alignment = Paragraph.ALIGN_RIGHT
            documento.add(p)

            documento.add(
                Paragraph("De estar de acuerdo con lo establecido en el " +
                        "presupuesto solicitado, debe drigirise al mensaje del presupuesto y dar clic " +
                        "en el botón 'Aceptar', una vez aceptado se redigirá a la ventana 'Forma de pago'," +
                        " ya que haya seleccionado la forma a pagar, deberá dirigirse a realizar el pago " +
                        "correspondiente")
            )


            val font2 = FontFactory.getFont(FontFactory.HELVETICA, 15f, Font.UNDERLINE)
            //documento.add(Paragraph("KERKLY", font2))



            // Agregar marca de agua
            /*font = FontFactory.getFont(
                FontFactory.HELVETICA, 42f, Font.BOLD,
                harmony.java.awt.Color.GRAY
            )
            var p: Float = 0.0F
            if (writer.pageNumber%2 == 1) {
                p = 45.0F
            } else {
                p = (-45.0).toFloat()
            }
            ColumnText.showTextAligned(
                writer.directContentUnder,
                Element.ALIGN_CENTER, Paragraph(
                    "androfast.com", font
                ), 297.5f, 421f, p

            )*/
        } catch (e: DocumentException) {
            Log.e(ETIQUETA_ERROR, e.message!!)
        } catch (e: IOException) {
            e.message?.let { Log.e(ETIQUETA_ERROR, it) }
        } finally {
            // Cerramos el documento.
            documento.close()
        }
    }


}

