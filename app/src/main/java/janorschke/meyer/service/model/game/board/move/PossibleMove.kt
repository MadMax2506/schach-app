package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece

open class PossibleMove(
        val from: PiecePositionPair,
        val to: PiecePositionPair,
        val beaten: PiecePositionPair,
        val castling: Castling?,
        val isEnPassant: Boolean,
        val promotionTo: Piece?
) {
    constructor(move: Move) : this(
            from = (move.from),
            to = PiecePositionPair(move.to),
            beaten = PiecePositionPair(move.beaten),
            castling = move.castling,
            isEnPassant = move.isEnPassant,
            promotionTo = move.promotionTo
    )
}