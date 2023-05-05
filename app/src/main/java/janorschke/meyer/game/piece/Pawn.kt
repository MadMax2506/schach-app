package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.GameViewModel

class Pawn(gameViewModel: GameViewModel, color: PieceColor) : Piece(gameViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_pawn
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        TODO("Not yet implemented")
    }
}