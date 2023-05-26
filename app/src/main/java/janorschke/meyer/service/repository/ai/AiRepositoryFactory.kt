package janorschke.meyer.service.repository.ai

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.player.AiPlayer

/**
 * Factory to create the repository for the correct ai level
 */
class AiRepositoryFactory(private val game: Game, private val board: Board, private val history: History) {
    private val aiPlayer: AiPlayer
        get() {
            if (game.playerWhite is AiPlayer) return game.playerWhite
            else if (game.playerBlack is AiPlayer) return game.playerBlack
            throw IllegalArgumentException("No player is an ai.")
        }

    fun create(): AiRepository {
        return when (aiPlayer.aiLevel) {
            AiLevel.KEVIN_OTTO -> AiLevelEasyRepository(aiPlayer.color, board, history)
            AiLevel.MAX -> AiLevelMediumRepository(aiPlayer.color, board, history)
            AiLevel.CHRIS -> AiLevelHardRepository(aiPlayer.color, board, history)
        }
    }
}