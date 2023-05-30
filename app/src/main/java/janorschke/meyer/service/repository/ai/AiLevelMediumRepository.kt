package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.board.Board

/**
 * Represents a medium ai
 * @see AiLevel.MAX
 */
class AiLevelMediumRepository(color: PieceColor, board: Board, readFromFile: Boolean) : AiRepository(color, AiLevel.MAX, board, readFromFile)