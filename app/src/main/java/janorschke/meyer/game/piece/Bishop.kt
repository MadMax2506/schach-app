package janorschke.meyer.game.piece

import janorschke.meyer.game.board.Board

class Bishop(board: Board, color: PieceColor) : LineMovingPiece(board, color, PieceInfo.BISHOP) {
    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        return possibleMovesOnDiagonalLine(position)
    }
}