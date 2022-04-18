package com.example.kerklyv5.controlador

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.R
import com.example.kerklyv5.modelo.serial.OrdenPendiente

class AdapterOrdenPendiente(val datset: ArrayList<OrdenPendiente>):
    RecyclerView.Adapter<AdapterOrdenPendiente.ViewHolder>(), View.OnClickListener {

    private lateinit var listener: View.OnClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_oficio: TextView = view.findViewById(R.id.oficio_txt_orden)

        init {}

        val txt_folio: TextView = view.findViewById(R.id.folio_txt_orden)

        init {}


        val txtFecha: TextView = view.findViewById(R.id.fecha_txt_orden)

        init {}


    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtFecha.text = datset[position].Fecha_Inicio
        viewHolder.txt_folio.text = "Folio No. ${datset[position].idContrato}"
        viewHolder.txt_oficio.text = "Servicio ${datset[position].nombreO}"
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_orden_pendiente, viewGroup, false)

        view.setOnClickListener(this)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datset.size
    }

    fun setOnClickListener(l: View.OnClickListener) {
        this.listener = l

    }

    override fun onClick(v: View?) {
        listener.onClick(v)
    }

}