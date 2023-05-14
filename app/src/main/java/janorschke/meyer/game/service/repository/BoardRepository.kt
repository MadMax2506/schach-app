package janorschke.meyer.game.service.repository

import android.util.Log
import janorschke.meyer.game.service.model.History
import janorschke.meyer.game.service.model.Move
import janorschke.meyer.game.service.model.board.BoardCopy
import janorschke.meyer.game.service.model.board.BoardGame
import janorschke.meyer.game.service.model.piece.Pawn
import janorschke.meyer.game.service.model.piece.lineMoving.Queen
import janorschke.meyer.game.service.utils.board.PiecePosition

private const val LOG_TAG = "BoardRepository"

object BoardRepository {
    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     *
     * @param fromPosition the source position of the chess piece
     * @param toPosition the target position to move the chess piece to
     */
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        val piece = BoardGame.getField(fromPosition)
        val possibleMoves = piece?.possibleMoves(BoardGame, fromPosition) ?: emptyList()

        if (toPosition in possibleMoves) {
            movePiece(fromPosition, toPosition)
            GameRepository.checkEndOfGame(piece!!)
        }
    }

    /**
     * Moves a piece to the target position
     *
     * @param from source position
     * @param to target position
     */
    private fun movePiece(from: PiecePosition, to: PiecePosition) {
        val boardMove = createBoardMove(from, to)
        History.push(boardMove)

        if (boardMove.toPiece != null) {
            Log.d(LOG_TAG, "${from.getNotation()} beat piece on ${to.getNotation()}")
        } else {
            Log.d(LOG_TAG, "Move piece from ${from.getNotation()} to ${to.getNotation()}")
        }
    }


    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     */
    private fun createBoardMove(from: PiecePosition, to: PiecePosition): Move {
        val fromPiece = BoardGame.getField(from)!!
        val toPiece = BoardGame.getField(to)

        fromPiece.move()

        BoardGame.setField(from, null)
        if (fromPiece is Pawn && to.row == fromPiece.color.opponent().borderlineIndex) {
            BoardGame.setField(to, Queen(fromPiece.color))
        } else {
            BoardGame.setField(to, fromPiece)
        }

        BoardCopy(BoardGame).getFields().apply {
            return Move(this, from, to, fromPiece, toPiece)
        }
    }
}