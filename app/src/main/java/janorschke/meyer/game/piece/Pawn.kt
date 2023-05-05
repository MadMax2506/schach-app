package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Pawn(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_pawn
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // normal move
        PiecePosition(position.row + 1, position.col).apply {
            if (fieldValidation.isEmpty(this)) possibleMoves.add(this)
        }

        // move from base line
        PiecePosition(position.row + 2, position.col).apply {
            if (!moved && fieldValidation.isEmpty(this)) possibleMoves.add(this)
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + 1, position.col + i).apply {
                if (!isFieldUnavailable(this) && fieldValidation.isOpponent(this)) possibleMoves.add(this)
            }
        }

        return possibleMoves
    }

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidation.isInBound(position) || fieldValidation.isTeammate(position)
    }
}