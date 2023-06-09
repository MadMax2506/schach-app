package janorschke.meyer.service.model.game.piece.lineMoving

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.validator.FieldValidator

/**
 * Pieces which will moving on the diagonal and straight lines
 */
abstract class LineMovingPiece(color: PieceColor, pieceInfo: PieceInfo) : Piece(color, pieceInfo) {

    /**
     * @param board instance
     * @param currentPosition of the piece
     * @param disableCheckCheck disables the check is the king is in check
     *
     * @return possible moves on the two diagonal lines
     */
    protected fun possibleMovesOnDiagonalLine(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row + i, currentPosition.col + i)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row + i, currentPosition.col - i)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row - i, currentPosition.col + i)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row - i, currentPosition.col - i)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        return possibleMoves
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     * @param disableCheckCheck disables the check is the king is in check
     *
     * @return possible moves on the two straight lines
     */
    protected fun possibleMovesOnStraightLine(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row + row, currentPosition.col)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row - row, currentPosition.col)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row, currentPosition.col + col)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = Position(currentPosition.row, currentPosition.col - col)
            if (addPosition(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        return possibleMoves
    }

    /**
     * @param board instance
     * @param currentPosition of the piece
     * @param possiblePosition of the piece
     * @param possibleMoves already added moves
     * @param disableCheckCheck disables the check is the king is in check
     *
     * @return true, if there are no further possible moves
     */
    private fun addPosition(
            board: Board,
            history: History,
            currentPosition: Position,
            possiblePosition: Position,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean
    ): Boolean {
        if (isFieldUnavailable(board, possiblePosition)) return true

        addPossibleMove(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
        return FieldValidator.isOpponent(board, color, possiblePosition)
    }
}