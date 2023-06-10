package janorschke.meyer.service.repository.player

import janorschke.meyer.service.model.game.board.move.Move

interface PlayerRepository {
    /**
     * @param move which was execute
     */
    fun apply(move: Move)

    /**
     * @return the next possible [Move]
     */
    fun nextMove(): Move
}