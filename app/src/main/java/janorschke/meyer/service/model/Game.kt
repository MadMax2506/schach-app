package janorschke.meyer.service.model

import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor

object Game {
    /**
     * Color of the player who is moving
     */
    private var color: PieceColor = PieceColor.WHITE

    /**
     * Current status of the game
     */
    private var status: GameStatus = GameStatus.RUNNING

    fun reset() {
        color = PieceColor.WHITE
        status = GameStatus.RUNNING
    }

    /**
     * Sets color of the current player
     *
     * @param color of the player
     */
    fun setColor(color: PieceColor) {
        Game.color = color
    }

    /**
     * Sets status of the game
     *
     * @param status of the game
     */
    fun setStatus(status: GameStatus) {
        Game.status = status
    }

    fun getColor() = color

    fun getStatus() = status
}