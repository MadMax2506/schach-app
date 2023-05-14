package janorschke.meyer.game.service.model.piece

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.utils.board.PiecePosition
import kotlin.math.abs

class Knight(color: PieceColor) : Piece(color, PieceInfo.KNIGHT) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (i in -2..2) {
            for (j in -2..2) {
                // proves that the position can be reached by the knight
                if (abs(i) + abs(j) != 3) continue

                val possiblePosition = PiecePosition(currentPosition.row + i, currentPosition.col + j)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}