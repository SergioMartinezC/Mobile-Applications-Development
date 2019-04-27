import com.example.e321799.conecta4.activities.MainActivity
import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import java.io.Serializable
import java.util.ArrayList

/**
 * Representacion de el tablero de nuestro juego
 *
 *
 *
 * @constructor Crea un tablero
 */
class TableroConecta4() : Tablero(), Serializable {
    constructor(tablero: MutableList<MutableList<Int>>, turno: Int, ultimoMovimiento: MovimientoConecta4, fichas: String, nJugadas: Int) : this() {
        this.turno = turno
        this.ultimoMovimiento = ultimoMovimiento /* Último movimiento*/

        fichasEnColumna = (fichas.split(",").associate {
            val (left, right) = it.split("=")
            left.toInt() to right.toInt()
        }).toMutableMap()
        this.numJugadas = nJugadas
    }

    val tablero = mutableListOf<MutableList<Int>>() /* Doble array que representa el tablero con las fichas del juego*/
    val NUM_COLUMNAS: Int = 7 /*Numero de columnas del tablero*/
    val NUM_FILAS: Int = 6 /*Numero de filas del tablero*/
    val JUGADOR_1 = 1 /*Valor dentro del array que representa una ficha del jugador 1*/
    val JUGADOR_2 = 2 /*Valor dentro del array que representa una ficha del jugador 2*/
    val CASILLA_VACIA = 0 /*Valor dentro del array que representa una ficha que no pertenece a ningún jugador*/
    var movimientosValidos: ArrayList<Movimiento> = ArrayList() /*Lista de movimientos validos*/
    var fichasEnColumna = mutableMapOf<Int, Int>() /*Mapa que relaciona la columna con el número de fichas que hay en ella*/
    init {
        estado = EN_CURSO
        /* Ponemos a 0 (vacio) todas las casillas*/
        for (i in 0 until NUM_FILAS) {
            var columna = mutableListOf<Int>()
            for (j in 0 until NUM_COLUMNAS) {
                columna.add(0)
            }
            tablero.add(columna)
        }
        for (i in 0 until NUM_COLUMNAS) {
            fichasEnColumna.put(i,NUM_FILAS)
        }
        this.calcularMovimientosValidos()
    }

    /**
     * Guarda en la lista de movimientos validos todos los movimientos validos
     * que se calculan a partir de las fichas que hay en cada columna
     */
    fun calcularMovimientosValidos() {
        for (columna in 0 until NUM_COLUMNAS) {
            if(fichasEnColumna.get(columna) != 0) {
                movimientosValidos.add(MovimientoConecta4(columna))
            }
        }
    }

    /**
     * Representa gráficamente el tablero
     * @return Un string que representa nuestro tablero
     */
    override fun toString(): String {
        var ret: String?
        ret = "    1   2   3   4   5   6   7  \n"
        ret += "  +---+---+---+---+---+---+---+\n"
        for (i in 0 until NUM_FILAS) {
            for (j in 0 until NUM_COLUMNAS) {
                when (j) {
                    0 -> ret += "  | $tablero[i][j] "

                    NUM_COLUMNAS - 1 -> {
                        ret += "| $tablero[i][j] |\n"
                        ret += "  +---+---+---+---+---+---+---+\n"
                    }
                    else ->
                        ret += "| $tablero[i][j] "
                }
            }
        }
            return ret
    }


    /**
     * Realiza un movimiento si el movimiento [m] es válido
     *
     */
    override fun mueve(m: Movimiento?) {
        if (m is MovimientoConecta4) {
            tablero[fichasEnColumna.getValue(m.columna)-1][m.columna] = this.turno + 1
            fichasEnColumna.set(m.columna, fichasEnColumna.getValue(m.columna).dec())
            ultimoMovimiento = m
            if(this.comprobarGanador(tablero) != 0 ){
                this.cambiaEstado(Tablero.FINALIZADA)
                return
            }
            else if (fichasEnColumna.getValue(m.columna) == 0) {
                movimientosValidos.remove(m)
                if (movimientosValidos.size == 0) {
                    this.cambiaEstado(Tablero.TABLAS)
                }
            }
            this.cambiaTurno()
        }
    }

    /**
     * Comprueba si el movimiento [m] es valido
     * @return true si el moviemto es valido, false si no
     */
    override fun esValido(m: Movimiento?): Boolean {
        if (m is MovimientoConecta4) {
            return m in movimientosValidos()
        }
        return false
    }

    /**
     * Devuelve la lista de movimientos validos
     * @return Lista de movimientos validos
     */
    override fun movimientosValidos(): ArrayList<Movimiento> {
        return movimientosValidos
    }

    /**
     * Covierte [cadena] en una representacion del tablero
     */
    override fun stringToTablero(cadena: String?) {
        if(cadena?.length == NUM_COLUMNAS * NUM_FILAS) {
            for (fila in 0 until NUM_FILAS) {
                for (columna in 0 until NUM_COLUMNAS) {
                    this.tablero[fila][columna] = cadena[fila * NUM_COLUMNAS + columna].toInt() - 48
                }
            }
        }
        this.calcularMovimientosValidos()
    }

    /**
     * Convierte el array de ints que representa el tablero a un string
     * @return El string que representa el tablero
     */
    override fun tableroToString(): String {
        var guardar = String()
        for (fila in 0 until NUM_FILAS)  {
            for (columna in 0 until NUM_COLUMNAS) {
                guardar += tablero[fila][columna]
            }
        }
        return guardar
    }

    /**
     * Reinicia el tablero poniendo todas las casillas vacías
     * @return true si lo hace correctamente, false si no
     */
    override fun reset(): Boolean {
        for (fila in 0 until NUM_FILAS) {
            for (columna in 0 until NUM_COLUMNAS) {
                this.tablero[fila][columna] = CASILLA_VACIA
            }
        }
        for (i in 0 until NUM_COLUMNAS) {
            fichasEnColumna.put(i,NUM_FILAS)
        }
        return super.reset()
    }

    /**
     * Comprueba si un jugador ha juntado 4 fichas en línea en el tablero [tablero]
     * @return Número del jugador que ha ganado, 0 si tablas
     */
    fun comprobarGanador(tablero: MutableList<MutableList<Int>>): Int {
        val FILA = tablero.size
        val COLUMNA = tablero[0].size
        val CASILLA_VACIA = 0
        for (fila in 0 until NUM_FILAS) {
            for (columna in 0 until NUM_COLUMNAS) {
                /*Guardamos el valor de la ficha y a qué jugador pertenece*/
                var jugador = tablero[fila][columna]
                /*No nos interesa comprobar casilals vacías*/
                if (jugador != CASILLA_VACIA) {
                    /*Comprobamos en horizontal*/
                    if (columna + 3 < COLUMNA &&
                        jugador == tablero[fila][columna + 1] &&
                        jugador == tablero[fila][columna + 2] &&
                        jugador == tablero[fila][columna + 3])
                        return jugador
                    /*Comprobamos en vertical*/
                    if (fila + 3 < FILA) {
                        if (jugador == tablero[fila + 1][columna] &&
                            jugador == tablero[fila + 2][columna] &&
                            jugador == tablero[fila + 3][columna])
                            return jugador
                        if (columna + 3 < COLUMNA &&
                            jugador == tablero[fila + 1][columna + 1] &&
                            jugador == tablero[fila + 2][columna + 2] &&
                            jugador == tablero[fila + 3][columna + 3])
                            return jugador
                        if (columna - 3 >= 0 &&
                            jugador == tablero[fila + 1][columna - 1] &&
                            jugador == tablero[fila + 2][columna - 2] &&
                            jugador == tablero[fila + 3][columna - 3])
                            return jugador
                    }
                }
            }
        }
        return CASILLA_VACIA /*No gana nadie*/
    }

    /**
     * Cambia el estado de la partida por [estado]
     */
    fun cambiaEstado(estado: Int) {
        this.estado = estado
    }
}