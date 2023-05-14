package janorschke.meyer.service.model.piece.lineMoving

import janorschke.meyer.service.model.board.Board
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.utils.board.PiecePosition

class Rook(color: PieceColor) : LineMovingPiece(color, PieceInfo.ROOK) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(board, currentPosition, disableCheckCheck)
    }
}