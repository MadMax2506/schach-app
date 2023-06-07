package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
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
        val possibleMoves = piece?.possibleMoves(board, history, fromPosition) ?: emptyList()
        val possibleMove = possibleMoves.firstOrNull { it.toPosition == toPosition }

        // Check if requested position is a possible move of the piece
        if (possibleMove == null) {
            game.setSelectedPiece()
            return
        }

        // Move piece and reset selection
        movePiece(possibleMove)
        game.setSelectedPiece()

        // Check if game is finished or
        if (gameRepository.checkEndOfGame(piece!!)) return

        game.setColor(game.getColor().opponent())

        // move was done by the ai
        if (isAiMove) return

        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/99
        GlobalScope.launch {
            aiRepository.calculateNextMove(board, history).let { move -> tryToMovePiece(move.fromPosition, move.toPosition, true) }
            withContext(Dispatchers.Main) { gameViewModel.aiMoved() }
        }
    }

    /**
     * Moves a piece to the target position
     *
     * @param possibleMove
     */
    private fun movePiece(possibleMove: PossibleMove) {
        val move = createMove(possibleMove)
        history.push(move)

        if (move.beatenPiece != null) Log.d(LOG_TAG, "${possibleMove.fromPosition.getNotation()} beat piece on ${possibleMove.toPosition.getNotation()}")
        else Log.d(LOG_TAG, "Move piece from ${possibleMove.fromPosition.getNotation()} to ${possibleMove.toPosition.getNotation()}")
    }


    /**
     * Moves an piece to another position
     *
     * @param possibleMove
     * @return board move
     *
     * @see Board.createMove
     */
    private fun createMove(possibleMove: PossibleMove): Move {
        board.getField(possibleMove.fromPosition)!!.let { piece ->
            if (BoardValidator.isPawnTransformation(piece, possibleMove.toPosition)) {
                // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/49
                return board.createMove(possibleMove.fromPosition, possibleMove.toPosition, Queen(piece.color))
            } else {
                return board.createMove(possibleMove)
            }
        }
    }
}