package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History

/**
 * Represents an easy ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelEasyRepository(color: PieceColor, board: Board, history: History) : AiRepository(color, board, history, AiLevel.KEVIN_OTTO)