package janorschke.meyer.game.service.model.piece.lineMoving

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.piece.PieceInfo
import janorschke.meyer.game.service.utils.board.PiecePosition

class Bishop(color: PieceColor) : LineMovingPiece(color, PieceInfo.BISHOP) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck)
    }
}