package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

abstract class LineMovingPiece(board: Board, color: PieceColor, pieceInfo: PieceInfo) : Piece(board, color, pieceInfo) {

    /**
     * @param position current position
     * @return possible moves on the four diagonal line
     */
    protected fun possibleMovesOnDiagonalLine(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + i, position.col + i)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + i, position.col - i)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - i, position.col + i)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - i, position.col - i)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        return possibleMoves
    }

    fun possibleMovesOnStraightLine(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row + row, position.col)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row - row, position.col)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row, position.col + col)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val possiblePosition = PiecePosition(position.row, position.col - col)
            if (addPosition(position, possiblePosition, possibleMoves, disableCheckCheck)) break
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
            currentPosition: PiecePosition,
            possiblePosition: PiecePosition,
            possibleMoves: MutableList<PiecePosition>,
            disableCheckCheck: Boolean
    ): Boolean {
        if (isFieldUnavailable(possiblePosition)) return true

        addPossibleMove(currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
        return fieldValidator.isOpponent(possiblePosition)
    }
}