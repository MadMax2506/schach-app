package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.validator.BoardValidator
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class King(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.KING) {

    override fun givesOpponentKingCheck(ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        return false
    }

    override fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (row in -1..1) {
            for (col in -1..1) {
                val currentPosition = PiecePosition(position.row + row, position.col + col)
                if (isFieldUnavailable(currentPosition)) continue
                addMoves(disableCheckCheck, position, currentPosition, possibleMoves)
            }
        }
        return possibleMoves
    }
}