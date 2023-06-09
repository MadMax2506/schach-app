package janorschke.meyer.service.validator

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.Position

/**
 * Validator for a specific field on the chess board for a piece
 */
object FieldValidator {
    private val CENTER_RANGE = 3..4
    private val EXTENDED_CENTER_RANGE = 2..5

    /**
     * @param position being checked
     * @return true, if the piece in the center
     */
    fun isCenter(position: Position) = position.row in CENTER_RANGE && position.col in CENTER_RANGE

    /**
     * @param position being checked
     * @return true, if the piece in the extended center
     */
    fun isExtendedCenter(position: Position) = position.row in EXTENDED_CENTER_RANGE && position.col in EXTENDED_CENTER_RANGE

    /**
     * @param board instance
     * @param color of the piece
     * @param position being checked
     *
     * @return true, if position contains a piece of the opponent team
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isOpponent(board: Board, color: PieceColor, position: Position): Boolean {
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
    fun isTeammate(board: Board, color: PieceColor, position: Position): Boolean {
        if (!isInBound(position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position)?.color == color
    }

    /**
     * @param board instance
     * @param position being checked
     *
     * @return true, if no piece is on the given position
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isEmpty(board: Board, position: Position): Boolean {
        if (!isInBound(position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position) == null
    }

    /**
     * @param position being checked
     *
     * @return true, if position is on the board
     */
    fun isInBound(position: Position): Boolean {
        val indices = 0 until Board.LINE_SIZE
        return position.row in indices && position.col in indices
    }
}