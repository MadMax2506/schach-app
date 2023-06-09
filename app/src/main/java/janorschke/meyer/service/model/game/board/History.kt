package janorschke.meyer.service.model.game.board

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.piece.Piece

/**
 * Provides the history of all board moves and the beaten pieces by a move
 */
class History {
    val numberOfMoves get() = moves.size

    private val beatenPieces: MutableList<Piece>
    private val moves: MutableList<Move>

    constructor() {
        beatenPieces = mutableListOf()
        moves = mutableListOf()
    }

    // Copy Constructor
    constructor(history: History) {
        this.beatenPieces = history.beatenPieces.toMutableList()
        this.moves = history.moves.toMutableList()
    }

    fun getMoves() = moves.toMutableList()

    /**
     * @param n number of moves
     * @return the n last moves
     */
    fun getLastMoves(n: Int) = moves.takeLast(n).toMutableList()

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
        if (move.beaten.piece != null) beatenPieces.also { it.add(move.beaten.requiredPiece) }.sortByDescending { it.pieceInfo.valence }
        moves.add(move)
    }
}