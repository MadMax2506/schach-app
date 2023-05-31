package janorschke.meyer.service.model.game.piece.lineMoving

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition

class Rook(color: PieceColor) : LineMovingPiece(color, PieceInfo.ROOK) {
    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean
    ): MutableList<PiecePosition> {
        return possibleMovesOnStraightLine(board, history, currentPosition, disableCheckCheck)
    }
}