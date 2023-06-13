package janorschke.meyer.service.repository.game

import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History

class GameRepositoryFactory(private val board: Board, private val history: History, private val game: Game) {
    fun create(): GameRepository {
        if (game.aiPlayer == null) TODO("Network player")

        return AiGameRepository(board, history, game)
    }
}