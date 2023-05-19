package janorschke.meyer.service.model.game.board

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.piece.Piece

/**
 * Provides the history of all board moves and the beaten pieces by a move
 */
class History {
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
    fun getLastMoves(n: Int) = moves.slice(IntRange(numberOfMoves() - 1 - n, numberOfMoves() - 1)).toMutableList()

    /**
     * @param color of the pieces
     * @return all beaten pieces for a color
     */
    fun getBeatenPieces(color: PieceColor) = beatenPieces.filter { it.color == color }.toMutableList()

    /**
     * Add a new move to the history
     *
     * @param move of a piece
     */
    fun push(move: Move) {
        if (move.toPiece != null) beatenPieces.also { it.add(move.toPiece) }.sortByDescending { it.pieceInfo.valence }
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