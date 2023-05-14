package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Pawn
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.utils.board.PiecePosition

private const val LOG_TAG = "BoardRepository"

class BoardRepository(private val board: Board, private val history: History, private val game: Game) {
    private val gameRepository: GameRepository = GameRepository(board, history, game)

    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     *
     * @param fromPosition the source position of the chess piece
     * @param toPosition the target position to move the chess piece to
     */
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        val piece = board.getField(fromPosition)
        val possibleMoves = piece?.possibleMoves(board, fromPosition) ?: emptyList()

        if (toPosition in possibleMoves) {
            movePiece(fromPosition, toPosition)
            gameRepository.checkEndOfGame(piece!!)

            if (game.getStatus() == GameStatus.RUNNING) game.setColor(game.getColor().opponent())
        }
        game.setSelectedPiece()
    }

    /**
     * Moves a piece to the target position
     *
     * @param from source position
     * @param to target position
     */
    private fun movePiece(from: PiecePosition, to: PiecePosition) {
        val boardMove = createBoardMove(from, to)
        history.push(boardMove)

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
        val fromPiece = board.getField(from)!!
        val toPiece = board.getField(to)

        fromPiece.move()

        board.setField(from, null)
        if (fromPiece is Pawn && to.row == fromPiece.color.opponent().borderlineIndex) {
            board.setField(to, Queen(fromPiece.color))
        } else {
            board.setField(to, fromPiece)
        }

        Board(board).getFields().apply {
            return Move(this, from, to, fromPiece, toPiece)
        }
    }
}