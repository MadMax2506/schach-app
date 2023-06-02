package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.viewModel.GameViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val LOG_TAG = "BoardRepository"

class BoardRepository(
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/99
        private val gameViewModel: GameViewModel,
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
    @OptIn(DelicateCoroutinesApi::class)
    private fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition, isAiMove: Boolean) {
        val piece = board.getField(fromPosition)
        val possibleMoves = piece?.possibleMoves(board, fromPosition) ?: emptyList()

        // Check if requested position is a possible move of the piece
        if (toPosition !in possibleMoves) {
            game.setSelectedPiece()
            return
        }

        // Move piece and reset selection
        movePiece(fromPosition, toPosition)
        game.setSelectedPiece()

        // Check if game is finished or move was done by the ai
        if (gameRepository.checkEndOfGame(piece!!) || isAiMove) return

        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/99
        GlobalScope.launch {
            aiRepository.calculateNextMove(board, history).let { move -> tryToMovePiece(move.from, move.to, true) }
            withContext(Dispatchers.Main) { gameViewModel.aiMoved() }
        }
    }

    /**
     * Moves a piece to the target position
     *
     * @param from source position
     * @param to target position
     */
    private fun movePiece(from: PiecePosition, to: PiecePosition) {
        val move = createMove(from, to)
        history.push(move)

        if (move.toPiece != null) Log.d(LOG_TAG, "${from.getNotation()} beat piece on ${to.getNotation()}")
        else Log.d(LOG_TAG, "Move piece from ${from.getNotation()} to ${to.getNotation()}")
    }


    /**
     * Moves an piece to another position
     *
     * @param from source position
     * @param to target position
     * @return board move
     *
     * @see Board.createMove
     */
    private fun createMove(from: PiecePosition, to: PiecePosition): Move {
        board.getField(from)!!.let { piece ->
            if (BoardValidator.isPawnTransformation(piece, to)) {
                // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/49
                return board.createMove(from, to)
            } else {
                return board.createMove(from, to)
            }
        }
    }
}