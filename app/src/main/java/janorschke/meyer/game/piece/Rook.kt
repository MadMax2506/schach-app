package janorschke.meyer.game.piece

import janorschke.meyer.game.board.Board

class Rook(board: Board, color: PieceColor) : LineMovingPiece(board, color, PieceInfo.ROOK) {
    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        TODO("Not yet implemented")
    }
}