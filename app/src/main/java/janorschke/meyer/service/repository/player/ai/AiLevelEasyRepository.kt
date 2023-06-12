package janorschke.meyer.service.repository.player.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

/**
 * Represents an easy ai
 * @see AiLevel.KEVIN_OTTO
 */
class AiLevelEasyRepository(color: PieceColor) : AiRepository(color, AiLevel.KEVIN_OTTO)