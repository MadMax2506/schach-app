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
     * @param position current position
     * @param disableCheckCheck disables the check of a Check in Chess (optional: Default = false)
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean = false): MutableList<PiecePosition>

    /**
     * @param ownPosition position of the current piece
     * @param kingPosition position of the opponent king
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        val possibleMoves = this.possibleMoves(ownPosition, true)
        return possibleMoves.contains(kingPosition)
    }

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
     * @return true, if you're not allowed to go to that position
     */
    protected fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidator.isInBound(position) || fieldValidator.isTeammate(position)
    }

    /**
     * Add the possible move if it is a valid move
     *
     * @param disableCheckCheck disables the check of a Check in Chess
     * @param currentPosition of the piece
     * @param possiblePosition of the piece
     * @param possibleMoves already added moves
     */
    protected fun addPossibleMove(
            currentPosition: PiecePosition,
            possiblePosition: PiecePosition,
            possibleMoves: MutableList<PiecePosition>,
            disableCheckCheck: Boolean,
    ) {
        if (disableCheckCheck) {
            possibleMoves.add(possiblePosition)
            return
        }

        Board(board).apply {
            this.createBoardMove(currentPosition, possiblePosition)
            if (!BoardValidator.isKingInCheck(this, color)) possibleMoves.add(possiblePosition)
        }
    }
}