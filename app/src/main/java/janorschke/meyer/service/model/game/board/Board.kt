package janorschke.meyer.service.model.game.board

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.move.Castling
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.board.move.PiecePosition
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.King
import janorschke.meyer.service.model.game.piece.Knight
import janorschke.meyer.service.model.game.piece.Pawn
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.piece.lineMoving.Bishop
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.model.game.piece.lineMoving.Rook
import janorschke.meyer.service.utils.ArrayUtils
import janorschke.meyer.service.utils.piece.PieceSequence

/**
 * Chess board
 */
class Board {
    companion object {
        const val LINE_SIZE = 8
        const val SIZE = LINE_SIZE * LINE_SIZE

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

    private var fields: Array<Array<Piece?>>

    constructor() {
        fields = Array(LINE_SIZE) { Array(LINE_SIZE) { null } }
        fields[0] = generateBaseLine(PieceColor.BLACK)
        fields[1] = generatePawnLine(PieceColor.BLACK)
        fields[6] = generatePawnLine(PieceColor.WHITE)
        fields[7] = generateBaseLine(PieceColor.WHITE)
    }

    // Copy Constructor
    constructor(board: Board) : this(board.getFields())
    constructor(fields: Array<Array<Piece?>>) {
        this.fields = ArrayUtils.deepCopy(fields)
    }

    /**
     * @return the whole chess board
     */
    fun getFields(): Array<Array<Piece?>> = fields

    /**
     * @param position target
     * @return piece on the target position
     */
    fun getField(position: Position) = fields[position.row][position.col]

    fun setField(position: Position, piece: Piece?) {
        fields[position.row][position.col] = piece
    }

    /**
     * Searches for the King on the board with the given color
     * @param color of the piece
     * @return position of the King
     */
    fun findKingPosition(color: PieceColor): Position? {
        return PieceSequence.allPiecesByColor(fields, color)
                .filter { it.piece is King }
                .map { it.position }
                .firstOrNull()
    }

    /**
     * @param color of the pieces
     * @return pawn difference of the pieces by the given color
     */
    fun getPawnDifferenceByColor(color: PieceColor): Int {
        val valanceOfOpponentPieces = PieceSequence.allPiecesByColor(fields, color.opponent()).sumOf { it.piece.pieceInfo.valence }
        val valenceOfOwnPieces = PieceSequence.allPiecesByColor(fields, color).sumOf { it.piece.pieceInfo.valence }

        return valenceOfOwnPieces - valanceOfOpponentPieces
    }

    /**
     * Creates a possible Move for a piece
     *
     * @param fromPosition source position
     * @param toPosition target position
     * @param pawnReplaceWith piece which is used for the pawn promotion
     * @param isEnPassant boolean that (Default = false)
     * @return possible board move
     */
    fun createPossibleMove(
            fromPosition: Position,
            toPosition: Position,
            pawnReplaceWith: Piece? = null,
            isEnPassant: Boolean = false,
            castling: Castling? = null,
    ): PossibleMove {
        val fromPiece = getField(fromPosition)!!
        val toPiece = getField(toPosition)

        val beatenPosition = if (!isEnPassant) toPosition
        else {
            fromPiece as Pawn
            Position(toPosition.row - fromPiece.getMoveDirection(), toPosition.col)
        }
        val beatenPiece = getField(beatenPosition)

        return PossibleMove(
                from = PiecePosition(fromPosition, fromPiece),
                to = PiecePosition(toPosition, toPiece),
                beaten = PiecePosition(beatenPosition, beatenPiece),
                castling = castling,
                isEnPassant = isEnPassant,
                promotionTo = pawnReplaceWith
        )
    }

    /**
     * Moves a piece to another position
     *
     * @param fromPosition source position
     * @param toPosition target position
     * @param pawnReplaceWith piece which is used for the pawn promotion
     * @param isEnPassant boolean that (Default = false)
     * @return board move
     */
    fun createMove(
            fromPosition: Position,
            toPosition: Position,
            pawnReplaceWith: Piece? = null,
            isEnPassant: Boolean = false,
            castling: Castling? = null,
    ): Move {
        return createMove(createPossibleMove(fromPosition, toPosition, pawnReplaceWith, isEnPassant, castling))
    }

    fun createMove(possibleMove: PossibleMove): Move {
        setField(possibleMove.from.position, null)
        setField(possibleMove.beaten.position, null)

        if (possibleMove.promotionTo != null) {
            // pawn can be transfer to an higher valency piece
            setField(possibleMove.to.position, possibleMove.promotionTo)
        } else {
            // normal move
            setField(possibleMove.to.position, possibleMove.from.piece)

            // castling
            val castling = possibleMove.castling
            if (castling != null) {
                // Move rook
                val sourceRook = castling.sourceRook

                setField(castling.targetRook.position, sourceRook.piece)
                setField(sourceRook.position, null)
            }
        }
        return Move(ArrayUtils.deepCopy(fields), possibleMove)
    }
}