package janorschke.meyer.service.model.game.piece.lineMoving

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.PossibleMove

class Rook(color: PieceColor) : LineMovingPiece(color, PieceInfo.ROOK) {
    companion object {
        const val LEFT_ROOK_COL = 0
        const val RIGHT_ROOK_COL = 7
    }

    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean,
            disableCastlingCheck: Boolean
    ): Sequence<PossibleMove> {
        return possibleMovesOnStraightLine(board, history, currentPosition, disableCheckCheck)
    }
}