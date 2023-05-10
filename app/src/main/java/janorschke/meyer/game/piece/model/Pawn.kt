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

        // TODO Schach?

        // normal move
        PiecePosition(position.row + getMoveDirection(), position.col).apply {
            if (fieldValidator.isEmpty(this)) possibleMoves.add(this)
        }

        // move from base line
        PiecePosition(position.row + 2 * getMoveDirection(), position.col).apply {
            if (!moved && fieldValidator.isEmpty(this)) possibleMoves.add(this)
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + getMoveDirection(), position.col + i).apply {
                if (!isFieldUnavailable(this) && fieldValidator.isOpponent(this)) possibleMoves.add(this)
            }
        }

        return possibleMoves
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1

    /**
     *
     */
    private fun beatOpponentPiece(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + getMoveDirection(), position.col + i).apply {
                if (!isFieldUnavailable(this) && fieldValidator.isOpponent(this)) possibleMoves.add(this)
            }
        }

        return possibleMoves
    }
}