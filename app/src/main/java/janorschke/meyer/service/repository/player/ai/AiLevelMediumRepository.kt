package janorschke.meyer.service.repository.player.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

/**
 * Represents a medium ai
 * @see AiLevel.MAX
 */
class AiLevelMediumRepository(color: PieceColor) : AiRepository(color, AiLevel.MAX)