package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.Piece

data class PiecePosition(val position: Position, val piece: Piece? = null) {
    val requiredPiece get() = piece!!

    constructor(piecePosition: PiecePosition) : this(Position(piecePosition.position), piecePosition.piece)
}
