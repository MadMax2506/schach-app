package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.lineMoving.Queen
import janorschke.meyer.service.repository.ai.AiRepository
import janorschke.meyer.service.validator.BoardValidator
import janorschke.meyer.view.dialog.PromotionDialog
import janorschke.meyer.viewModel.GameViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val LOG_TAG = "BoardRepository"
private const val PROMOTION_DIALOG_TAG = "PromotionDialog"

class BoardRepository(
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/99
        private val gameViewModel: GameViewModel,
        private val board: Board,
        private val history: History,
        private val game: Game,
        private val gameRepository: GameRepository,
        private val aiRepository: AiRepository
) {
    fun tryToMovePiece(position: Position) {
        val possibleMove = game.getPossibleMoves().firstOrNull { it.to.position == position }
        tryToMovePiece(possibleMove, false)
    }

    /**
     * Moves a chess piece from the source position to the target position, if the target position is valid.
     *
     * @param possibleMove of the piece
     * @param isAiMove if true, the move was produced by an ai
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun tryToMovePiece(possibleMove: PossibleMove?, isAiMove: Boolean) {
        // Check if requested position is a possible move of the piece
        if (possibleMove == null) {
            game.setSelectedPiece()
            return
        }

        val fromPosition = possibleMove.from.position
        val piece = board.getField(fromPosition)

        // Move piece and reset selection
        movePiece(possibleMove)
        game.setSelectedPiece()

        // Check if game is finished or
        if (gameRepository.checkEndOfGame(piece!!)) return

        gameRepository.handleMove()

        // move was done by the ai
        if (isAiMove) return

        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/99
        GlobalScope.launch {
            val move = aiRepository.calculateNextMove(board, history)
            withContext(Dispatchers.Main) {
                tryToMovePiece(move, true)
                gameViewModel.aiMoved()
            }
        }
    }

    /**
     * Moves a piece to the target position
     *
     * @param possibleMove from which the move is created to move the piece
     */
    private fun movePiece(possibleMove: PossibleMove) {
        val move = createMove(possibleMove)
        history.push(move)

        if (move.beaten.piece != null) Log.d(LOG_TAG, "${possibleMove.from.position.getNotation()} beat piece on ${possibleMove.to.position.getNotation()}")
        else Log.d(LOG_TAG, "Move piece from ${possibleMove.from.position.getNotation()} to ${possibleMove.to.position.getNotation()}")
    }


    /**
     * Moves an piece to another position
     *
     * @param possibleMove from which the move is created
     * @return board move
     *
     * @see Board.createMove
     */
    private fun createMove(possibleMove: PossibleMove): Move {
        val piece = possibleMove.from.requiredPiece
        if (BoardValidator.isPawnTransformation(piece, possibleMove.to.position)) {
            // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/49
            //PromotionDialog.newInstance().show(supportFragmentManager, PROMOTION_DIALOG_TAG)
            return board.createMove(possibleMove.from.position, possibleMove.to.position, Queen(piece.color))
        } else {
            return board.createMove(possibleMove)
        }
    }
}