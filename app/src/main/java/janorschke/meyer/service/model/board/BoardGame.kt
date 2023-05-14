package janorschke.meyer.service.model.board

import janorschke.meyer.service.model.piece.King
import janorschke.meyer.service.model.piece.Knight
import janorschke.meyer.service.model.piece.Pawn
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.piece.lineMoving.Bishop
import janorschke.meyer.service.model.piece.lineMoving.Queen
import janorschke.meyer.service.model.piece.lineMoving.Rook

object BoardGame : Board() {
    init {
        reset()
    }

    fun reset() {
        boardFields[0] = generateBaseLine(PieceColor.BLACK)
        boardFields[1] = generatePawnLine(PieceColor.BLACK)
        boardFields[6] = generatePawnLine(PieceColor.WHITE)
        boardFields[7] = generateBaseLine(PieceColor.WHITE)
    }

    /**
     * @param color of the piece
     * @return the pawn line
     */
    private fun generatePawnLine(color: PieceColor): Array<Piece?> = Array(LINE_SIZE) { Pawn(color) }

    /**
     * @param color of the piece
     * @return the base line of pieces
     */
    private fun generateBaseLine(color: PieceColor): Array<Piece?> {
        return arrayOf(
                Rook(color),
                Knight(color),
                Bishop(color),
                Queen(color),
                King(color),
                Bishop(color),
                Knight(color),
                Rook(color)
        )
    }
}