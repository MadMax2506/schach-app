package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Castling
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator

/**
 * Represents a chess piece
 */
abstract class Piece(val color: PieceColor, val pieceInfo: PieceInfo) {
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
            currentPosition: Position,
            disableCheckCheck: Boolean,
            disableCastlingCheck: Boolean
    ): MutableList<PossibleMove>

    /**
     * @param board instance
     * @param history instance
     * @param kingPosition of the opponent king
     * @param ownPosition of the piece
     *
     * @return true, if the piece gives check to the opponent king
     */
    open fun givesOpponentKingCheck(
            board: Board,
            history: History,
            kingPosition: Position,
            ownPosition: Position
    ): Boolean {
        return possibleMoves(board, history, ownPosition, true, true)
                .map { it.beaten.position }
                .contains(kingPosition)
    }

    /**
     * @param board instance
     * @param history instance
     * @param currentPosition of the piece
     *
     * @return possible moves
     */
    fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: Position
    ): MutableList<PossibleMove> {
        return possibleMoves(board, history, currentPosition, false, false)
    }

    /**
     * @param history instance
     * @return `true`, if the [Piece] has been moved before
     */
    fun hasMoved(history: History) = history.getMoves().any { it.from.piece == this }

    /**
     * @param board instance
     * @param currentPosition of the piece
     *
     * @return true, if you're not allowed to go to that position
     */
    protected fun isFieldUnavailable(board: Board, currentPosition: Position): Boolean {
        return !FieldValidator.isInBound(currentPosition) || FieldValidator.isTeammate(board, color, currentPosition)
    }

    /**
     * Add the possible move if it is a valid move to the possible moves list
     *
     * @param board instance
     * @param history instance
     * @param currentPosition of the piece
     * @param possiblePosition of the piece
     * @param possibleMoves already added moves
     * @param disableCheckCheck disables the check is the king is in check
     * @param isEnPassant indicates if the move is en passant
     * @param castling information
     */
    protected fun addPossibleMove(
            board: Board,
            history: History,
            currentPosition: Position,
            possiblePosition: Position,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean,
            isEnPassant: Boolean = false,
            castling: Castling? = null
    ) {
        if (disableCheckCheck) {
            possibleMoves.add(board.createPossibleMove(currentPosition, possiblePosition, isEnPassant = isEnPassant))
            return
        }

        Board(board).let { boardCopy ->
            History(history).let { historyCopy ->
                val move = boardCopy.createMove(
                        fromPosition = currentPosition,
                        toPosition = possiblePosition,
                        isEnPassant = isEnPassant,
                        castling = castling
                )
                historyCopy.push(move)

                if (BoardValidator.isKingInCheck(boardCopy, historyCopy, color)) return
                possibleMoves.add(PossibleMove(move))
            }
        }
    }
}