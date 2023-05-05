package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.board.Board

class Rook(board: Board, color: PieceColor) : LineMovingPiece(board, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_rook
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}