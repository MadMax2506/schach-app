package janorschke.meyer.game.service.repository

import janorschke.meyer.game.service.model.board.BoardGame
import janorschke.meyer.game.service.model.game.Game
import janorschke.meyer.game.service.model.game.GameStatus
import janorschke.meyer.game.service.model.piece.Piece
import janorschke.meyer.game.service.validator.BoardValidator

private const val LOG_TAG = "GameRepository"

object GameRepository {
    /**
     * Check if the game is finished
     *
     * @param piece which has moved
     */
    fun checkEndOfGame(piece: Piece) {
        if (BoardValidator.isKingCheckmate(BoardGame, piece.color.opponent())) Game.setStatus(GameStatus.CHECKMATE)
        else if (BoardValidator.isStalemate(BoardGame, piece.color.opponent())) Game.setStatus(GameStatus.STALEMATE)
    }
}