import es.uam.eps.multij.Movimiento

/**
 * Representacion de los movimientos de nuestro juego
 *
 *
 *
 * @property columna Nombre del jugador.
 * @constructor Crea un movimiento con la clumna
 */
class MovimientoConecta4(var columna: Int) : Movimiento() {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MovimientoConecta4) {
            return false
        }
        return (other.columna == columna)
    }
    /**
     * Convierte la informacion de la clase en string
     * @return La información del movimiento
     */
    override fun toString(): String {
        return "C-${columna + 1}"
    }

}