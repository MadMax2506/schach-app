package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.game.Game
import janorschke.meyer.service.model.game.GameStatus
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * View model of the board history
 *
 * @param application for the current activity
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val statusObservable: LiveData<GameStatus?>
    private val selectedPositionObservable: LiveData<PiecePosition?>
    private val possibleMovesObservable: LiveData<MutableList<PiecePosition>>

    init {
        statusObservable = MutableLiveData()
        statusObservable.value = Game.getStatus()

        selectedPositionObservable = MutableLiveData()
        selectedPositionObservable.value = Game.getSelectedPosition()

        possibleMovesObservable = MutableLiveData()
        possibleMovesObservable.value = Game.getPossibleMoves()
    }

    /**
     * @return the live data for the game status
     */
    fun getGameStatusPosition() = statusObservable

    /**
     * @return the live data for the selected position
     */
    fun getSelectedPosition() = selectedPositionObservable

    /**
     * @return the live data list for the possible moves
     */
    fun getPossibleMovesObservable() = possibleMovesObservable
}