package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.board.Board

class Bishop(board: Board, color: PieceColor) : LineMovingPiece(board, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_bishop
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        return possibleMovesOnDiagonalLine(position)
    }
}