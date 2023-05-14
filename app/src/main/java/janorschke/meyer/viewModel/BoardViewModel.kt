package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.board.BoardGame
import janorschke.meyer.service.model.piece.Piece

/**
 * View model of the board
 *
 * @param application for the current activity
 */
class BoardViewModel(application: Application) : AndroidViewModel(application) {
    private val fieldsObservable: LiveData<Array<Array<Piece?>>>

    init {
        fieldsObservable = MutableLiveData()
        fieldsObservable.value = BoardGame.getFields()
    }

    /**
     * @return the live data array for the chess board
     */
    fun getFieldObservable() = fieldsObservable
}