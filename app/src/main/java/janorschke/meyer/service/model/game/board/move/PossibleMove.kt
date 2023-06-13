package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece

open class PossibleMove(
        val from: PiecePosition,
        val to: PiecePosition,
        val beaten: PiecePosition,
        val castling: Castling?,
        val isEnPassant: Boolean,
        var promotionTo: Piece?
) {
    constructor(move: Move) : this(
            from = (move.from),
            to = PiecePosition(move.to),
            beaten = PiecePosition(move.beaten),
            castling = move.castling,
            isEnPassant = move.isEnPassant,
            promotionTo = move.promotionTo
    )

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + beaten.hashCode()
        result = 31 * result + castling.hashCode()
        result = 31 * result + isEnPassant.hashCode()
        result = 31 * result + promotionTo.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PossibleMove) return false

        return this.from == other.from
                && this.to == other.to
                && this.beaten == other.beaten
                && this.castling == other.castling
                && this.isEnPassant == other.isEnPassant
                && this.promotionTo == other.promotionTo
    }
}