package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiBoardEvaluation
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 */
abstract class AiRepository(
        val color: PieceColor,
        protected val board:
        Board, val aiLevel: AiLevel,
        val history: History
) {
    /**
     * @return the next possible move
     */
    abstract fun calculateNextMove(): Move

    /**
     * @return all evaluated moves for the ai
     */
    protected fun evaluateBoard(): MutableList<AiBoardEvaluation> {
        Log.d(LOG_TAG, "Evaluate the board")

        TODO("evaluateBoard is not implemented")
    }
}