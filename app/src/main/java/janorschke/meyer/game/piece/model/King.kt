package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.utils.PiecePosition

class King(color: PieceColor) : Piece(color, PieceInfo.KING) {
    override fun possibleMoves(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (row in -1..1) {
            for (col in -1..1) {
                val possiblePosition = PiecePosition(position.row + row, position.col + col)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, position, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}