package janorschke.meyer.service.model.game.board

import janorschke.meyer.enums.PieceColor
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

    fun reset() {
        beatenPieces.clear()
        moves.clear()
    }

    fun getMoves() = moves.toMutableList()

    /**
     * @param n number of moves
     * @return the n last moves
     */
    fun getLastMoves(n: Int) = moves.slice(IntRange(numberOfMoves - n, numberOfMoves - 1)).toMutableList()

    fun getBeatenPieces() = beatenPieces.toMutableList()

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
        move.toPiece.let { piece ->
            if (piece != null) beatenPieces.also { it.add(piece) }.sortByDescending { it.pieceInfo.valence }
        }
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