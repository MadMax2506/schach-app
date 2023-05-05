package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Rook(boardViewModel: BoardViewModel, color: PieceColor) : LineMovingPiece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_rook
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}