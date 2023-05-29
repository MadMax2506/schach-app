package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Pawn
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.utils.board.PiecePosition

private const val LOG_TAG = "BoardRepository"

class BoardRepository(
        private val board: Board,
        private val history: History,
        private val game: Game,
        private val gameRepository: GameRepository,
        private val aiRepository: AiRepository
) {
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        tryToMovePiece(fromPosition, toPosition, false)
    }

    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     *
     * @param fromPosition the source position of the chess piece
     * @param toPosition the target position to move the chess piece to
     * @param isAiMove if true, the move was produced by an ai
     */
    private fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition, isAiMove: Boolean) {
        val piece = board.getField(fromPosition)
        val possibleMoves = piece?.possibleMoves(board, fromPosition, history) ?: emptyList()

        // Check if requested position is a possible move of the piece
        if (toPosition !in possibleMoves) {
            game.setSelectedPiece()
            return
        }

        // Move piece and reset selection
        movePiece(fromPosition, toPosition)
        game.setColor(piece!!.color.opponent()) // TODO added for enPassantTest
        game.setSelectedPiece()

        // Check if game is finished or move was done by the ai
        if (gameRepository.checkEndOfGame(piece) || isAiMove) return

        // TODO commented out to test enpassant
        // Calculate the next move from the ai
        //aiRepository.calculateNextMove().let { move -> tryToMovePiece(move.from, move.to, true) }
    }

    /**
     * Moves a piece to the target position
     *
     * @param from source position
     * @param to target position
     */
    private fun movePiece(from: PiecePosition, to: PiecePosition) {
        // TODO toPosition is not always the position of the beaten Piece => enPassant

        val boardMove = createBoardMove(from, to)
        history.push(boardMove)

        if (boardMove.toPiece != null) Log.d(LOG_TAG, "${from.getNotation()} beat piece on ${to.getNotation()}")
        else Log.d(LOG_TAG, "Move piece from ${from.getNotation()} to ${to.getNotation()}")
    }


    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     */
    private fun createBoardMove(from: PiecePosition, to: PiecePosition): Move {
        val fromPiece = board.getField(from)!!
        val toPiece = board.getField(to)

        fromPiece.markAsMove()

        board.setField(from, null)
        if (fromPiece is Pawn && to.row == fromPiece.color.opponent().borderlineIndex) {
            // pawn can be transfer to an higher valency piece
            board.setField(to, Queen(fromPiece.color))
        } else {
            // normal move
            board.setField(to, fromPiece)
        }

        Board(board).let { boardCopy -> return Move(boardCopy.getFields(), from, to, fromPiece, toPiece) }
    }
}