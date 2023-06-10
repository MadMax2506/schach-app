package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.Piece

data class PiecePosition(val position: Position, val piece: Piece? = null) {
    val requiredPiece get() = piece!!

    constructor(piecePosition: PiecePosition) : this(Position(piecePosition.position), piecePosition.piece)

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + piece.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PiecePosition) return false

        return this.position == other.position && this.piece == other.piece
    }
}
