package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.BoardViewModel

class Queen(boardViewModel: BoardViewModel, color: PieceColor) : LineMovingPiece(boardViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_queen
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}