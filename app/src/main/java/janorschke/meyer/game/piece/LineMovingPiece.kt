package janorschke.meyer.game.piece

import janorschke.meyer.game.board.BoardViewModel

abstract class LineMovingPiece(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidation.isInBound(position) || fieldValidation.isTeammate(position)
    }

    /**
     * @param position current position
     * @return possible moves on the four diagonal line
     */
    protected fun possibleMovesOnDiagonalLine(position: PiecePosition): MutableCollection<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // right up
        for (i in 1 until BoardViewModel.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + i, position.col + i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // right down
        for (i in 1 until BoardViewModel.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row + i, position.col - i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // left up
        for (i in 1 until BoardViewModel.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col + i)
            if (addPosition(currentPosition, possibleMoves)) break
        }

        // left down
        for (i in 1 until BoardViewModel.LINE_SIZE) {
            val currentPosition = PiecePosition(position.row - i, position.col - i)
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
        return fieldValidation.isOpponent(position)
    }
}