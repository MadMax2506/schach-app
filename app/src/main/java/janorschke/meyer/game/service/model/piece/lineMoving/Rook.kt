package janorschke.meyer.game.service.model.piece.lineMoving

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.piece.PieceInfo
import janorschke.meyer.game.service.utils.board.PiecePosition

class Rook(color: PieceColor) : LineMovingPiece(color, PieceInfo.ROOK) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(board, currentPosition, disableCheckCheck)
    }
}