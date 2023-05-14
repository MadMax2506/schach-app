package janorschke.meyer.game.service.model.player

import janorschke.meyer.game.service.model.piece.PieceColor

abstract class Player(val resourceId: Int, val color: PieceColor) {
    fun nextColor() = color.opponent()
}