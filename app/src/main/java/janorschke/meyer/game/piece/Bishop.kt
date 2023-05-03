package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Bishop(boardViewModel: BoardViewModel, colo: PieceColor) : Piece(boardViewModel, colo) {
    override fun getImageId(): Int {
        return R.drawable.chess_bishop
    }

    override fun possibleMoves(position: PiecePosition): Array<PiecePosition> {
        TODO("Not yet implemented")
    }
}