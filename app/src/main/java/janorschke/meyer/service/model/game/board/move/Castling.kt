package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.piece.Piece

class Castling(
        rookPosition: PiecePosition,
        rook: Piece,
        color: PieceColor,
        castlingCol: Int,
        isShortCastling: Boolean
) {
    val sourceRook: PiecePositionPair
    val targetRook: PiecePositionPair

    init {
        sourceRook = PiecePositionPair(rookPosition, rook)

        val targetPosition = PiecePosition(color.borderRow, castlingCol + if (isShortCastling) -1 else +1)
        targetRook = PiecePositionPair(targetPosition)
    }
}