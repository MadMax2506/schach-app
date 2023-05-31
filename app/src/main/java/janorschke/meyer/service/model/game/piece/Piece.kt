package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition
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
        val pieceInfo: PieceInfo
) {
    /**
     * @param board instance
     * @param currentPosition of the piece
     * @param disableCheckCheck disables the check is the king is in check
     *
     * @return possible moves
     */
    protected abstract fun possibleMoves(
            board: Board,
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean,
            history: History
    ): MutableList<PiecePosition>

    /**
     * @param board instance
     * @param ownPosition of the piece
     * @param kingPosition of the opponent king
     *
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(
            board: Board,
            history: History,
            kingPosition: PiecePosition,
            ownPosition: PiecePosition
    ): Boolean {
        val possibleMoves = this.possibleMoves(board, ownPosition, true, history)
        return possibleMoves.contains(kingPosition)
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return possible moves
     */
    fun possibleMoves(
            board: Board,
            currentPosition: PiecePosition,
            history: History
    ): MutableList<PiecePosition> {
        return possibleMoves(board, currentPosition, false, history)
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return true, if you're not allowed to go to that position
     */
    protected fun isFieldUnavailable(board: Board, currentPosition: PiecePosition): Boolean {
        return !FieldValidator.isInBound(currentPosition) || FieldValidator.isTeammate(board, color, currentPosition)
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
            history: History
    ) {
        if (disableCheckCheck) {
            possibleMoves.add(possiblePosition)
            return
        }

        Board(board).let { boardCopy ->
            boardCopy.createMove(currentPosition, possiblePosition)
            if (!BoardValidator.isKingInCheck(boardCopy, color, history)) possibleMoves.add(possiblePosition)
        }
    }
}