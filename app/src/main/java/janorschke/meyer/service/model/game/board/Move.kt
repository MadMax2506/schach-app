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
)