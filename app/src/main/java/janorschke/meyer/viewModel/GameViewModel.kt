package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.Player
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.repository.GameRepository
import janorschke.meyer.service.repository.ai.AiRepositoryFactory
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * View model for the game activity
 *
 * @param application for the current activity
 */
class GameViewModel(
        application: Application,
        textResourceWhite: Int,
        textResourceBlack: Int,
        aiLevelWhite: AiLevel?,
        aiLevelBlack: AiLevel?
) : AndroidViewModel(application) {
    // live data for the view
    val activePlayer: MutableLiveData<Player> = MutableLiveData()
    val playerWhite: MutableLiveData<Player> = MutableLiveData()
    val playerBlack: MutableLiveData<Player> = MutableLiveData()
    val status: MutableLiveData<GameStatus> = MutableLiveData()
    val selectedPosition: MutableLiveData<PiecePosition?> = MutableLiveData()
    val possibleMoves: MutableLiveData<MutableList<PiecePosition>> = MutableLiveData()
    val fields: MutableLiveData<Array<Array<Piece?>>> = MutableLiveData()
    val moves: MutableLiveData<MutableList<Move>> = MutableLiveData()
    val beatenPiecesByWhite: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val beatenPiecesByBlack: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifference: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private val game = Game(textResourceWhite, textResourceBlack, aiLevelWhite, aiLevelBlack)
    private val board = Board()
    private val history = History()
    private val aiRepository = AiRepositoryFactory(game, board).create()
    private val gameRepository = GameRepository(board, history, game)
    private val boardRepository = BoardRepository(board, history, game, gameRepository, aiRepository)

    init {
        setValues()
    }

    /**
     * @see BoardRepository.tryToMovePiece
     */
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        boardRepository.tryToMovePiece(fromPosition, toPosition)
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
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PiecePosition> = mutableListOf()) {
        game.setSelectedPiece(selectedPosition, possibleMoves)
        setValues()
    }

    /**
     * Set observable values
     */
    private fun setValues() {
        // game settings
        updateIfDifferent(activePlayer, game.getPlayer())
        updateIfDifferent(playerWhite, game.playerWhite)
        updateIfDifferent(playerBlack, game.playerBlack)
        updateIfDifferent(status, game.getStatus())
        updateIfDifferent(selectedPosition, game.getSelectedPosition())
        updateListIfDifferent(possibleMoves, game.getPossibleMoves())

        // board
        updateDeepArrayIfDifferent(fields, board.getFields())

        // history
        updateListIfDifferent(moves, history.getMoves())
        updateListIfDifferent(beatenPiecesByWhite, history.getBeatenPieces(PieceColor.WHITE.opponent()))
        updateListIfDifferent(beatenPiecesByBlack, history.getBeatenPieces(PieceColor.BLACK.opponent()))
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/70
        // updateIfDifferent(pawnDifferent, ...)
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
    private fun <T> updateListIfDifferent(liveData: MutableLiveData<MutableList<T>>, data: MutableList<T>) {
        if (data.size != liveData.value?.size || data != liveData.value) liveData.value = data.toMutableList()
    }

    /**
     * Updates the deep array of live data if it is different to the given data
     *
     * @param liveData
     * @param data
     */
    private fun updateDeepArrayIfDifferent(liveData: MutableLiveData<Array<Array<Piece?>>>, data: Array<Array<Piece?>>) {
        if (!liveData.value.contentDeepEquals(data)) liveData.value = data.map { it.copyOf() }.toTypedArray()
    }
}