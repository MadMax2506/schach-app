package janorschke.meyer.service.model.player

import janorschke.meyer.service.model.piece.PieceColor

abstract class Player(val resourceId: Int, val color: PieceColor) {
    fun nextColor() = color.opponent()
}