package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.GameViewModel
import kotlin.math.abs

class Knight(gameViewModel: GameViewModel, color: PieceColor) : Piece(gameViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_knight
    }

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidation.isInBound(position) || fieldValidation.isTeammate(position)
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
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