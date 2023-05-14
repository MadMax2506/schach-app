package janorschke.meyer.service.model.piece.lineMoving

import janorschke.meyer.service.model.board.Board
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.utils.board.PiecePosition

class Queen(color: PieceColor) : LineMovingPiece(color, PieceInfo.QUEEN) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck)
                .plus(possibleMovesOnStraightLine(board, currentPosition, disableCheckCheck))
                .toMutableList()
    }
}