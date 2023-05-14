package janorschke.meyer.service.model.board

import janorschke.meyer.service.model.piece.King
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.utils.board.PiecePosition
import janorschke.meyer.service.utils.piece.PieceSequence

/**
 * Chess board
 */
abstract class Board {
    companion object {
        const val LINE_SIZE = 8
        const val SIZE = LINE_SIZE * LINE_SIZE
    }

    protected var boardFields: Array<Array<Piece?>> = Array(LINE_SIZE) { Array(LINE_SIZE) { null } }

    /**
     * @return the whole chess board
     */
    fun getFields(): Array<Array<Piece?>> = boardFields

    /**
     * @param position target
     * @return piece on the target
     */
    fun getField(position: PiecePosition): Piece? = boardFields[position.row][position.col]

    fun setField(position: PiecePosition, piece: Piece?) {
        boardFields[position.row][position.col] = piece
    }

    /**
     * Searches for the King on the board with the given color
     * @param color of the piece
     * @return position of the King
     */
    fun findKingPosition(color: PieceColor): PiecePosition? {
        return PieceSequence.allPiecesByColor(boardFields, color)
                .filter { it.piece is King }
                .map { it.position }
                .firstOrNull()
    }
}