package janorschke.meyer.service.model.game

import android.os.CountDownTimer
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TimeMode
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.model.game.player.PlayerFactory
import janorschke.meyer.viewModel.GameViewModel

class Game(
        private val gameViewModel: GameViewModel,
        timeMode: TimeMode,
        playerNameWhite: String,
        playerNameBlack: String,
        aiLevelWhite: AiLevel?,
        aiLevelBlack: AiLevel?
) {
    val playerWhite = PlayerFactory(PieceColor.WHITE, playerNameWhite, aiLevelWhite, timeMode).create()
    val playerBlack = PlayerFactory(PieceColor.BLACK, playerNameBlack, aiLevelBlack, timeMode).create()

    private var activeColor: PieceColor = PieceColor.WHITE
    private var status: GameStatus = GameStatus.RUNNING
    private var selectedPosition: Position? = null
    private var possibleMoves: MutableList<PossibleMove> = mutableListOf()
    private var countdownTimer: CountDownTimer? = null

    val activePlayer get() = if (activeColor == PieceColor.WHITE) playerWhite else playerBlack
    val aiPlayer: AiPlayer?
        get() {
            if (playerWhite is AiPlayer) return playerWhite
            else if (playerBlack is AiPlayer) return playerBlack
            return null
        }
    val requiredAiPlayer get() = aiPlayer!!

    init {
        setCountdownTimer()
    }

    /**
     * Set the next player to move pieces
     */
    fun setNextPlayer() {
        activeColor = activeColor.opponent()
    }

    /**
     * Sets the Timer from the remainingTime
     */
    fun setCountdownTimer() {
        if (activePlayer.remainingTime == null) return

        countdownTimer = object : CountDownTimer(activePlayer.requiredRemainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                activePlayer.requiredRemainingTime = millisUntilFinished
                gameViewModel.timerTick()
            }

            override fun onFinish() {
                activePlayer.requiredRemainingTime = 0
                status = GameStatus.TIME_OVER
                gameViewModel.timerTick()
            }
        }.start()
    }

    /**
     * Stops the CountdownTimer if active
     */
    fun stopCountdownTimer() {
        this.countdownTimer?.cancel()
    }

    /**
     * Sets status of the game
     *
     * @param status of the game
     */
    fun setStatus(status: GameStatus) {
        this.status = status
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param selectedPosition the position of the selected piece
     * @param possibleMoves the possible moves for the selected piece
     */
    fun setSelectedPiece(selectedPosition: Position? = null, possibleMoves: MutableList<PossibleMove> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    fun getActiveColor() = activeColor

    fun getStatus() = status

    fun getSelectedPosition() = selectedPosition

    fun getPossibleMoves() = possibleMoves
}