package janorschke.meyer.enums

import janorschke.meyer.R

enum class PieceInfo(val imageId: Int, val notationId: Int, val valence: Int, val type: PieceType) {
    KING(R.drawable.chess_king, R.string.piece_info_king, Int.MAX_VALUE, PieceType.OTHER),
    QUEEN(R.drawable.chess_queen, R.string.piece_info_queen, 9, PieceType.HEAVY),
    ROOK(R.drawable.chess_rook, R.string.piece_info_rook, 5, PieceType.HEAVY),
    KNIGHT(R.drawable.chess_knight, R.string.piece_info_knight, 3, PieceType.LIGHT),
    BISHOP(R.drawable.chess_bishop, R.string.piece_info_bishop, 3, PieceType.LIGHT),
    PAWN(R.drawable.chess_pawn, R.string.piece_info_pawn, 1, PieceType.OTHER)
}