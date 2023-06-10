package janorschke.meyer.service.repository.player

import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.move.Move

interface PlayerRepository {
    /**
     * @param board instance
     * @param history instance
     *
     * @return the next possible move
     */
    fun nextMove(board: Board, history: History): Move
}