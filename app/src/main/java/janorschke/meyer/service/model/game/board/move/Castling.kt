package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.piece.lineMoving.Rook

class Castling(rookPosition: Position, rook: Piece, color: PieceColor, castlingCol: Int) {
    /**
     * Source [PiecePosition] of the [Rook]
     */
    val sourcePiecePosition: PiecePosition

    /**
     * Target [Position] of the [Rook]
     */
    val targetPosition: Position

    init {
        sourcePiecePosition = PiecePosition(rookPosition, rook)

        val isShortCastling = castlingCol == King.SHORT_CASTLING_COL
        targetPosition = Position(color.borderRow, castlingCol + if (isShortCastling) -1 else +1)
    }
}