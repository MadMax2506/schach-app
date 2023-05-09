package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.board.validator.BoardValidator
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class King(board: Board, color: PieceColor) : Piece(board, color, PieceInfo.KING) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        return (-1..1).flatMap { row ->
            (-1..1).map { col ->
                PiecePosition(position.row + row, position.col + col)
            }
        }.filterNot { isFieldUnavailable(it) }
                .filterNot {
                    Board(board).apply { this.createBoardMove(position, it) }
                            .let { board -> BoardValidator.isKingInCheck(board, color) }
                }.toMutableList()
    }
}