package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.PiecePosition

/**
 * TODO
 */
data class Castling(val rookPosition: PiecePosition, private val color: PieceColor, private val castlingCol: Int) {
    val rookCastlingTargetPosition get() = PiecePosition(color.borderRow, castlingCol)
}
