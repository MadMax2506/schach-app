package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.Piece

class Castling(
        rookPosition: Position,
        rook: Piece,
        color: PieceColor,
        castlingCol: Int,
        isShortCastling: Boolean
) {
    val sourceRook: PiecePosition
    val targetRook: PiecePosition

    init {
        sourceRook = PiecePosition(rookPosition, rook)

        val targetPosition = Position(color.borderRow, castlingCol + if (isShortCastling) -1 else +1)
        targetRook = PiecePosition(targetPosition)
    }
}