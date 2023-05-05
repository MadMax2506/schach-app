package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.GameViewModel

class King(gameViewModel: GameViewModel, color: PieceColor) : Piece(gameViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_king
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        TODO("Not yet implemented")
    }
}