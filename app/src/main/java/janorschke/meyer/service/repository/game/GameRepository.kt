package janorschke.meyer.service.repository.game

import android.util.Log
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.validator.BoardValidator

private const val LOG_TAG = "GameRepository"

abstract class GameRepository(protected val board: Board, protected val history: History, protected val game: Game) {
    /**
     * @return `true`, if the draw is accepted
     */
    abstract fun playerOffersDraw(): Boolean

    /**
     * Handles the move by setting the next player and performing corresponding actions.
     * - Sets the next player in the game.
     * - If the active player is an AI player, stops the countdown timer.
     * - If the active player is not an AI player, sets the countdown timer.
     */
    abstract fun handleMove()

    /**
     * Check if the game is finished
     *
     * @param piece which has moved
     * @return true if the game is finished
     */
    fun checkEndOfGame(piece: Piece): Boolean {
        if (BoardValidator.isKingCheckmate(board, history, piece.color.opponent())) {
            Log.d(LOG_TAG, "Game ends with CHECKMATE")
            game.setStatus(GameStatus.CHECKMATE)
            return true
        } else if (BoardValidator.isStalemate(board, history, piece.color.opponent())) {
            Log.d(LOG_TAG, "Game ends with STALEMATE")
            game.setStatus(GameStatus.STALEMATE)
            return true
        }
        return false
    }
}