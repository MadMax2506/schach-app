package janorschke.meyer.enums

import janorschke.meyer.R

enum class PieceInfo(val imageId: Int, val notationId: Int, val valence: Int) {
    KING(R.drawable.chess_king, R.string.piece_info_king, Int.MAX_VALUE),
    QUEEN(R.drawable.chess_queen, R.string.piece_info_queen, 9),
    ROOK(R.drawable.chess_rook, R.string.piece_info_rook, 5),
    KNIGHT(R.drawable.chess_knight, R.string.piece_info_knight, 3),
    BISHOP(R.drawable.chess_bishop, R.string.piece_info_bishop, 3),
    PAWN(R.drawable.chess_pawn, R.string.piece_info_pawn, 1)
}