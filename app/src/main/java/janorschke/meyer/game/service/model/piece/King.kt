package janorschke.meyer.game.service.model.piece

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.utils.board.PiecePosition

class King(color: PieceColor) : Piece(color, PieceInfo.KING) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (row in -1..1) {
            for (col in -1..1) {
                val possiblePosition = PiecePosition(currentPosition.row + row, currentPosition.col + col)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}