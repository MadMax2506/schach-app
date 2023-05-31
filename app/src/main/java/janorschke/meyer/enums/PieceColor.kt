package janorschke.meyer.enums

enum class PieceColor(val borderlineIndex: Int, val pawnLine: Int) {
    WHITE(7, 6),
    BLACK(0, 1);

    fun opponent(): PieceColor = if (this == WHITE) BLACK else WHITE
}