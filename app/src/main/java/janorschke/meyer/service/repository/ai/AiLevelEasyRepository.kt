package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiEvaluationType
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.utils.TimeTracking

private const val LOG_TAG = "AiLevelOneRepository"

/**
 * Represents an easy ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelEasyRepository(color: PieceColor, board: Board, history: History) : AiRepository(color, board, history, AiLevel.KEVIN_OTTO) {
    override fun calculateNextMove(): Move {
        return TimeTracking.log(LOG_TAG, "calculateNextMove") {
            calculateNextMove(AiEvaluationType.MIN_MAX_EVALUATION, 2)
        }
    }
}