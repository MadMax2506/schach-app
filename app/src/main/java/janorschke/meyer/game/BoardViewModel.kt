package janorschke.meyer.game

import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {

    val boardSize: Int = 64
    private val board = Array(8) { Array(8) { PieceType.NONE } }

    init {
        resetBoard()
    }

    private fun resetBoard() {
        // TODO Weiße und Schwarze Figuren initialisieren / in Startposition setzen
    }

    fun getBoard(): Array<Array<PieceType>> {
        return this.board
    }

    fun movePiece(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        // TODO Methoden movePawn, moveBishop ...
    }

    fun getFieldContent(position: Int): PieceType {
        val row = position / 8
        val col = position % 8
        return board[row][col]
    }

    fun onFieldClicked(position: Int) {
        // TODO Validierung, ob ein eigenes Piece angeklickt => in einen State behalten und beim nächsten klick schauen, ob der Move valide ist
    }

    // TODO Spiellogik Methoden einbauen
}
