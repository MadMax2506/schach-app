package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.validator.BoardValidator
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.utils.PiecePosition
import janorschke.meyer.game.piece.validator.FieldValidator

/**
 * Represents a chess piece
 */
abstract class Piece(
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
    protected val fieldValidator = FieldValidator(color)

    /**
     * @param board current board instance
     * @param position current position
     * @param disableCheckCheck disables the check of a Check in Chess (optional: Default = false)
     * @return possible moves
     */
    abstract fun possibleMoves(board: Board, position: PiecePosition, disableCheckCheck: Boolean = false): MutableList<PiecePosition>

    /**
     * @param board current board instance
     * @param ownPosition position of the current piece
     * @param kingPosition position of the opponent king
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(board: Board, ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        val possibleMoves = this.possibleMoves(board, ownPosition, true)
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
    protected fun isFieldUnavailable(board: Board, position: PiecePosition): Boolean {
        return !fieldValidator.isInBound(board, position) || fieldValidator.isTeammate(board, position)
    }

    /**
     * Add the possible move if it is a valid move
     *
     * @param board current board instance
     * @param currentPosition of the piece
     * @param possiblePosition of the piece
     * @param possibleMoves already added moves
     * @param disableCheckCheck disables the check of a Check in Chess
     */
    protected fun addPossibleMove(
            board: Board,
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