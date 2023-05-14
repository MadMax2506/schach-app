package janorschke.meyer.game.service.model.board

import janorschke.meyer.game.service.model.piece.King
import janorschke.meyer.game.service.model.piece.Knight
import janorschke.meyer.game.service.model.piece.Pawn
import janorschke.meyer.game.service.model.piece.Piece
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.piece.lineMoving.Bishop
import janorschke.meyer.game.service.model.piece.lineMoving.Queen
import janorschke.meyer.game.service.model.piece.lineMoving.Rook

object BoardGame : Board() {
    init {
        fields[0] = generateBaseLine(PieceColor.BLACK)
        fields[1] = generatePawnLine(PieceColor.BLACK)
        fields[6] = generatePawnLine(PieceColor.WHITE)
        fields[7] = generateBaseLine(PieceColor.WHITE)
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