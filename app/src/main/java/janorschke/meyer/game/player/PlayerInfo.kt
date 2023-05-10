package janorschke.meyer.game.player

import janorschke.meyer.game.piece.PieceColor

enum class PlayerInfo(val color: PieceColor) {
    WHITE(PieceColor.WHITE),
    BLACK(PieceColor.BLACK);

    fun nextPlayer(): PlayerInfo {
        return if (this == WHITE) BLACK else WHITE
    }
}