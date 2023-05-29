package janorschke.meyer.service.model.game.piece

import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.PieceInfo
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.utils.board.PiecePosition
import janorschke.meyer.service.validator.FieldValidator
import kotlin.math.abs

class Pawn(color: PieceColor) : Piece(color, PieceInfo.PAWN) {
    override fun givesOpponentKingCheck(board: Board, ownPosition: PiecePosition, kingPosition: PiecePosition): Boolean {
        for (i in arrayOf(-1, 1)) {
            PiecePosition(ownPosition.row + getMoveDirection(), ownPosition.col + i).let { piecePosition ->
                if (piecePosition == kingPosition) return true
            }
        }
        return false
    }

    override fun possibleMoves(board: Board, currentPosition: PiecePosition, disableCheckCheck: Boolean): MutableList<PiecePosition> {
        val possibleMoves = mutableListOf<PiecePosition>()

        // normal move
        PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col).let { piecePosition ->
            if (FieldValidator.isEmpty(board, piecePosition)) {
                addPossibleMove(board, currentPosition, piecePosition, possibleMoves, disableCheckCheck)

                // move from base line only possible if normal move is also possible
                specialMoveFromBaseLine(currentPosition, board, possibleMoves, disableCheckCheck)
            }
        }

        // beat
        for (i in arrayOf(-1, 1)) {
            PiecePosition(currentPosition.row + getMoveDirection(), currentPosition.col + i).let { piecePosition ->
                if (!isFieldUnavailable(board, piecePosition) && FieldValidator.isOpponent(board, color, piecePosition)) {
                    addPossibleMove(board, currentPosition, piecePosition, possibleMoves, disableCheckCheck)
                }
            }
        }

        enPassant(currentPosition, board, possibleMoves, disableCheckCheck)

        return possibleMoves
    }

    private fun enPassant(
            currentPosition: PiecePosition,
            board: Board,
            possibleMoves: MutableList<PiecePosition>,
            disableCheckCheck: Boolean
    ) {
        val history = History() // TODO get History from GameViewModel
        val lastMove = history.getLastMoves(1).getOrNull(0)

        if (lastMove != null && lastMove.fromPiece is Pawn && lastMove.fromPiece.color != color) {
            val lastMoveTargetRow = lastMove.to.row
            val lastMoveTargetCol = lastMove.to.col

            if (currentPosition.row == lastMoveTargetRow && abs(currentPosition.col - lastMoveTargetCol) == 1) {
                val enPassantRow = if (color == PieceColor.WHITE) 4 else 3
                if (currentPosition.row == enPassantRow) {
                    val enPassantPosition = PiecePosition(enPassantRow + getMoveDirection(), lastMoveTargetCol)
                    addPossibleMove(board, currentPosition, enPassantPosition, possibleMoves, disableCheckCheck)
                }
            }
        }
    }

    private fun specialMoveFromBaseLine(
            currentPosition: PiecePosition,
            board: Board,
            possibleMoves: MutableList<PiecePosition>,
            disableCheckCheck: Boolean
    ) {
        if (moved) return
        PiecePosition(currentPosition.row + 2 * getMoveDirection(), currentPosition.col).let { piecePosition ->
            if (FieldValidator.isEmpty(board, piecePosition)) {
                addPossibleMove(board, currentPosition, piecePosition, possibleMoves, disableCheckCheck)
            }
        }
    }

    private fun getMoveDirection() = if (color == PieceColor.WHITE) -1 else 1
}