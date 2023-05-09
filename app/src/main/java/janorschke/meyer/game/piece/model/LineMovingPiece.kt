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
    protected fun possibleMovesOnDiagonalLine(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + i, position.col + i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // right down
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + i, position.col - i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // left up
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col + i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // left down
        for (i in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col - i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        return possibleMoves
    }

    fun possibleMovesOnStraightLine(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // up
        for (row in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + row, position.col)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // down
        for (row in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - row, position.col)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // right
        for (col in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row, position.col + col)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // left
        for (col in 1 until Board.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row, position.col - col)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        return possibleMoves
    }

    /**
     * @param position of the pice
     * @param possibleMoves
     * @return true if there are no further possible moves
     */
    private fun addPosition(position: PiecePosition, possibleMoves: MutableList<PiecePosition>): Boolean {
        if (isFieldUnavailable(position)) return true

        // TODO Steht der König im Schach oder ist die Figur gesesselt
        possibleMoves.add(position)
        return fieldValidator.isOpponent(position)
    }
}