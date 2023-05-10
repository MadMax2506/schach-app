package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class King(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.KING) {
    override fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (row in -1..1) {
            for (col in -1..1) {
                val possiblePosition = PiecePosition(position.row + row, position.col + col)
                if (isFieldUnavailable(possiblePosition)) continue
                addPossibleMove(position, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }
        return possibleMoves
    }
}