package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.board.Board
import janorschke.meyer.service.model.game.board.History
import janorschke.meyer.service.model.game.board.Move
import janorschke.meyer.service.model.game.piece.Piece
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * View model for the game activity
 *
 * @param application for the current activity
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {
    val playerColor: MutableLiveData<PieceColor> = MutableLiveData()
    val status: MutableLiveData<GameStatus?> = MutableLiveData()
    val selectedPosition: MutableLiveData<PiecePosition?> = MutableLiveData()
    val possibleMoves: MutableLiveData<MutableList<PiecePosition>> = MutableLiveData()

    val fields: MutableLiveData<Array<Array<Piece?>>> = MutableLiveData()

    val moves: MutableLiveData<MutableList<Move>> = MutableLiveData()
    val beatenPiecesByWhite: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val beatenPiecesByBlack: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    val pawnDifferent: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private val game = Game()
    private val board = Board()
    private val history = History()
    private val boardRepository = BoardRepository(board, history, game)

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
        playerColor.value = game.getColor()
        status.value = game.getStatus()
        selectedPosition.value = game.getSelectedPosition()
        possibleMoves.value = game.getPossibleMoves()

        // board
        fields.value = board.getFields()

        // history
        moves.value = history.getMoves()
        beatenPiecesByWhite.value = history.getBeatenPieces(PieceColor.WHITE.opponent())
        beatenPiecesByBlack.value = history.getBeatenPieces(PieceColor.BLACK.opponent())
        // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/70
        // pawnDifferent.value =
    }
}