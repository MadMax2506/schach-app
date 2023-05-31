package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.game.piece.Piece

/**
 * Snapshot for {@link Board} after moving a piece
 */
class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        from: PiecePosition,
        to: PiecePosition,
        fromPiece: Piece,
        beatenPiece: Piece?,
        isEnPassant: Boolean,
        promotionTo: Piece?
) : PossibleMove(from, to, fromPiece, beatenPiece, isEnPassant, promotionTo) {
    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving,
            possibleMove.from,
            possibleMove.to,
            possibleMove.fromPiece,
            possibleMove.beatenPiece,
            possibleMove.isEnPassant,
            possibleMove.promotionTo
    )
}