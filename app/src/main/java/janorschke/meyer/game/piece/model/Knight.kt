package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition
import kotlin.math.abs

class Knight(color: PieceColor) : Piece(color, PieceInfo.KNIGHT) {
    override fun possibleMoves(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -2..2) {
            for (j in -2..2) {
                // proves that the position can be reached by the knight
                if (abs(i) + abs(j) != 3) continue

                val possiblePosition = PiecePosition(position.row + i, position.col + j)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, position, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}