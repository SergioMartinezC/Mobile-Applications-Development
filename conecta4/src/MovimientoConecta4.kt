import es.uam.eps.multij.Jugador
import es.uam.eps.multij.Movimiento

/*He decidido asignar a un movimiento la columna elegida y el jugador que ha hecho el movimiento*/
public class MovimientoConecta4(var columna: Int) : Movimiento() {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MovimientoConecta4) {
            return false
        }
        return (other.columna == columna)
    }

    override fun toString(): String {
        return "C-$columna"
    }

}