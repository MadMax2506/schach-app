package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece

class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        from: PiecePositionPair,
        to: PiecePositionPair,
        beaten: PiecePositionPair,
        isEnPassant: Boolean,
        castling: Castling?,
        promotionTo: Piece?
) : PossibleMove(from, to, beaten, castling, isEnPassant, promotionTo) {
    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving = fieldsAfterMoving,
            from = PiecePositionPair(possibleMove.from),
            to = PiecePositionPair(possibleMove.to),
            beaten = PiecePositionPair(possibleMove.beaten),
            isEnPassant = possibleMove.isEnPassant,
            castling = possibleMove.castling,
            promotionTo = possibleMove.promotionTo
    )
}