package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.board.PiecePosition
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.repository.GameRepository

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
    val pawnDifferenceWhite: MutableLiveData<Int> = MutableLiveData()
    val beatenPiecesByBlack: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferenceBlack: MutableLiveData<Int> = MutableLiveData()

    private val game = Game(textResourceWhite, textResourceBlack, aiLevelWhite, aiLevelBlack)
    private val board = Board()
    private val history = History()

    // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/111
    //private val aiRepository = AiRepositoryFactory(game, board).create()
    private val gameRepository = GameRepository(board, history, game)

    // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/111
    //private val boardRepository = BoardRepository(board, history, game, gameRepository, aiRepository)
    private val boardRepository = BoardRepository(board, history, game, gameRepository)

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
        if (!liveData.value.contentDeepEquals(data)) liveData.value = data.map { it.copyOf() }.toTypedArray()
    }
}