package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.piece.Piece

open class PossibleMove(
        val from: PiecePosition,
        val to: PiecePosition,
        val beaten: PiecePosition,
        val castling: Castling?,
        val isEnPassant: Boolean,
        val promotionTo: Piece?
) {
    constructor(move: Move) : this(
            from = (move.from),
            to = PiecePosition(move.to),
            beaten = PiecePosition(move.beaten),
            castling = move.castling,
            isEnPassant = move.isEnPassant,
            promotionTo = move.promotionTo
    )
}