package janorschke.meyer.game.piece

import janorschke.meyer.R

enum class PieceColor(val fillColorId: Int, val strokeColorId: Int) {
    WHITE(R.color.white, R.color.black),
    BLACK(R.color.black, R.color.white)
}