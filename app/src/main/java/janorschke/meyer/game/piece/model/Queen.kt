package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition

class Queen(board: Board, color: PieceColor) : LineMovingPiece(board, color, PieceInfo.QUEEN) {
    override fun possibleMoves(position: PiecePosition): MutableList<PiecePosition> {
        return possibleMovesOnDiagonalLine(position).plus(possibleMovesOnStraightLine(position)).toMutableList()
    }
}