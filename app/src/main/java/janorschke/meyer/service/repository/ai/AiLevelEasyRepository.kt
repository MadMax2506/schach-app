package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board

/**
 * Represents an easy ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelEasyRepository(color: PieceColor, board: Board, readFromFile: Boolean) : AiRepository(color, AiLevel.KEVIN_OTTO, board, readFromFile)