package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

class King(boardViewModel: BoardViewModel, color: PieceColor) : Piece(boardViewModel, color, PieceInfo.KING) {
    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }

    override fun isFieldUnavailable(position: PiecePosition): Boolean {
        TODO("Not yet implemented")
    }
}