package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.Move

/**
 * View model of the board history
 *
 * @param application for the current activity
 */
class MoveHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val boardHistoryObservable: MutableLiveData<MutableList<Move>> = MutableLiveData()

    init {
        boardHistoryObservable.value = janorschke.meyer.service.model.History.getMoves()
    }

    /**
     * @return the live data list for the board move
     */
    fun getBoardHistory() = boardHistoryObservable
}