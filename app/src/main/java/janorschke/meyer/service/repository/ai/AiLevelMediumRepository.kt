package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.utils.TimeTracking

private const val LOG_TAG = "AiLevelTwoRepository"

/**
 * Represents a medium ai
 * @see AiLevel.MAX
 */
class AiLevelMediumRepository(color: PieceColor, board: Board, history: History) : AiRepository(color, board, history, AiLevel.MAX) {
    override fun calculateNextMove() = TimeTracking.log(LOG_TAG, "calculateNextMove") { calculateNextMove(4) }
}