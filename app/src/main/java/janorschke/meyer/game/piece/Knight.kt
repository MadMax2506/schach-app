package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel
import kotlin.math.abs

class Knight(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color, PieceInfo.KNIGHT) {

    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -2..2) {
            for (j in -2..2) {
                // proves that the position can be reached by the knight
                if (abs(i) + abs(j) != 3) continue

                val currentPosition = PiecePosition(position.row + i, position.col + j)
                if (isFieldUnavailable(currentPosition)) continue
                // TODO Steht der König im Schach oder ist die Figur gesesselt
                possibleMoves.add(currentPosition)
            }
        }
        return possibleMoves
    }
}