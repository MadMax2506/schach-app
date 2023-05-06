package janorschke.meyer.game.piece

enum class PieceColor {
    WHITE,
    BLACK;

    fun opponent(): PieceColor = if (this == WHITE) BLACK else WHITE
}