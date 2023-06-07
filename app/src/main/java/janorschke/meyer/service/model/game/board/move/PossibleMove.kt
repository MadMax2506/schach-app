package janorschke.meyer.service.model.game.board.move

import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.piece.Piece

open class PossibleMove(
        val fromPosition: PiecePosition,
        val toPosition: PiecePosition,
        val beatenPiecePosition: PiecePosition,
        val fromPiece: Piece,
        val beatenPiece: Piece?,
        val isEnPassant: Boolean,
        val castling: Castling?,
        val promotionTo: Piece?
) {
    constructor(move: Move) : this(
            move.fromPosition,
            move.toPosition,
            move.beatenPiecePosition,
            move.fromPiece,
            move.beatenPiece,
            move.isEnPassant,
            move.castling,
            move.promotionTo
    )
}