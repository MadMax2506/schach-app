package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class King(boardViewModel: BoardViewModel, colo: PieceColor) : Piece(boardViewModel, colo) {
    override fun getImageId(): Int {
        return R.drawable.chess_king
    }

    override fun possibleMoves(position: PiecePosition): Array<PiecePosition> {
        TODO("Not yet implemented")
    }
}