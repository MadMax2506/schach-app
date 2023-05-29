package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * Snapshot for {@link Board} after moving a piece
 */
class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        val from: PiecePosition,
        val to: PiecePosition,
        val fromPiece: Piece,
        val toPiece: Piece?,
) {
    override fun hashCode(): Int {
        var result = fieldsAfterMoving.contentDeepHashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + fromPiece.hashCode()
        result = 31 * result + (toPiece?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (!fieldsAfterMoving.contentDeepEquals(other.fieldsAfterMoving)) return false
        if (from != other.from) return false
        if (to != other.to) return false
        if (fromPiece != other.fromPiece) return false
        if (toPiece != other.toPiece) return false

        return true
    }
}