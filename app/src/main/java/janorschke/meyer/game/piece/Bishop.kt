package janorschke.meyer.game.piece

import janorschke.meyer.R
import janorschke.meyer.game.GameViewModel

class Bishop(gameViewModel: GameViewModel, color: PieceColor) : LineMovingPiece(gameViewModel, color) {
    override fun getImageId(): Int {
        return R.drawable.chess_bishop
    }

    override fun possibleMoves(position: PiecePosition): MutableCollection<PiecePosition> {
        return possibleMovesOnDiagonalLine(position)
    }
}