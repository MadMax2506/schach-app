package janorschke.meyer.game.service.model

import janorschke.meyer.game.service.model.piece.Piece
import janorschke.meyer.game.service.utils.board.PiecePosition

/**
 * Snapshot for {@link Board} after moving a piece
 */
data class Move(
        /**
         * Version of the board after moving pieces
         */
        val board: Array<Array<Piece?>>,
        /**
         * Source position of the piece
         */
        val from: PiecePosition,
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (!board.contentDeepEquals(other.board)) return false
        if (from != other.from) return false
        if (to != other.to) return false
        if (fromPiece != other.fromPiece) return false
        if (toPiece != other.toPiece) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + fromPiece.hashCode()
        result = 31 * result + (toPiece?.hashCode() ?: 0)
        return result
    }
}