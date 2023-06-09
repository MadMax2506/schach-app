package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.PossibleMove
import kotlin.math.abs

class Knight(color: PieceColor) : Piece(color, PieceInfo.KNIGHT) {
    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean,
            disableCastlingCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()
        for (i in -2..2) {
            for (j in -2..2) {
                // proves that the position can be reached by the knight
                if (abs(i) + abs(j) != 3) continue

                val possiblePosition = Position(currentPosition.row + i, currentPosition.col + j)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}