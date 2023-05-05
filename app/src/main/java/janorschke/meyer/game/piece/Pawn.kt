package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.board.BoardViewModel

class Pawn(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color) {
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