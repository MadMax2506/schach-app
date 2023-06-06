package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.PossibleMove
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
            history: History,
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove>

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
        return possibleMoves(board, history, ownPosition, true)
                .map { it.beatenPiecePosition }
                .contains(kingPosition)
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return possible moves
     */
    fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: PiecePosition
    ): MutableList<PossibleMove> {
        return possibleMoves(board, history, currentPosition, false)
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
            history: History,
            currentPosition: PiecePosition,
            possiblePosition: PiecePosition,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean,
            isEnPassant: Boolean = false
    ) {
        if (disableCheckCheck) {
            possibleMoves.add(board.createPossibleMove(currentPosition, possiblePosition, isEnPassant = isEnPassant))
            return
        }

        Board(board).let { boardCopy ->
            History(history).let { historyCopy ->
                boardCopy.createMove(currentPosition, possiblePosition, isEnPassant = isEnPassant).let { historyCopy.push(it) }
                if (!BoardValidator.isKingInCheck(boardCopy, historyCopy, color))
                    possibleMoves.add(board.createPossibleMove(currentPosition, possiblePosition, isEnPassant = isEnPassant))
            }
        }
    }
}