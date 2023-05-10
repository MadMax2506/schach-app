package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.validator.BoardValidator
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
        protected var moved: Boolean = false
) {
    protected val fieldValidator = FieldValidator(color, board)

    /**
     * Marks the piece as moved
     */
    fun move() {
        moved = true
    }

    /**
     * @return true, if the piece has moved
     */
    fun hasMoved(): Boolean = moved

    /**
     * @param ownPosition position of the current piece
     * @param kingPosition position of the opponent king
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        return this.possibleMoves(ownPosition, true).contains(kingPosition)
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
    protected fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidator.isInBound(position) || fieldValidator.isTeammate(position)
    }

    protected fun addMoves(disableCheckCheck: Boolean,
                           position: PiecePosition,
                           possiblePosition: PiecePosition,
                           possibleMoves: MutableList<PiecePosition>) {
        if (!disableCheckCheck) {
            Board(board).apply {
                this.createBoardMove(position, possiblePosition)
                if (!BoardValidator.isKingInCheck(this, color)) possibleMoves.add(possiblePosition)
            }
        } else {
            possibleMoves.add(possiblePosition)
        }
    }
}