package janorschke.meyer.service.repository.ai

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.ai.AiEvaluationTree
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.TimeTracking

private const val LOG_TAG = "AiRepository"

/**
 * Handles the ai moves
 *
 * @param color of the ai
 * @param board instance
 * @param history instance
 * @param level of the ai
 */
abstract class AiRepository(val color: PieceColor, board: Board, history: History, level: AiLevel) {
    private lateinit var tree: AiEvaluationTree

    init {
        if (level.deepness < 1 || level.deepness % 2 != 0) throw IllegalArgumentException("Deepness is invalid")
        Log.d(LOG_TAG, "Create evaluation tree for the ai=$level with deepness=${level.deepness}")

        TimeTracking.log(LOG_TAG, "init") { tree = AiEvaluationTree(History(history), level, board, color) }
    }

    /**
     * @see AiEvaluationTree.applyMove
     */
    fun applyMove(move: Move) = tree.applyMove(move)

    /**
     * @return the next possible move
     */
    fun calculateNextMove() = TimeTracking.log(LOG_TAG, "calculateNextMove") { tree.calculateBestMove() }
}