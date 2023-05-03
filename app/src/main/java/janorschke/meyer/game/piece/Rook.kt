package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Rook(boardViewModel: BoardViewModel, colo: PieceColor) : Piece(boardViewModel, colo) {
    override fun getImageId(): Int {
        return R.drawable.chess_rook
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}