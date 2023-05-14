package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.board.BoardGame
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.service.repository.BoardRepository
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * View model of the board
 *
 * @param application for the current activity
 */
class BoardViewModel(application: Application) : AndroidViewModel(application) {
    private val fieldsObservable: MutableLiveData<Array<Array<Piece?>>> = MutableLiveData()

    init {
        fieldsObservable.value = BoardGame.getFields()
    }

    /**
     * @see BoardRepository.tryToMovePiece
     */
    fun tryToMovePiece(fromPosition: PiecePosition, toPosition: PiecePosition) {
        BoardRepository.tryToMovePiece(fromPosition, toPosition)
    }

    /**
     * @return the live data array for the chess board
     */
    fun getFieldObservable() = fieldsObservable
}