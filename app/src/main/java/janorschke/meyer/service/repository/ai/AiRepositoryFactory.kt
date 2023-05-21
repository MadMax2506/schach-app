package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History

/**
 * Factory to create the repository for the correct ai level
 */
class AiRepositoryFactory(private val game: Game, private val board: Board, private val history: History) {
    init {
        if (game.playerWhite.aiLevel == null && game.playerBlack.aiLevel == null) {
            throw IllegalArgumentException("No player is an ai.")
        }
    }

    fun create(): AiRepository {
        return when (aiLevel()) {
            AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(aiColor(), board, history)
            AiLevel.MAX -> AiLevelMediumRepository(aiColor(), board, history)
            AiLevel.CHRIS -> AiLevelHardRepository(aiColor(), board, history)
        }
    }

    private fun aiColor() = if (game.playerWhite.aiLevel != null) PieceColor.WHITE else PieceColor.BLACK

    private fun aiLevel() = game.playerWhite.aiLevel ?: game.playerBlack.aiLevel!!
}