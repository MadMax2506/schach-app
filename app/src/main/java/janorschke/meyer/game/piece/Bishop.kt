package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

class Bishop(boardViewModel: BoardViewModel, color: PieceColor) : LineMovingPiece(boardViewModel, color, PieceInfo.BISHOP) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(position)
    }
}