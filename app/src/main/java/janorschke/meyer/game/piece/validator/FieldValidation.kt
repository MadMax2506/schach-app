package janorschke.meyer.game.piece.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.model.Piece
import janorschke.meyer.game.piece.PiecePosition

/**
 * Validator for a specific field on the chess board for a piece
 */
class FieldValidation(private val piece: Piece, private val board: Board) {
    /**
     * @param position being checked
     * @return true, if position contains a piece of the opponent team
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isOpponent(position: PiecePosition): Boolean {
        return !isEmpty(position) && !isTeammate(position)
    }

    /**
     * @param position being checked
     * @return true, if position contains a piece of the own team.
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isTeammate(position: PiecePosition): Boolean {
        if (!isInBound(position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position)?.color == piece.color
    }

    /**
     * @param position being checked
     * @return true, if no piece is on the given position
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isEmpty(position: PiecePosition): Boolean {
        if (!isInBound(position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position) == null
    }

    /**
     * @param position being checked
     * @return true, if position is on the board
     */
    fun isInBound(position: PiecePosition): Boolean {
        val indices = board.getFields().indices
        return position.row in indices && position.col in indices
    }
}