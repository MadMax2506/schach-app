package janorschke.meyer.service.utils.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.piece.Piece

/**
 * Provides functionality to compare all pieces on the board
 */
object PieceSequence {
    /**
     * @param board instance
     *
     * @see PieceSequence.allPieces
     */
    fun allPieces(board: Board) = allPieces(board.getFields())

    /**
     * @param fields of the current board instance
     *
     * @return a piece sequence with nullable values
     */
    fun allPieces(fields: Array<Array<Piece?>>): Sequence<IndexedPiece?> {
        return fields.flatten()
                .withIndex()
                .map { if (it.value != null) IndexedPiece(it) else null }
                .asSequence()
    }

    /**
     * @param board instance
     * @param color of the pieces
     *
     * @see PieceSequence.allPiecesByColor
     */
    fun allPiecesByColor(board: Board, color: PieceColor) = allPiecesByColor(board.getFields(), color)

    /**
     * @param fields of the current board instance
     * @param color of the pieces
     *
     * @return all pieces, filtered by color
     */
    fun allPiecesByColor(fields: Array<Array<Piece?>>, color: PieceColor): Sequence<IndexedPiece> {
        return allPieces(fields)
                .filterNotNull()
                .filter { it.piece.color == color }
    }

    class IndexedPiece(indexedValue: IndexedValue<Piece?>) {
        val position: Position
        val piece: Piece

        init {
            position = Position(indexedValue.index)
            piece = indexedValue.value!!
        }
    }
}