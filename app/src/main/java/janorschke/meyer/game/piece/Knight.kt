package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel
import kotlin.math.abs

class Knight(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_knight
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        // TODO steht eigener König im Schach vor dem bewegen?

        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -2..2) {
            for (j in -2..2) {
                // proves that the position can be reached by the knight
                if (abs(i) + abs(j) != 3) continue
                val currentPosition = PiecePosition(position.row + i, position.col + j)
                if (!isFieldAvailable(currentPosition)) continue
                possibleMoves.add(currentPosition)
            }
        }
        // TODO steht eigener König im Schach nach dem bewegen?
        return possibleMoves
    }
}