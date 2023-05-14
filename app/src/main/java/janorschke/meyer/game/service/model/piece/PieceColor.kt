package janorschke.meyer.game.service.model.piece

enum class PieceColor(val borderlineIndex: Int) {
    WHITE(7),
    BLACK(0);

    fun opponent(): PieceColor = if (this == WHITE) BLACK else WHITE
}