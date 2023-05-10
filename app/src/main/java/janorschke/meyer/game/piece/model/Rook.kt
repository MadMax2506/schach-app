package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class Rook(board: Board, color: PieceColor) : LineMovingPiece(board, color, PieceInfo.ROOK) {
    override fun possibleMoves(position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(position, disableCheckCheck)
    }
}