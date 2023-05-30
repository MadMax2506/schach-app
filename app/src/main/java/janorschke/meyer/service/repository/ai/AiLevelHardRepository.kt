package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board

/**
 * Represents a hard ai
 * @see AiLevel.CHRIS
 */
class AiLevelHardRepository(color: PieceColor, board: Board, readFromFile: Boolean) : AiRepository(color, AiLevel.CHRIS, board, readFromFile)