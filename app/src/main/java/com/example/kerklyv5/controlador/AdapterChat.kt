package com.example.kerklyv5.controlador

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.paris.extensions.*
import com.example.kerklyv5.R
import com.example.kerklyv5.modelo.Mensaje

class AdapterChat(c: Context): RecyclerView.Adapter<AdapterChat.ViewHolder>() {

    private var lista = ArrayList<Mensaje>()
    var context = c

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

       val txt_mensaje = view.findViewById<TextView>(R.id.txt_mensaje_chat)

        val txt_fecha = view.findViewById<TextView>(R.id.txt_fechaMensaje_chat)

        var layout = view.findViewById<LinearLayout>(R.id.layout_mensaje_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_mensaje, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_mensaje.text = lista[position].mensaje
        holder.txt_fecha.text = lista[position].hora

        var tipo_usuario = lista[position].tipo_usuario.trim()

        if (tipo_usuario == "Ker") {

            holder.layout.style {
                this.backgroundRes(R.drawable.burbuja_chat_der)
                this.layoutGravity(Gravity.END)
            }

            if (position > 0) {
                if (tipo_usuario == lista[position-1].tipo_usuario.trim()) {
                    holder.layout.style {
                        this.layoutMarginTopDp(5)
                    }
                } else {
                    holder.layout.style {
                        this.layoutMarginTopDp(20)
                    }
                }
            }

        } else if (tipo_usuario == "Kerkly") {

            holder.layout.style {
                this.backgroundRes(R.drawable.burbuja_chat)
                this.layoutGravity(Gravity.START)
            }

            if (position > 0) {
                if (tipo_usuario == lista[position-1].tipo_usuario.trim()) {
                    holder.layout.style {
                        this.layoutMarginTopDp(5)
                    }
                } else {
                    holder.layout.style {
                        this.layoutMarginTopDp(20)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun addMensaje(m: Mensaje) {
        lista.add(m)
        notifyItemInserted(lista.size)
    }
}