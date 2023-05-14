package janorschke.meyer.service.model.piece.lineMoving

import janorschke.meyer.service.model.board.Board
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.utils.board.PiecePosition

class Bishop(color: PieceColor) : LineMovingPiece(color, PieceInfo.BISHOP) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck)
    }
}