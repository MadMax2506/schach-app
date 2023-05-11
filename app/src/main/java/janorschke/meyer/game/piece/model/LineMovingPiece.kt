package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.utils.PiecePosition

abstract class LineMovingPiece(color: PieceColor, pieceInfo: PieceInfo) : Piece(color, pieceInfo) {

    /**
     * @param position current position
     * @return possible moves on the four diagonal line
     */
    protected fun possibleMovesOnDiagonalLine(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + i, position.col + i)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + i, position.col - i)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - i, position.col + i)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - i, position.col - i)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        return possibleMoves
    }

    fun possibleMovesOnStraightLine(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + row, position.col)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - row, position.col)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row, position.col + col)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row, position.col - col)
            if (addPosition(board, position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        return possibleMoves
    }

    /**
     * @param currentPosition the position of the piece
     * @param possiblePosition a possible of the piece
     * @param possibleMoves
     * @param disableCheckCheck disables the check of a Check in Chess
     * @return true if there are no further possible moves
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
        return fieldValidator.isOpponent(board, possiblePosition)
    }
}