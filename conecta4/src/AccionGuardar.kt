import es.uam.eps.multij.*


class AccionGuardar(var jugador: Jugador): Accion {

    override fun ejecuta(p: Partida?) {
        val t = p?.tablero
        if (t is TableroConecta4) {
            t.tableroToString()
            MenuConecta4().menuPrincipal(MenuConecta4().getOpcion())
        }
    }

    override fun getOrigen(): Jugador {
        return jugador
    }

    override fun requiereConfirmacion(): Boolean {
        return true
    }
}