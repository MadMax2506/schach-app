package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Bishop(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_bishop
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}