package janorschke.meyer.service.model.game.piece.lineMoving

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.PossibleMove

class Queen(color: PieceColor) : LineMovingPiece(color, PieceInfo.QUEEN) {
    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean,
            disableCastlingCheck: Boolean
    ): MutableList<PossibleMove> {
        return possibleMovesOnDiagonalLine(board, history, currentPosition, disableCheckCheck)
                .plus(possibleMovesOnStraightLine(board, history, currentPosition, disableCheckCheck))
                .toMutableList()
    }
}