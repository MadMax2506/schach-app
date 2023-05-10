package janorschke.meyer.game.piece.validator

import janorschke.meyer.game.board.Board
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.game.piece.PiecePosition

/**
 * Validator for a specific field on the chess board for a piece
 */
class FieldValidator(private val pieceColor: PieceColor) {
    /**
     * @param position being checked
     * @return true, if position contains a piece of the opponent team
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isOpponent(board: Board, position: PiecePosition): Boolean {
        return !isEmpty(board, position) && !isTeammate(board, position)
    }

    /**
     * @param board current board instance
     * @param position being checked
     * @return true, if position contains a piece of the own team.
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isTeammate(board: Board, position: PiecePosition): Boolean {
        if (!isInBound(board, position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position)?.color == pieceColor
    }

    /**
     * @param board current board instance
     * @param position being checked
     * @return true, if no piece is on the given position
     * @throws IndexOutOfBoundsException if position is invalid
     */
    fun isEmpty(board: Board, position: PiecePosition): Boolean {
        if (!isInBound(board, position)) throw IndexOutOfBoundsException("Position is not on the board")

        return board.getField(position) == null
    }

    /**
     * @param board current board instance
     * @param position being checked
     * @return true, if position is on the board
     */
    fun isInBound(board: Board, position: PiecePosition): Boolean {
        val indices = board.getFields().indices
        return position.row in indices && position.col in indices
    }
}