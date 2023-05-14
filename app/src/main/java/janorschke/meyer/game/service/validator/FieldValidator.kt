package janorschke.meyer.game.service.validator

import janorschke.meyer.game.service.model.board.Board
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.utils.board.PiecePosition

/**
 * Validator for a specific field on the chess board for a piece
 */
object FieldValidator {
    /**
     * @param board instance
     * @param color of the piece
     * @param position being checked
     *
     * @return true, if position contains a piece of the opponent team
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isOpponent(board: Board, color: PieceColor, position: PiecePosition): Boolean {
        return !isEmpty(board, position) && !isTeammate(board, color, position)
    }

    /**
     * @param board instance
     * @param color of the piece
     * @param position being checked
     *
     * @return true, if position contains a piece of the own team.
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isTeammate(board: Board, color: PieceColor, position: PiecePosition): Boolean {
        if (!isInBound(board, position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position)?.color == color
    }

    /**
     * @param board instance
     * @param position being checked
     *
     * @return true, if no piece is on the given position
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isEmpty(board: Board, position: PiecePosition): Boolean {
        if (!isInBound(board, position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position) == null
    }

    /**
     * @param board instance
     * @param position being checked
     *
     * @return true, if position is on the board
     */
    fun isInBound(board: Board, position: PiecePosition): Boolean {
        val indices = board.getFields().indices
        return position.row in indices && position.col in indices
    }
}