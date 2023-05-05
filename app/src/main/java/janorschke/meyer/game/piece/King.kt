package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

class King(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color, PieceInfo.KING) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -1..1) {
            for (j in -1..1) {
                val currentPosition = PiecePosition(position.row + i, position.col + j)
                if (isFieldUnavailable(currentPosition)) continue
                // TODO Steht der König im Schach
                possibleMoves.add(currentPosition)
            }
        }
        return possibleMoves
    }
}