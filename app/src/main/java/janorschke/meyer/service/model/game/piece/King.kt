package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Castling
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator

class King(color: PieceColor) : Piece(color, PieceInfo.KING) {
    companion object {
        const val SHORT_CASTLING_COL = 6
        const val LONG_CASTLING_COL = 2
    }

    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: Position,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()

        // normal move
        for (row in -1..1) {
            for (col in -1..1) {
                val possiblePosition = Position(currentPosition.row + row, currentPosition.col + col)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }

        if (hasMoved(history)) return possibleMoves
        if (BoardValidator.isKingInCheck(board, history, color)) return possibleMoves

        // long castling
        castling(board, history, possibleMoves, disableCheckCheck, currentPosition, 0, false)

        // short castling
        castling(board, history, possibleMoves, disableCheckCheck, currentPosition, 7, true)

        return possibleMoves
    }

    /**
     * Checks and produce a castling move
     *
     * @param board instance
     * @param history instance
     * @param possibleMoves already added moves
     * @param disableCheckCheck disables the check is the king is in check
     * @param kingPosition
     * @param rookCol
     * @param isShortCastling
     */
    private fun castling(
            board: Board,
            history: History,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean,
            kingPosition: Position,
            rookCol: Int,
            isShortCastling: Boolean = true
    ) {
        val borderRow = color.borderRow
        val castlingCol = if (isShortCastling) SHORT_CASTLING_COL else LONG_CASTLING_COL

        val rookPosition = Position(borderRow, rookCol)
        val rook = board.getField(rookPosition) ?: return

        if (rook.hasMoved(history) || !isCastlingPossible(board, history, kingPosition, rookPosition)) return

        val castling = Castling(rookPosition, rook, color, castlingCol, isShortCastling)

        addPossibleMove(
                board = board,
                history = history,
                currentPosition = kingPosition,
                possiblePosition = Position(borderRow, castlingCol),
                possibleMoves = possibleMoves,
                disableCheckCheck = disableCheckCheck,
                castling = castling
        )
    }

    /**
     * Checks if castling is possible
     *
     * @param board instance
     * @param history instance
     * @param kingPosition
     * @param rookPosition
     *
     * @return `true`, if the castling is possible
     */
    private fun isCastlingPossible(
            board: Board,
            history: History,
            kingPosition: Position,
            rookPosition: Position
    ): Boolean {
        val row = kingPosition.row
        val startCol = kingPosition.col.coerceAtMost(rookPosition.col) + 1
        val endCol = kingPosition.col.coerceAtLeast(rookPosition.col) - 1

        for (col in IntRange(startCol, endCol)) {
            val position = Position(row, col)
            if (!FieldValidator.isEmpty(board, position)) return false

            Board(board).let { boardCopy ->
                History(history).let { historyCopy ->
                    val move = boardCopy.createMove(kingPosition, position)
                    historyCopy.push(move)

                    if (BoardValidator.isKingInCheck(boardCopy, historyCopy, color)) return false
                }
            }
        }

        return true
    }
}