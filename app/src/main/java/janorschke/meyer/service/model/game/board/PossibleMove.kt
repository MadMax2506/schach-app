package janorschke.meyer.service.model.game.board

import janorschke.meyer.service.model.game.piece.Piece

open class PossibleMove(
        val from: PiecePosition,
        val to: PiecePosition,
        val fromPiece: Piece,
        val beatenPiece: Piece?,
        val isEnPassant: Boolean,
        val promotionTo: Piece?
)