package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class King(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_king
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        // TODO steht eigener König im Schach vor dem bewegen?

        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val currentPosition = PiecePosition(position.row + i, position.col + j)
                if (!isFieldAvailable(currentPosition)) continue
                possibleMoves.add(currentPosition)
            }
        }
        // TODO steht eigener König im Schach nach dem bewegen?
        return possibleMoves
    }
}