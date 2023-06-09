package janorschke.meyer.service.repository

import android.util.Log
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.utils.BoardUtils
import janorschke.meyer.service.validator.BoardValidator

private const val LOG_TAG = "GameRepository"

class GameRepository(private val board: Board, private val history: History, private val game: Game) {
    fun playerOffersDraw() {
        val aiLevel = game.aiPlayer.aiLevel

        val offeringPlayerValency = BoardUtils.calculatePieceValency(board, game.getActiveColor())
        val opponentPlayerValency = BoardUtils.calculatePieceValency(board, game.getActiveColor().opponent())
        val valencyDiff = opponentPlayerValency - offeringPlayerValency

        // Accept draw if the other player has more than one pawn
        if (aiLevel == AiLevel.KEVIN_OTTO && valencyDiff < -1) game.setStatus(GameStatus.DRAW)

        // Accept draw if the other player has more than two pawns
        if (aiLevel == AiLevel.MAX && valencyDiff < -2) game.setStatus(GameStatus.DRAW)

        // Accept draw if the other player has more than a light piece
        if (aiLevel == AiLevel.CHRIS && valencyDiff < -3) game.setStatus(GameStatus.DRAW)
    }

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

    /**
 * Handles the move by setting the next player and performing corresponding actions.
 * - Sets the next player in the game.
 * - If the active player is an AI player, stops the countdown timer.
 * - If the active player is not an AI player, sets the countdown timer.
     */
    fun handleMove() {
        game.setNextPlayer()

        if (game.activePlayer is AiPlayer) game.stopCountdownTimer()
        else game.setCountdownTimer()
    }
}