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
            val currentPosition = PiecePosition(position.row + i, position.col + i)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + i, position.col - i)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col + i)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col - i)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        return possibleMoves
    }

    fun possibleMovesOnStraightLine(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + row, position.col)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - row, position.col)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row, position.col + col)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row, position.col - col)
            if (addPosition(disableCheckCheck, position, currentPosition, possibleMoves)) break
        }

        return possibleMoves
    }

    /**
     * @param disableCheckCheck
     * @param position the position of the piece
     * @param possiblePosition a possible of the piece
     * @param possibleMoves
     * @return true if there are no further possible moves
     */
    private fun addPosition(disableCheckCheck: Boolean,
                            position: PiecePosition,
                            possiblePosition: PiecePosition,
                            possibleMoves: MutableList<PiecePosition>): Boolean {
        if (isFieldUnavailable(possiblePosition)) return true

        addMoves(disableCheckCheck, position, possiblePosition, possibleMoves)
        return fieldValidator.isOpponent(possiblePosition)
    }
}