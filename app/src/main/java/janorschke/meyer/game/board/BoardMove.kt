package janorschke.meyer.game.board

import janorschke.meyer.game.piece.Piece
import janorschke.meyer.game.piece.PiecePosition

/**
 * Snapshot for {@link Board} after moving a piece
 */
class BoardMove(
        /**
         * Version of the board after moving pieces
         */
        val board: Array<Array<Piece?>>,
        /**
         * Target position of the piece
         */
        val to: PiecePosition,
        /**
         * Piece which is moving
         */
        val fromPiece: Piece,
        /**
         * Piece on the target position which is beaten (opponent) or is null (empty field)
         */
        val toPiece: Piece?,
) {
}