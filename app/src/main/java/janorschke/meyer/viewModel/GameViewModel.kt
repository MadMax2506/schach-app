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
import janorschke.meyer.service.model.game.board.Position
import janorschke.meyer.service.model.game.board.move.Move
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.repository.game.GameRepositoryFactory
import janorschke.meyer.service.repository.player.PlayerRepositoryFactory
import janorschke.meyer.service.utils.ArrayUtils
import janorschke.meyer.view.callback.BoardRepositoryCallback

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
    val possibleMoves: MutableLiveData<Sequence<PossibleMove>> = MutableLiveData()
    val fields: MutableLiveData<Array<Array<Piece?>>> = MutableLiveData()
    val moves: MutableLiveData<MutableList<Move>> = MutableLiveData()
    val beatenPiecesByWhite: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferenceWhite: MutableLiveData<Int> = MutableLiveData()
    val beatenPiecesByBlack: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferenceBlack: MutableLiveData<Int> = MutableLiveData()

    private val game = Game(this, timeMode, playerNameWhite, playerNameBlack, aiLevelWhite, aiLevelBlack)
    private val board = Board()
    private val history = History()

    // TODO  async
    private val playerRepository = PlayerRepositoryFactory(game).create()
    private val gameRepository = GameRepositoryFactory(board, history, game).create()
    private val boardRepository = BoardRepository(this, board, history, game, gameRepository, playerRepository)

    fun getBoardRepository() = boardRepository

    init {
        playerWhite.value = game.playerWhite
        playerBlack.value = game.playerBlack

        setValues()
    }

    fun setBoardRepositoryCallback(callback: BoardRepositoryCallback) {
        boardRepository.setCallback(callback)
    }

    fun surrenderGame() {
        game.setStatus(GameStatus.SURRENDERED)
        setValues()
    }

    /**
     * @return `true`, if the draw is accepted
     */
    fun voteDraw() = gameRepository.playerOffersDraw().also { setValues() }

    fun timerTick() {
        setValues()
    }

    fun stopCountdownTimer() {
        game.stopCountdownTimer()
        setValues()
    }

    fun aiMoved() = setValues()

    fun onFieldClick(position: Position) {
        val piece = board.getField(position)
        val selectedPosition = game.getSelectedPosition()
        val isPlayersPiece = (piece?.color == game.getActiveColor())

        when {
            // Move piece to a valid position
            (selectedPosition != null && !isPlayersPiece) -> boardRepository.tryToMovePiece(position)

            // Set the current selected piece on the board
            (isPlayersPiece && (selectedPosition == null || selectedPosition != position)) -> {
                val possibleMoves = piece?.possibleMoves(Board(board), history, position) ?: emptySequence()
                game.setSelectedPiece(position, possibleMoves)
            }

            else -> game.setSelectedPiece()
        }
        setValues()
    }

    /**
     * Set observable values
     */
    private fun setValues() {
        // game settings
        updateIfDifferent(activePlayerColor, game.getActiveColor())
        updateIfDifferent(activePlayerTime, game.activePlayer.remainingTime)
        updateIfDifferent(status, game.getStatus())
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
    private fun <T> updateIfDifferent(liveData: MutableLiveData<Sequence<T>>, data: Sequence<T>) {
        if (data != liveData.value) liveData.value = data
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