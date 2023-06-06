package janorschke.meyer.service.model.game.board

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
        promotionTo: Piece?
) : PossibleMove(fromPosition, toPosition, beatenPiecePosition, fromPiece, beatenPiece, isEnPassant, promotionTo) {
    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving,
            possibleMove.fromPosition,
            possibleMove.toPosition,
            possibleMove.beatenPiecePosition,
            possibleMove.fromPiece,
            possibleMove.beatenPiece,
            possibleMove.isEnPassant,
            possibleMove.promotionTo
    )
}