package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.piece.Piece

data class PiecePositionPair(val position: PiecePosition, val piece: Piece? = null) {
    val requiredPiece get() = piece!!

    constructor(piecePositionPair: PiecePositionPair) : this(PiecePosition(piecePositionPair.position), piecePositionPair.piece)
}
