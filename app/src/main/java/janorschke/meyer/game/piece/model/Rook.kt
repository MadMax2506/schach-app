package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.utils.PiecePosition

class Rook(color: PieceColor) : LineMovingPiece(color, PieceInfo.ROOK) {
    override fun possibleMoves(board: Board, position: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(board, position, disableCheckCheck)
    }
}