package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class Pawn(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.PAWN) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        val moveDirection = if (color == PieceColor.WHITE) -1 else 1

        // TODO Schach?

        // normal move
        PiecePosition(position.row + moveDirection, position.col).apply {
            if (fieldValidation.isEmpty(this)) possibleMoves.add(this)
        }

        // move from base line
        PiecePosition(position.row + 2 * moveDirection, position.col).apply {
            if (!moved && fieldValidation.isEmpty(this)) possibleMoves.add(this)
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(position.row + moveDirection, position.col + i).apply {
                if (!isFieldUnavailable(this) && fieldValidation.isOpponent(this)) possibleMoves.add(this)
            }
        }
        return possibleMoves
    }
}