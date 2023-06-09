package janorschke.meyer.service.model.game.player

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TimeMode

/**
 * Creates an instance of the player, depending on the given parameters
 *
 * @param color of the player
 * @param name to display the player name
 * @param aiLevel of the player
 * @param timeMode of the current game
 */
class PlayerFactory(
        private val color: PieceColor,
        private val name: String,
        private val aiLevel: AiLevel?,
        private val timeMode: TimeMode
) {
    /**
     * @return the created player
     */
    fun create(): Player {
        if (aiLevel != null) return AiPlayer(color, name, aiLevel, timeMode.time)
        return Player(color, name, timeMode.time)
    }
}