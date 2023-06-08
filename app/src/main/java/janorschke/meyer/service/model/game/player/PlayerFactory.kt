package janorschke.meyer.service.model.game.player

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.PieceColor

/**
 * Creates an instance of the player, depending on the given parameters
 *
 * @param color of the player
 * @param name to display the player name
 * @param aiLevel of the player (<b>OPTIONAL</b>, Default=<code>null</code>)
 */
class PlayerFactory(
        private val color: PieceColor,
        private val name: String,
        private val aiLevel: AiLevel? = null,
        private val time: Long?
) {
    /**
     * @return the created player
     */
    fun create(): Player {
        if (aiLevel != null) return AiPlayer(color, name, aiLevel)
        return Player(color, name, remainingTime = time)
    }
}