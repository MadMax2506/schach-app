package janorschke.meyer.game

import janorschke.meyer.R

enum class PieceType(val value: String, val ressourceId: Int) {
    NONE("NONE", R.color.purple_200),

    PAWN_BLACK("PAWN_BLACK", R.drawable.chess_pdt45),
    BISHOP_BLACK("BISHOP_BLACK", R.drawable.chess_bdt45),
    KNIGHT_BLACK("KNIGHT_BLACK", R.drawable.chess_ndt45),
    ROOK_BLACK("ROOK_BLACK", R.drawable.chess_rdt45),
    QUEEN_BLACK("QUEEN_BLACK", R.drawable.chess_qdt45),
    KING_BLACK("KING_BLACK", R.drawable.chess_kdt45),

    PAWN_WHITE("PAWN_WHITE", R.drawable.chess_plt45),
    BISHOP_WHITE("BISHOP_WHITE", R.drawable.chess_blt45),
    KNIGHT_WHITE("KNIGHT_WHITE", R.drawable.chess_nlt45),
    ROOK_WHITE("ROOK_WHITE", R.drawable.chess_rlt45),
    QUEEN_WHITE("QUEEN_WHITE", R.drawable.chess_qlt45),
    KING_WHITE("KING_WHITE", R.drawable.chess_klt45)
}