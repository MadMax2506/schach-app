package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.PiecePosition

/**
 * TODO
 */
data class Castling(
        val rookPosition: PiecePosition,
        private val color: PieceColor,
        private val castlingCol: Int,
        private val isShortCastling: Boolean
) {
    val rookCastlingTargetPosition: PiecePosition
        get() {
            return PiecePosition(
                    color.borderRow,
                    castlingCol + if (isShortCastling) -1 else +1
            )
        }
}
