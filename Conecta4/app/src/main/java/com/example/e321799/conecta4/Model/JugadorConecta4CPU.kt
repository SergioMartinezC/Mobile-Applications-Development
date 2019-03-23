package com.example.e321799.conecta4.Model

import es.uam.eps.multij.Evento
import es.uam.eps.multij.JugadorAleatorio

import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_pregame_view.view.*

class JugadorConecta4CPU(nombre: String?) : JugadorAleatorio(nombre) {
    var drawable : Int = R.drawable.token_red_48dp
}