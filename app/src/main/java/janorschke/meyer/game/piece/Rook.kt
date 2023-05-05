package janorschke.meyer.game.piece

import janorschke.meyer.game.BoardViewModel

class Rook(boardViewModel: BoardViewModel, color: PieceColor) : LineMovingPiece(boardViewModel, color, PieceInfo.ROOK) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(position)
    }
}