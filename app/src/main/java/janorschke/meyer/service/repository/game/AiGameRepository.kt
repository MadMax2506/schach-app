package janorschke.meyer.service.repository.game

import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.utils.BoardUtils

class AiGameRepository(board: Board, history: History, game: Game) : GameRepository(board, history, game) {
    override fun playerOffersDraw(): Boolean {
        val aiLevel = game.requiredAiPlayer.aiLevel

        val offeringPlayerValency = BoardUtils.calculatePieceValency(board, game.getActiveColor())
        val opponentPlayerValency =
                BoardUtils.calculatePieceValency(board, game.getActiveColor().opponent())
        val valencyDiff = opponentPlayerValency - offeringPlayerValency

        val shouldAcceptDraw = when (aiLevel) {
            // Accept draw if the other player has more than one pawn
            AiLevel.KEVIN_OTTO -> valencyDiff < -1

            // Accept draw if the other player has more than two pawns
            AiLevel.MAX -> valencyDiff < -2

            // Accept draw if the other player has more than a light piece
            AiLevel.CHRIS -> valencyDiff < -3
        }

        if (shouldAcceptDraw) {
            game.setStatus(GameStatus.DRAW)
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
    override fun handleMove() {
        game.setNextPlayer()

        if (game.activePlayer is AiPlayer) game.stopCountdownTimer()
        else game.setCountdownTimer()
    }
}