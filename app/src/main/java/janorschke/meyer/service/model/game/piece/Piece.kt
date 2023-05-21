package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.utils.board.PiecePosition
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator

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
    /**
     * @param board instance
     * @param currentPosition of the piece
     * @param disableCheckCheck disables the check is the king is in check
     *
     * @return possible moves
     */
    protected abstract fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition>

    /**
     * @param board instance
     * @param ownPosition of the piece
     * @param kingPosition of the opponent king
     *
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(board: Board, ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        val possibleMoves = this.possibleMoves(board, ownPosition, true)
        return possibleMoves.contains(kingPosition)
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return possible moves
     */
    fun possibleMoves(board: Board, currentPosition: PiecePosition): MutableList<PiecePosition> {
        return possibleMoves(board, currentPosition, false)
    }

    /**
     * Marks the piece as moved
     */
    fun markAsMove() {
        moved = true
    }

    /**
     * @return true, if the piece has moved
     */
    fun hasMoved(): Boolean = moved

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return true, if you're not allowed to go to that position
     */
    protected fun isFieldUnavailable(board: Board, currentPosition: PiecePosition): Boolean {
        return !FieldValidator.isInBound(board, currentPosition) || FieldValidator.isTeammate(board, color, currentPosition)
    }

    /**
     * Add the possible move if it is a valid move to the possible moves list
     *
     * @param board instance
     * @param currentPosition of the piece
     * @param possiblePosition of the piece
     * @param possibleMoves already added moves
     * @param disableCheckCheck disables the check is the king is in check
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

        Board(board).let { boardCopy ->
            boardCopy.createMove(currentPosition, possiblePosition)
            if (!BoardValidator.isKingInCheck(boardCopy, color)) possibleMoves.add(possiblePosition)
        }
    }
}