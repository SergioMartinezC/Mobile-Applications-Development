import es.uam.eps.multij.*
import java.io.File

class AccionGuardar(var jugador: Jugador): Accion {

    override fun ejecuta(p: Partida) {
        val t = p.tablero
        if (t is TableroConecta4) {

        }
    }

    override fun getOrigen(): Jugador {
        return jugador
    }

    override fun requiereConfirmacion(): Boolean {
        return false
    }
}