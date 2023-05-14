package janorschke.meyer.service.model.piece.lineMoving

import janorschke.meyer.service.model.board.Board
import janorschke.meyer.service.model.piece.PieceColor
import janorschke.meyer.service.model.piece.PieceInfo
import janorschke.meyer.service.utils.board.PiecePosition

class Bishop(color: PieceColor) : LineMovingPiece(color, PieceInfo.BISHOP) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck)
    }
}