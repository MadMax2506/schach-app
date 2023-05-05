package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

/**
 * Represents a chess piece
 */
abstract class Piece(
        protected val boardViewModel: BoardViewModel,
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
    protected val fieldValidation = FieldValidation(this, boardViewModel)

    /**
     * Marks the piece as moved
     */
    fun move() {
        moved = true
    }

    /**
     * @param position current position
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition): MutableList<PiecePosition>

    fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidation.isInBound(position) || fieldValidation.isTeammate(position)
    }
}