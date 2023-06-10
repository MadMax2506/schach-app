package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.utils.ArrayUtils.deepCopy
import janorschke.meyer.service.utils.ArrayUtils.deepEquals

class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        from: PiecePosition,
        to: PiecePosition,
        beaten: PiecePosition,
        isEnPassant: Boolean,
        castling: Castling?,
        promotionTo: Piece?
) : PossibleMove(from, to, beaten, castling, isEnPassant, promotionTo) {
    constructor(move: Move) : this(
            fieldsAfterMoving = deepCopy(move.fieldsAfterMoving),
            from = PiecePosition(move.from),
            to = PiecePosition(move.to),
            beaten = PiecePosition(move.beaten),
            isEnPassant = move.isEnPassant,
            castling = move.castling,
            promotionTo = move.promotionTo
    )

    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving = fieldsAfterMoving,
            from = PiecePosition(possibleMove.from),
            to = PiecePosition(possibleMove.to),
            beaten = PiecePosition(possibleMove.beaten),
            isEnPassant = possibleMove.isEnPassant,
            castling = possibleMove.castling,
            promotionTo = possibleMove.promotionTo
    )


    override fun hashCode(): Int {
        var result = fieldsAfterMoving.contentDeepHashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + beaten.hashCode()
        result = 31 * result + castling.hashCode()
        result = 31 * result + isEnPassant.hashCode()
        result = 31 * result + promotionTo.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Move) return false

        return deepEquals(this.fieldsAfterMoving, other.fieldsAfterMoving)
                && this.from == other.from
                && this.to == other.to
                && this.beaten == other.beaten
                && this.castling == other.castling
                && this.isEnPassant == other.isEnPassant
                && this.promotionTo == other.promotionTo
    }
}