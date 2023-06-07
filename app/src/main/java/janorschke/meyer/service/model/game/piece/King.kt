package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.Castling
import janorschke.meyer.service.model.game.board.PossibleMove
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
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()

        // normal move
        for (row in -1..1) {
            for (col in -1..1) {
                val possiblePosition = PiecePosition(currentPosition.row + row, currentPosition.col + col)
                if (isFieldUnavailable(board, possiblePosition)) continue
                addPossibleMove(board, history, currentPosition, possiblePosition, possibleMoves, disableCheckCheck)
            }
        }

        if (hasMoved(history)) return possibleMoves

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
     * @param shortCastling
     */
    private fun castling(
            board: Board,
            history: History,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean,
            kingPosition: PiecePosition,
            rookCol: Int,
            shortCastling: Boolean = true
    ) {
        val borderRow = color.borderRow
        val castlingCol = if (shortCastling) SHORT_CASTLING_COL else LONG_CASTLING_COL

        val rookPosition = PiecePosition(borderRow, rookCol)
        val rook = board.getField(rookPosition) ?: return

        if (rook.hasMoved(history)) return
        if (!isCastlingPossible(board, history, kingPosition, rookPosition)) return

        addPossibleMove(
                board = board,
                history = history,
                currentPosition = kingPosition,
                possiblePosition = PiecePosition(borderRow, castlingCol),
                possibleMoves = possibleMoves,
                disableCheckCheck = disableCheckCheck,
                castling = Castling(rookPosition, color, castlingCol)
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
            kingPosition: PiecePosition,
            rookPosition: PiecePosition
    ): Boolean {
        val row = kingPosition.row
        val startCol = kingPosition.col.coerceAtMost(rookPosition.col) + 1
        val endCol = kingPosition.col.coerceAtLeast(rookPosition.col) - 1

        for (col in IntRange(startCol, endCol)) {
            val position = PiecePosition(row, col)
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