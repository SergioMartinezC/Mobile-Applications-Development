import es.uam.eps.multij.Evento
import es.uam.eps.multij.PartidaListener
import TableroConecta4
import es.uam.eps.multij.AccionMover
import es.uam.eps.multij.Partida

/*Simplemente he creado el observador pero no sé como implementarlo, de momento solo printea cuando cambia algo en
* la partida*/
class ObservadorConecta4() :PartidaListener {
    override fun onCambioEnPartida(evento: Evento?) {
        when(evento?.tipo) {
            Evento.EVENTO_CAMBIO -> {
                println(evento.partida.tablero)
                println(evento.descripcion)

            }
            Evento.EVENTO_FIN -> {
                println(evento.partida.tablero)
                println(evento.descripcion)
            }

        }
    }
}