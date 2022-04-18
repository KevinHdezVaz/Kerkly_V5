package com.example.kerklyv5.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kerklyv5.R
import com.example.kerklyv5.modelo.serial.Kerkly

class AdapterKerkly(val datset: ArrayList<Kerkly>):
    RecyclerView.Adapter<AdapterKerkly.ViewHolder>(), View.OnClickListener {

    private lateinit var listener: View.OnClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.nombre_kekrly_txt)

        init {}

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: AdapterKerkly.ViewHolder, position: Int) {
        val n = datset[position].Nombre
        val ap = datset[position].Apellido_Paterno
        val am = datset[position].Apellido_Materno
        val nombre = "$n $ap $am"

        viewHolder.txtNombre.text = nombre


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_kerkly, viewGroup, false)

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