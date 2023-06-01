package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.PossibleMove
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.service.validator.FieldValidator
import kotlin.math.abs

class Pawn(color: PieceColor) : Piece(color, PieceInfo.PAWN) {
    override fun givesOpponentKingCheck(
            board: Board,
            history: History,
            kingPosition: PiecePosition,
            ownPosition: PiecePosition
    ): Boolean {
        for (i in arrayOf(-1, 1)) {
            PiecePosition(ownPosition.row + getMoveDirection(), ownPosition.col + i).let { piecePosition ->
                if (piecePosition == kingPosition) return true
            }
        }
        return false
    }

    override fun possibleMoves(
            board: Board,
            history: History,
            currentPosition: PiecePosition,
            disableCheckCheck: Boolean
    ): MutableList<PossibleMove> {
        val possibleMoves = mutableListOf<PossibleMove>()

        // normal move
        PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col).let { piecePosition ->
            if (FieldValidator.isEmpty(board, piecePosition)) {
                addPossibleMove(board, history, currentPosition, piecePosition, possibleMoves, disableCheckCheck)

                // move from base line only possible if normal move is also possible
                specialMoveFromBaseLine(board, history, currentPosition, possibleMoves, disableCheckCheck)
            }
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col + i).let { piecePosition ->
                if (!isFieldUnavailable(board, piecePosition) && FieldValidator.isOpponent(board, color, piecePosition)) {
                    addPossibleMove(board, history, currentPosition, piecePosition, possibleMoves, disableCheckCheck)
                }
            }
        }

        enPassant(board, history, currentPosition, possibleMoves, disableCheckCheck)

        return possibleMoves
    }

    private fun enPassant(
            board: Board,
            history: History,
            currentPosition: PiecePosition,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean
    ) {
        val lastMove = history.getLastMoves(1).getOrNull(0)

        if (BoardValidator.isEnPassantMove(lastMove, color, currentPosition)) {
            val enPassantPosition = PiecePosition(color.enPassantRow + getMoveDirection(), lastMove!!.to.col)
            addPossibleMove(board, history, currentPosition, enPassantPosition, possibleMoves, disableCheckCheck)
        }
    }

    private fun specialMoveFromBaseLine(
            board: Board,
            history: History,
            currentPosition: PiecePosition,
            possibleMoves: MutableList<PossibleMove>,
            disableCheckCheck: Boolean
    ) {
        if (currentPosition.row != color.pawnSpawnRow) return
        PiecePosition(currentPosition.row + 2 * getMoveDirection(), currentPosition.col).let { piecePosition ->
            if (FieldValidator.isEmpty(board, piecePosition)) {
                addPossibleMove(board, history, currentPosition, piecePosition, possibleMoves, disableCheckCheck)
            }
        }
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1
}