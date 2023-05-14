package janorschke.meyer.game.service.model.piece.lineMoving

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.model.piece.Piece
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.piece.PieceInfo
import janorschke.meyer.game.service.utils.board.PiecePosition
import janorschke.meyer.game.service.validator.FieldValidator

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
    protected fun possibleMovesOnDiagonalLine(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row + i, currentPosition.col + i)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row + i, currentPosition.col - i)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row - i, currentPosition.col + i)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row - i, currentPosition.col - i)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
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
    protected fun possibleMovesOnStraightLine(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row + row, currentPosition.col)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row - row, currentPosition.col)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row, currentPosition.col + col)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(currentPosition.row, currentPosition.col - col)
            if (addPosition(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)) break
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
            currentPosition: PiecePosition,
            possiblePosition: PiecePosition,
            possibleMoves: MutableList<PiecePosition>,
            disableCheckCheck: Boolean
    ): Boolean {
        if (isFieldUnavailable(board, possiblePosition)) return true

        addPossibleMove(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
        return FieldValidator.isOpponent(board, color, possiblePosition)
    }
}