import es.uam.eps.multij.*


class AccionSalir(var jugador: Jugador): Accion {

    override fun ejecuta(p: Partida?) {
        val t = p?.tablero
        if (t is TableroConecta4)
            t.salir()
    }

    override fun getOrigen(): Jugador {
        return jugador
    }

    override fun requiereConfirmacion(): Boolean {
        return true
    }
}