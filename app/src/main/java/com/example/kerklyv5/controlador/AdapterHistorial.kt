package com.example.kerklyv5.controlador

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.R
import com.example.kerklyv5.modelo.serial.Historial

class AdapterHistorial(val datset: ArrayList<Historial>):
    RecyclerView.Adapter<AdapterHistorial.ViewHolder>(), View.OnClickListener {

    private lateinit var listener: View.OnClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_folio: TextView = view.findViewById(R.id.folio_historial_txt)

        init {}


        val txt_fecha: TextView = view.findViewById(R.id.txt_fecha_historial)

        init {}
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txt_folio.text = "Folio No. ${datset[position].idContrato}"
        viewHolder.txt_fecha.text = datset[position].Fecha_Inicio
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_historial, viewGroup, false)

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