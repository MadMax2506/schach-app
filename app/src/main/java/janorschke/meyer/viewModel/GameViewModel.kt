package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.Game

/**
 * View model of the game settings
 *
 * @param application for the current activity
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val colorObservable: MutableLiveData<PieceColor> = MutableLiveData()
    private val statusObservable: MutableLiveData<GameStatus?> = MutableLiveData()

    init {
        colorObservable.value = Game.getColor()
        statusObservable.value = Game.getStatus()
    }

    /**
     * @return the live data for the active player color
     */
    fun getPlayerColor() = colorObservable

    /**
     * @return the live data for the game status
     */
    fun getGameStatus() = statusObservable
}