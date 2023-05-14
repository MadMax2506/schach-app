package janorschke.meyer.game.service.model

import janorschke.meyer.game.service.model.piece.Piece
import janorschke.meyer.game.service.model.piece.PieceColor

/**
 * Provides the history of all board moves and the beaten pieces by a move
 */
object History {
    private val beatenPieces: MutableList<Piece> = mutableListOf()
    private val moves: MutableList<Move> = mutableListOf()

    fun reset() {
        beatenPieces.clear()
        moves.clear()
    }

    /**
     * @return number of moves
     */
    fun numberOfMoves(): Int = moves.size

    fun getMoves() = moves

    /**
     * @param n number of moves
     * @return the n last moves
     */
    fun getLastMoves(n: Int): MutableList<Move> = moves.slice(IntRange(numberOfMoves() - 1 - n, numberOfMoves() - 1)).toMutableList()

    /**
     * @param color of the pieces
     * @return number of beaten pieces
     */
    fun numberOfBeatenPieceByColor(color: PieceColor): Int = getBeatenPiecesByColor(color).size

    /**
     * @param color of the pieces
     * @return all beaten pieces for a color
     */
    fun getBeatenPiecesByColor(color: PieceColor): MutableList<Piece> = beatenPieces.filter { it.color == color }.toMutableList()

    /**
     * Add a new move to the history
     *
     * @param move of a piece
     */
    fun push(move: Move) {
        if (move.toPiece != null) beatenPieces.add(move.toPiece)
        moves.add(move)
    }

    /**
     * @return the last move and remove it from the history
     */
    fun undo(): Move {
        val move = moves.removeLast()
        if (move.toPiece != null) beatenPieces.removeLast()
        return move
    }
}