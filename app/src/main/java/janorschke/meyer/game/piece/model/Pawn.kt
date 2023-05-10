package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class Pawn(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.PAWN) {
    override fun givesOpponentKingCheck(ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        for (i in arrayOf(-1, 1)) {
            PiecePosition(ownPosition.row + getMoveDirection(), ownPosition.col + i).apply {
                if (this == kingPosition) return true
            }
        }
        return false
    }

    override fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // normal move
        PiecePosition(position.row + getMoveDirection(), position.col).apply {
            if (fieldValidator.isEmpty(this))
                addMoves(disableCheckCheck, position, this, possibleMoves)
        }

        // move from base line
        PiecePosition(position.row + 2 * getMoveDirection(), position.col).apply {
            if (!moved && fieldValidator.isEmpty(this))
                addMoves(disableCheckCheck, position, this, possibleMoves)
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + getMoveDirection(), position.col + i).apply {
                if (!isFieldUnavailable(this) && fieldValidator.isOpponent(this))
                    addMoves(disableCheckCheck, position, this, possibleMoves)
            }
        }
        return possibleMoves
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1
}