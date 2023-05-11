package janorschke.meyer.game.piece.utils

import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.model.Piece

/**
 * Provides functionality to compare all pieces on the board
 */
class PieceSequence private constructor(indexedValue: IndexedValue<Piece?>) {
    val position: PiecePosition
    val piece: Piece

    init {
        position = PiecePosition(indexedValue.index)
        piece = indexedValue.value!!
    }

    companion object {
        /**
         * @param fields to map and filter
         * @return a piece sequence with nullable values
         */
        fun allPieces(fields: Array<Array<Piece?>>): Sequence<PieceSequence?> {
            return fields.flatten()
                    .withIndex()
                    .map { if (it.value != null) PieceSequence(it) else null }
                    .asSequence()
        }

        /**
         * @param fields to map and filter
         * @param color to filter by
         * @return all pieces, filtered by color
         */
        fun piecesByColor(fields: Array<Array<Piece?>>, color: PieceColor): Sequence<PieceSequence> {
            return allPieces(fields)
                    .filterNotNull()
                    .filter { it.piece.color == color }
        }
    }
}