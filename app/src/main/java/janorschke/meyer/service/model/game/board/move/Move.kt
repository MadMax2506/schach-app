package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.piece.Piece

/**
 * Snapshot for {@link Board} after moving a piece
 */
class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        fromPosition: PiecePosition,
        toPosition: PiecePosition,
        beatenPiecePosition: PiecePosition,
        fromPiece: Piece,
        beatenPiece: Piece?,
        isEnPassant: Boolean,
        castling: Castling?,
        promotionTo: Piece?
) : PossibleMove(fromPosition, toPosition, beatenPiecePosition, fromPiece, beatenPiece, isEnPassant, castling, promotionTo) {
    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving,
            possibleMove.fromPosition,
            possibleMove.toPosition,
            possibleMove.beatenPiecePosition,
            possibleMove.fromPiece,
            possibleMove.beatenPiece,
            possibleMove.isEnPassant,
            possibleMove.castling,
            possibleMove.promotionTo
    )
}