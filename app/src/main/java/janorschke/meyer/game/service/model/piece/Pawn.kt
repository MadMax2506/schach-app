package janorschke.meyer.game.service.model.piece

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.utils.board.PiecePosition
import janorschke.meyer.game.service.validator.FieldValidator

class Pawn(color: PieceColor) : Piece(color, PieceInfo.PAWN) {
    override fun givesOpponentKingCheck(board: Board, ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        for (i in arrayOf(-1, 1)) {
            PiecePosition(ownPosition.row + getMoveDirection(), ownPosition.col + i).apply {
                if (this == kingPosition) return true
            }
        }
        return false
    }

    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // normal move
        PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col).apply {
            if (FieldValidator.isEmpty(board, this)) {
                addPossibleMove(board, currentPosition, this, possibleMoves, disableCheckCheck)
            }
        }

        // move from base line
        PiecePosition(currentPosition.row + 2 * getMoveDirection(), currentPosition.col).apply {
            if (!moved && FieldValidator.isEmpty(board, this)) {
                addPossibleMove(board, currentPosition, this, possibleMoves, disableCheckCheck)
            }
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col + i).apply {
                if (!isFieldUnavailable(board, this) && FieldValidator.isOpponent(board, color, this)) {
                    addPossibleMove(board, currentPosition, this, possibleMoves, disableCheckCheck)
                }
            }
        }
        return possibleMoves
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1
}