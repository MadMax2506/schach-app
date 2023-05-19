package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board

/**
 * Factory to create the repository for the correct ai level
 */
class AiRepositoryFactory(private val game: Game, private val board: Board) {
    init {
        if (game.playerWhite.aiLevel == null && game.playerBlack.aiLevel == null) {
            throw IllegalArgumentException("No player is an ai.")
        }
    }

    fun create(): AiRepository {
        return when (aiLevel()) {
            AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(aiColor(), board)
            AiLevel.MAX -> AiLevelMediumRepository(aiColor(), board)
            AiLevel.CHRIS -> AiLevelHardRepository(aiColor(), board)
        }
    }

    private fun aiColor() = if (game.playerWhite.aiLevel != null) PieceColor.WHITE else PieceColor.BLACK

    private fun aiLevel() = game.playerWhite.aiLevel ?: game.playerBlack.aiLevel!!
}