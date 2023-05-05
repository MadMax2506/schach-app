package janorschke.meyer.game.piece

import janorschke.meyer.game.GameViewModel

abstract class Piece(protected val gameViewModel: GameViewModel, val color: PieceColor) {
    protected var moved: Boolean = false
    protected val fieldValidation = FieldValidation(this, gameViewModel)

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