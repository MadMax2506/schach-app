package janorschke.meyer.game.board

import janorschke.meyer.game.piece.model.Piece

/**
 * Provides the history of all board moves and the beaten pieces by a move
 */
class BoardHistory {
    private val beatenPieces: MutableList<Piece> = mutableListOf()
    private val history: MutableList<BoardMove> = mutableListOf()

    /**
     * @return number of moves
     */
    fun numberOfMoves(): Int = history.size

    /**
     * @return history of the moves as list
     */
    fun getMoves(): MutableList<BoardMove> = history

    /**
     * @param index of the move
     * @return move ons the related place
     */
    fun getMove(index: Int): BoardMove = history[index]

    /**
     * @param n number of moves
     * @return the n last moves
     */
    fun getLastMoves(n: Int): List<BoardMove> = history.slice(IntRange(numberOfMoves() - 1 - n, numberOfMoves() - 1))

    /**
     * Add a new move to the history
     *
     * @param move of a piece
     */
    fun push(move: BoardMove) {
        if (move.toPiece != null) beatenPieces.add(move.toPiece)
        history.add(move)
    }

    /**
     * @return the last move and remove it from the history
     */
    fun undo(): BoardMove {
        val move = history.removeLast()
        if (move.toPiece != null) beatenPieces.removeLast()
        return move
    }

    /**
     * Resets the board to the initial state
     */
    fun reset() {
        beatenPieces.clear()
        history.clear()
    }
}