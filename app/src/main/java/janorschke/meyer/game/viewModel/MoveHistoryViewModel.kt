package janorschke.meyer.game.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.game.service.model.History
import janorschke.meyer.game.service.model.Move

/**
 * View model of the board history
 *
 * @param application for the current activity
 */
class MoveHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val boardHistoryObservable: LiveData<MutableList<Move>>

    init {
        boardHistoryObservable = MutableLiveData()
        boardHistoryObservable.value = History.getMoves()
    }

    /**
     * @return the live data list for the board move
     */
    fun getBoardHistory() = boardHistoryObservable
}