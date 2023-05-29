package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board

/**
 * Represents an easy ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelEasyRepository(color: PieceColor, board: Board) : AiRepository(color, board, AiLevel.KEVIN_OTTO.deepness)