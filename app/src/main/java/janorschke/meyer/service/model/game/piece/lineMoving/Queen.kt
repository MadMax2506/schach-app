package janorschke.meyer.service.model.game.piece.lineMoving

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition

class Queen(color: PieceColor) : LineMovingPiece(color, PieceInfo.QUEEN) {
    override fun possibleMoves(
            board: Board,
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean,
            history: History
    ): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(board, currentPosition, disableCheckCheck, history)
                .plus(possibleMovesOnStraightLine(board, currentPosition, disableCheckCheck, history))
                .toMutableList()
    }
}