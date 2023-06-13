package janorschke.meyer.service.repository.player.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

/**
 * Represents a hard ai
 * @see AiLevel.CHRIS
 */
class AiLevelHardRepository(color: PieceColor) : AiRepository(color, AiLevel.CHRIS)