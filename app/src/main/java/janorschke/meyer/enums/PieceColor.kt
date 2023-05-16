package janorschke.meyer.enums

enum class PieceColor(val borderlineIndex: Int) {
    WHITE(7),
    BLACK(0);

    fun opponent(): PieceColor = if (this == WHITE) BLACK else WHITE
}