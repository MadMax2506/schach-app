package janorschke.meyer.enums

enum class PieceColor(val borderRow: Int, val pawnSpawnRow: Int, val enPassantRow: Int) {
    WHITE(7, 6, 3),
    BLACK(0, 1, 4);

    fun opponent(): PieceColor = if (this == WHITE) BLACK else WHITE
}