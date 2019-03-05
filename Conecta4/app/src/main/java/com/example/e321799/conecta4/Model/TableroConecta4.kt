import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import java.util.ArrayList

/*Para el tablero he estado buscando una forma de hacer un array bidimensional y solo me ha aparecido esto
* No sé si se puede hacer para definir el tamaño directamente y tenerlo controlado
* La función mueve simplemente usará el movimiento (columna-jugador) para decir qué ficha poner y donde*/
class TableroConecta4(var tablero: MutableList<MutableList<Int>>) : Tablero() {
    constructor(tablero: MutableList<MutableList<Int>>, turno: Int, ultimoMovimiento: MovimientoConecta4, fichas: String, nJugadas: Int) : this(tablero) {
        this.turno = turno
        this.ultimoMovimiento = ultimoMovimiento

        fichasEnColumna = (fichas.split(",").associate {
            val (left, right) = it.split("=")
            left.toInt() to right.toInt()
        }).toMutableMap()
        this.numJugadas = nJugadas
    }

    val NUM_COLUMNAS: Int = 7
    val NUM_FILAS: Int = 6
    val JUGADOR_1 = 1
    val JUGADOR_2 = 2
    val CASILLA_VACIA = 0
    var ganador = 0
    var movimientosValidos: ArrayList<Movimiento> = ArrayList()
    var fichasEnColumna = mutableMapOf<Int, Int>()
    init {
        estado = EN_CURSO
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

    fun calcularMovimientosValidos() {
        for (columna in 0 until NUM_COLUMNAS) {
            if(fichasEnColumna.get(columna) != 0) {
                movimientosValidos.add(MovimientoConecta4(columna))
            }
        }
    }


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

    fun salir() {
        this.cambiaEstado(Tablero.TABLAS)
    }

    override fun esValido(m: Movimiento?): Boolean {
        if (m is MovimientoConecta4) {
            return m in movimientosValidos()
        }
        return false
    }

    override fun movimientosValidos(): ArrayList<Movimiento> {
        return movimientosValidos
    }

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

    override fun tableroToString(): String {
        var guardar = String()
        for (fila in 0 until NUM_FILAS)  {
            for (columna in 0 until NUM_COLUMNAS) {
                guardar += tablero[fila][columna]
            }
        }
        return guardar
    }

    /*fun convertirFicha(turno: Int): Char? {
        when(turno) {
            0 -> return CASILLA_VACIA
            1 -> return JUGADOR_1
            2 -> return JUGADOR_2
            else -> return null
        }
    }*/

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

    fun cambiaEstado(estado: Int) {
        this.estado = estado
    }
}