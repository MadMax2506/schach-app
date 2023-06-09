package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TimeMode
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.board.PossibleMove
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.repository.GameRepository
import janorschke.meyer.service.repository.ai.AiRepositoryFactory
import janorschke.meyer.service.utils.ArrayUtils

/**
 * View model for the game activity
 *
 * @param application for the current activity
 */
class GameViewModel(
        application: Application,
        playerNameWhite: String,
        playerNameBlack: String,
        aiLevelWhite: AiLevel?,
        aiLevelBlack: AiLevel?,
        timeMode: TimeMode
) : AndroidViewModel(application) {
    // live data for the view
    val activePlayerColor: MutableLiveData<PieceColor> = MutableLiveData()
    val activePlayerTime: MutableLiveData<Long?> = MutableLiveData()
    val playerWhite: MutableLiveData<Player> = MutableLiveData()
    val playerBlack: MutableLiveData<Player> = MutableLiveData()
    val status: MutableLiveData<GameStatus> = MutableLiveData()
    val selectedPosition: MutableLiveData<PiecePosition?> = MutableLiveData()
    val possibleMoves: MutableLiveData<MutableList<PossibleMove>> = MutableLiveData()
    val fields: MutableLiveData<Array<Array<Piece?>>> = MutableLiveData()
    val moves: MutableLiveData<MutableList<Move>> = MutableLiveData()
    val beatenPiecesByWhite: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferenceWhite: MutableLiveData<Int> = MutableLiveData()
    val beatenPiecesByBlack: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferenceBlack: MutableLiveData<Int> = MutableLiveData()

    private val game = Game(this, timeMode, playerNameWhite, playerNameBlack, aiLevelWhite, aiLevelBlack)
    private val board = Board()
    private val history = History()
    private val aiRepository = AiRepositoryFactory(game).create()
    private val gameRepository = GameRepository(board, history, game)
    private val boardRepository = BoardRepository(this, board, history, game, gameRepository, aiRepository)

    fun getHistory() = history

    init {
        playerWhite.value = game.playerWhite
        playerBlack.value = game.playerBlack

        setValues()
    }

    /**
     * @see BoardRepository.tryToMovePiece
     */
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        boardRepository.tryToMovePiece(fromPosition, toPosition)
        setValues()
    }

    fun surrenderGame() {
        game.setStatus(GameStatus.SURRENDERED)
        setValues()
    }

    fun voteDraw() {
        gameRepository.playerOffersDraw()
        setValues()
    }

    fun timerTick() {
        setValues()
    }

    fun stopCountdownTimer() {
        game.stopCountdownTimer()
        setValues()
    }

    fun aiMoved() {
        setValues()
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param selectedPosition the position of the selected piece (optional: Default = null)
     * @param possibleMoves the possible moves for the selected piece (optional: Default = emptyList())
     *
     * @see Game.selectedPosition
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PossibleMove> = mutableListOf()) {
        game.setSelectedPiece(selectedPosition, possibleMoves)
        setValues()
    }

    /**
     * Set observable values
     */
    private fun setValues() {
        // game settings
        updateIfDifferent(activePlayerColor, game.getActiveColor())
        updateIfDifferent(activePlayerTime, game.activePlayer.time)
        updateIfDifferent(status, game.getStatus())
        updateIfDifferent(selectedPosition, game.getSelectedPosition())
        updateIfDifferent(possibleMoves, game.getPossibleMoves())

        // board
        updateIfDifferent(fields, board.getFields())
        updateIfDifferent(pawnDifferenceWhite, board.getPawnDifferenceByColor(PieceColor.WHITE))
        updateIfDifferent(pawnDifferenceBlack, board.getPawnDifferenceByColor(PieceColor.BLACK))

        // history
        updateIfDifferent(moves, history.getMoves())
        updateIfDifferent(beatenPiecesByWhite, history.getBeatenPieces(PieceColor.WHITE.opponent()))
        updateIfDifferent(beatenPiecesByBlack, history.getBeatenPieces(PieceColor.BLACK.opponent()))
    }

    /**
     * Updates the live data if it is different to the given data
     *
     * @param liveData
     * @param data
     */
    private fun <T> updateIfDifferent(liveData: MutableLiveData<T>, data: T) {
        if (liveData.value != data) liveData.value = data
    }

    /**
     * Updates the mutable list of live data if it is different to the given data
     *
     * @param liveData
     * @param data
     */
    private fun <T> updateIfDifferent(liveData: MutableLiveData<MutableList<T>>, data: MutableList<T>) {
        if (data.size != liveData.value?.size || data != liveData.value) liveData.value = data.toMutableList()
    }

    /**
     * Updates the deep array of live data if it is different to the given data
     *
     * @param liveData
     * @param data
     */
    private fun updateIfDifferent(liveData: MutableLiveData<Array<Array<Piece?>>>, data: Array<Array<Piece?>>) {
        if (!liveData.value.contentDeepEquals(data)) liveData.value = ArrayUtils.deepCopy(data)
    }
}