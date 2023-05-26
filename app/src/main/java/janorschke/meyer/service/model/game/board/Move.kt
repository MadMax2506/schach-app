package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * Snapshot for {@link Board} after moving a piece
 */
class Move(
        /**
         * Fields before moving pieces
         */
        val fieldsBeforeMoving: Array<Array<Piece?>>,
        /**
         * Fields before moving pieces
         */
        // TODO not working
        val fieldsAfterMoving: Array<Array<Piece?>>,
        /**
         * Source position of the piece
         */
        val from: PiecePosition,
        /**
         * Target position of the piece
         */
        val to: PiecePosition
) {
    val fromPiece get() = fieldsBeforeMoving[from.row][from.col]!!
    val toPiece get() = fieldsBeforeMoving[to.row][to.col]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (!fieldsBeforeMoving.contentDeepEquals(other.fieldsBeforeMoving)) return false
        if (!fieldsAfterMoving.contentDeepEquals(other.fieldsAfterMoving)) return false
        if (from != other.from) return false
        if (to != other.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fieldsBeforeMoving.contentDeepHashCode()
        result = 31 * result + fieldsAfterMoving.contentDeepHashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}