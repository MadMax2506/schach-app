package janorschke.meyer.game.piece

import janorschke.meyer.game.board.Board

abstract class Piece(protected val board: Board, val color: PieceColor) {
    protected var moved: Boolean = false
    protected val fieldValidation = FieldValidation(this, board)

    /**
     * Marks the piece as moved
     */
    fun move() {
        moved = true
    }

    /**
     * @return the image id for the piece
     */
    abstract fun getImageId(): Int

    /**
     * @param position current position
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition>

    abstract fun isFieldUnavailable(position: PiecePosition): Boolean
}