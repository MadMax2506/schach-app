package janorschke.meyer.service.model.game

import android.os.CountDownTimer
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.player.PlayerFactory
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.PossibleMove

class Game(
        playerNameWhite: String, playerNameBlack: String,
        aiLevelWhite: AiLevel?, aiLevelBlack: AiLevel?,
        time: Long?
) {

    val playerWhite = PlayerFactory(PieceColor.WHITE, playerNameWhite, aiLevelWhite, time).create()
    val playerBlack = PlayerFactory(PieceColor.BLACK, playerNameBlack, aiLevelBlack, time).create()

    /**
     * Color of the player who is moving
     */
    private var color: PieceColor = PieceColor.WHITE

    /**
     * Current status of the game
     */
    private var status: GameStatus = GameStatus.RUNNING

    /**
     * Current selected position
     */
    private var selectedPosition: PiecePosition? = null

    /**
     * Possible moves for the piece on the selected position
     */
    private var possibleMoves: MutableList<PossibleMove> = mutableListOf()

    /**
     * CountdownTimer for the game
     */
    private var countdownTimer: CountDownTimer? = null
    /**
     * Sets color of the current player
     *
     * @param color of the player
     */
    fun setColor(color: PieceColor) {
        this.color = color
    }

    /**
     * Sets the Timer from the remainingTime
     */
    fun setCountdownTimer() {
        val activePlayer = getPlayer()
        if(activePlayer.remainingTime == null) return
        countdownTimer = object : CountDownTimer(activePlayer.remainingTime!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                activePlayer.remainingTime = millisUntilFinished
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                // TODO Zeit anzeigen https://github.com/users/MadMax2506/projects/19/views/1?pane=issue&itemId=29217573
//                binding.playerTwo!!.time.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                activePlayer.remainingTime = 0
                // TODO Dialog anzeigen https://github.com/users/MadMax2506/projects/19/views/1?pane=issue&itemId=29217573
                //  irgendwie die Beziehung jetzt falschrum...
//                gameViewModel.gameTimeOver()
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
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PossibleMove> = mutableListOf()) {
        this.selectedPosition = selectedPosition
        this.possibleMoves = possibleMoves
    }

    fun getPlayer() = if (color == PieceColor.WHITE) playerWhite else playerBlack

    fun getColor() = color

    fun getStatus() = status

    fun getSelectedPosition() = selectedPosition

    fun getPossibleMoves() = possibleMoves
}