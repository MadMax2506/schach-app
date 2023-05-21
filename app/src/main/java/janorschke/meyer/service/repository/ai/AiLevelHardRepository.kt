package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.utils.TimeTracking

private const val LOG_TAG = "AiLevelThreeRepository"

/**
 * Represents a hard ai
 * @see AiLevel.CHRIS
 */
class AiLevelHardRepository(color: PieceColor, board: Board, history: History) : AiRepository(color, board, history, AiLevel.CHRIS) {
    override fun calculateNextMove() = TimeTracking.log(LOG_TAG, "calculateNextMove") { calculateNextMove(16) }
}