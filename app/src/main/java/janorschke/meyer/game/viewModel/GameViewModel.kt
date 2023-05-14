package janorschke.meyer.game.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.game.service.model.game.Game
import janorschke.meyer.game.service.utils.board.PiecePosition

/**
 * View model of the board history
 *
 * @param application for the current activity
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val selectedPositionObservable: LiveData<PiecePosition?>
    private val possibleMovesObservable: LiveData<MutableList<PiecePosition>>

    init {
        selectedPositionObservable = MutableLiveData()
        selectedPositionObservable.value = Game.getSelectedPosition()

        possibleMovesObservable = MutableLiveData()
        possibleMovesObservable.value = Game.getPossibleMoves()
    }

    /**
     * @return the live data for the selected position
     */
    fun getSelectedPosition() = selectedPositionObservable

    /**
     * @return the live data list for the possible moves
     */
    fun getPossibleMovesObservable() = possibleMovesObservable
}