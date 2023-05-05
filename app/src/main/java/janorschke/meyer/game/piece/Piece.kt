package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel
import kotlin.math.abs

abstract class Piece(val boardViewModel: BoardViewModel, val color: PieceColor) {
    /**
     * @param position being checked
     * @return true, if a piece can move to the position
     */
    protected fun isFieldAvailable(position: PiecePosition): Boolean {
        val board = boardViewModel.getBoard()

        val indices = board.indices
        if (position.row !in indices || position.col !in indices) return false

        val piece = board[position.row][position.col]
        return if (piece == null) true else piece.color != this.color
    }

    /**
     * @param position current position
     * @return possible moves on the four diagonal line
     */
    protected fun possibleMovesOnDiagonalLine(position: PiecePosition): MutableCollection<PiecePosition> {
        // TODO steht eigener König im Schach vor dem bewegen?
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1..7) {
            val currentPosition = PiecePosition(position.row + i, position.col + i)
            if (!isFieldAvailable(currentPosition)) break
            possibleMoves.add(currentPosition)
        }

        // left down
        for (i in -1 downTo -7) {
            val currentPosition = PiecePosition(position.row + i, position.col + i)
            if (!isFieldAvailable(currentPosition)) break
            possibleMoves.add(currentPosition)
        }

        // left up
        for (i in -1 downTo -7) {
            val currentPosition = PiecePosition(position.row + abs(i), position.col + i)
            if (!isFieldAvailable(currentPosition)) break
            possibleMoves.add(currentPosition)
        }

        // right down
        for (i in -1 downTo -7) {
            val currentPosition = PiecePosition(position.row + i, position.col + abs(i))
            if (!isFieldAvailable(currentPosition)) break
            possibleMoves.add(currentPosition)
        }

        return possibleMoves
    }

    /**
     * @return the image id for the piece
     */
    abstract fun getImageId(): Int

    /**
     * @param position current position
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition>

}