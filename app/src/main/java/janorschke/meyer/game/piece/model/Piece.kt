package janorschke.meyer.game.piece.model

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PieceInfo
import janorschke.meyer.game.piece.PiecePosition
import janorschke.meyer.game.piece.validator.FieldValidation

/**
 * Represents a chess piece
 */
abstract class Piece(
        protected val board: Board,
        /**
         * Specifies piece color
         */
        val color: PieceColor,
        /**
         * Static piece informations
         */
        val pieceInfo: PieceInfo,
        /**
         * If true, the piece has already moved
         */
        var moved: Boolean = false
) {
    protected val fieldValidation = FieldValidation(this, board)

    /**
     * Marks the piece as moved
     */
    fun move() {
        moved = true
    }

    /**
     * @param position current position
     * @return possible moves
     */
    abstract fun possibleMoves(position: PiecePosition): MutableList<PiecePosition>

    /**
     * @return true, if you're not allowed to go to that position
     */
    fun isFieldUnavailable(position: PiecePosition): Boolean {
        return !fieldValidation.isInBound(position) || fieldValidation.isTeammate(position)
    }

    /**
     * @return true, if King is in check
     */
    fun isKingInCheck(): Boolean {
        val kingPosition = board.findKingPosition(color)
        val opponentPieces = board.getPieces(color.opponent())

        for (opPiece in opponentPieces) {
            /*
            TODO anders zu lösen, da im Piece keine position gespeichert wird
            jede Figur aber nochmal auf dem Board zu suchen ist zu viel aufwand
            */

//            if(opPiece.possibleMoves(opPiece.position).contains(kingPosition)) {
//                return true
//            }
        }
        return false
    }
}