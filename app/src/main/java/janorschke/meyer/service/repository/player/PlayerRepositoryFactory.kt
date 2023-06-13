package janorschke.meyer.service.repository.player

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.repository.player.ai.AiLevelEasyRepository
import janorschke.meyer.service.repository.player.ai.AiLevelHardRepository
import janorschke.meyer.service.repository.player.ai.AiLevelMediumRepository

/**
 * Factory to create the repository for the current player
 */
class PlayerRepositoryFactory(private val game: Game) {
    fun create(): PlayerRepository {
        if (game.aiPlayer == null) TODO("Network player")

        val color = game.requiredAiPlayer.color
        return when (game.requiredAiPlayer.aiLevel) {
            AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(color)
            AiLevel.MAX -> AiLevelMediumRepository(color)
            AiLevel.CHRIS -> AiLevelHardRepository(color)
        }
    }
}