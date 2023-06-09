package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.Piece

data class PiecePositionPair(val position: Position, val piece: Piece? = null) {
    val requiredPiece get() = piece!!

    constructor(piecePositionPair: PiecePositionPair) : this(Position(piecePositionPair.position), piecePositionPair.piece)
}
