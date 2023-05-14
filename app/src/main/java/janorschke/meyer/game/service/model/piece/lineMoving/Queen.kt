package janorschke.meyer.game.service.model.piece.lineMoving

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.piece.PieceInfo
import janorschke.meyer.game.service.utils.board.PiecePosition

class Queen(color: PieceColor) : LineMovingPiece(color, PieceInfo.QUEEN) {
    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck)
                .plus(possibleMovesOnStraightLine(board, currentPosition, disableCheckCheck))
                .toMutableList()
    }
}