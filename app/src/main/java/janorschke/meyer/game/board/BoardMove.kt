package janorschke.meyer.game.board

import janorschke.meyer.game.piece.Piece
import janorschke.meyer.game.piece.PiecePosition

/**
 * Snapshot for {@link Board}
 */
class BoardMove(val board: Array<Array<Piece?>>, val to: PiecePosition, val beatenOpponentPiece: Boolean) {
    fun getMoveNotation(): String {
        val piece = board[to.row][to.col]
        // TODO need https://github.com/MadMax2506/android-wahlmodul-project/issues/40
        return "${""}${if (beatenOpponentPiece) "x" else ""}${""}"
    }
}