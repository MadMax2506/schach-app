package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.validator.BoardValidator
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class King(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.KING) {

    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()
        for (row in -1..1) {
            for (col in -1..1) {
                val currentPosition = PiecePosition(position.row + row, position.col + col)
                if (isFieldUnavailable(currentPosition)) continue

                val boardCopy = Board(board)
                boardCopy.createBoardMove(position, currentPosition)
                // in BoardValidator#isKingInCheck wird possibleMoves aufgerufen => rekursion zwischen 2 Funktionen
                if(!BoardValidator.isKingInCheck(boardCopy, color)) possibleMoves.add(currentPosition)
            }
        }
        return possibleMoves
    }
}