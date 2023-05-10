package janorschke.meyer.game.board

import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.PieceSequence
import janorschke.meyer.game.piece.model.Bishop
import janorschke.meyer.game.piece.model.King
import janorschke.meyer.game.piece.model.Knight
import janorschke.meyer.game.piece.model.Pawn
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.piece.model.Queen
import janorschke.meyer.game.piece.model.Rook

class Board {
    companion object {
        const val SIZE = 64
        const val LINE_SIZE = 8
    }

    private var fields: Array<Array<Piece?>> = Array(LINE_SIZE) { Array(LINE_SIZE) { null } }

    constructor() {
        fields[0] = generateBaseLine(PieceColor.BLACK)
        fields[1] = generatePawnLine(PieceColor.BLACK)
        fields[6] = generatePawnLine(PieceColor.WHITE)
        fields[7] = generateBaseLine(PieceColor.WHITE)
    }

    /**
     * Copy constructor to create a new Board object based on an existing one.
     *
     * @param board Board object to copy
     */
    constructor(board: Board) {
        this.fields = board.fields.map { it.copyOf() }.toTypedArray()
    }

    /**
     * @return the whole chess board
     */
    fun getFields(): Array<Array<Piece?>> = fields

    /**
     * @param position target
     * @return piece on the target
     */
    fun getField(position: PiecePosition): Piece? = fields[position.row][position.col]

    private fun setField(position: PiecePosition, piece: Piece?) {
        fields[position.row][position.col] = piece
    }

    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     */
    fun createBoardMove(from: PiecePosition, to: PiecePosition): BoardMove {
        val fromPiece = getField(from)!!
        val toPiece = getField(to)

        setField(from, null)
        if (fromPiece is Pawn && to.row == fromPiece.color.opponent().borderlineIndex) {
            setField(to, Queen(fromPiece.color))
        } else {
            setField(to, fromPiece)
        }
        fromPiece.move()
        return BoardMove(fields.map { it.copyOf() }.toTypedArray(), from, to, fromPiece, toPiece)
    }

    /**
     * Searches for the King on the board with the given color
     * @param color of the piece
     * @return position of the King
     */
    fun findKingPosition(color: PieceColor): PiecePosition {
        return PieceSequence.piecesByColor(fields, color)
                .filter { it.piece is King }
                .map { it.position }
                .firstOrNull()
                ?: throw IllegalStateException("King with color $color could not be found!")
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