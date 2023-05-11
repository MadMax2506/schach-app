package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.utils.PiecePosition

class Pawn(color: PieceColor) : Piece(color, PieceInfo.PAWN) {
    override fun givesOpponentKingCheck(board: Board, ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        for (i in arrayOf(-1, 1)) {
            PiecePosition(ownPosition.row + getMoveDirection(), ownPosition.col + i).apply {
                if (this == kingPosition) return true
            }
        }
        return false
    }

    override fun possibleMoves(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // normal move
        PiecePosition(position.row + getMoveDirection(), position.col).apply {
            if (fieldValidator.isEmpty(board, this)) {
                addPossibleMove(board, position, this, possibleMoves, disableCheckCheck)
            }
        }

        // move from base line
        PiecePosition(position.row + 2 * getMoveDirection(), position.col).apply {
            if (!moved && fieldValidator.isEmpty(board, this)) {
                addPossibleMove(board, position, this, possibleMoves, disableCheckCheck)
            }
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + getMoveDirection(), position.col + i).apply {
                if (!isFieldUnavailable(board, this) && fieldValidator.isOpponent(board, this)) {
                    addPossibleMove(board, position, this, possibleMoves, disableCheckCheck)
                }
            }
        }
        return possibleMoves
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1
}