package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece

class Move(
        val fieldsAfterMoving: Array<Array<Piece?>>,
        from: PiecePosition,
        to: PiecePosition,
        beaten: PiecePosition,
        isEnPassant: Boolean,
        castling: Castling?,
        promotionTo: Piece?
) : PossibleMove(from, to, beaten, castling, isEnPassant, promotionTo) {
    constructor(fieldsAfterMoving: Array<Array<Piece?>>, possibleMove: PossibleMove) : this(
            fieldsAfterMoving = fieldsAfterMoving,
            from = PiecePosition(possibleMove.from),
            to = PiecePosition(possibleMove.to),
            beaten = PiecePosition(possibleMove.beaten),
            isEnPassant = possibleMove.isEnPassant,
            castling = possibleMove.castling,
            promotionTo = possibleMove.promotionTo
    )
}