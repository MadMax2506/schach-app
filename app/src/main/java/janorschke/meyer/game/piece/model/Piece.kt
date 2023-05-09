package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.validator.FieldValidator

/**
 * Represents a chess piece
 */
abstract class Piece(
        protected val board: Board,
        /**
         * Specifies piece color
         */
        val color: PieceColor,
        /**
         * Static piece informations
         */
        val pieceInfo: PieceInfo,
        /**
         * If true, the piece has already moved
         */
        var moved: Boolean = false
) {
    protected val fieldValidator = FieldValidator(this, board)

    /**
     * Marks the piece as moved
     */
    fun move() {
        moved = true
    }

    /**
     * @param position current position
     * @param disableCheckCheck disables the check of a Check in Chess (optional: Default = false)
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean = false): MutableList<PiecePosition>

    /**
     * @return true, if you're not allowed to go to that position
     */
    fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidator.isInBound(position) || fieldValidator.isTeammate(position)
    }
}