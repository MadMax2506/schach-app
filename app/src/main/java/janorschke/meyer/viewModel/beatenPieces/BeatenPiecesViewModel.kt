package janorschke.meyer.viewModel.beatenPieces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.service.model.piece.PieceColor

/**
 * View model for the beaten pieces
 *
 * @param application for the current activity
 * @param color of the own pieces
 */
class BeatenPiecesViewModel(application: Application, private val color: PieceColor) : AndroidViewModel(application) {
    private val beatenPiecesObservable: LiveData<MutableList<Piece>>
    private val pawnDifferentOpponent: LiveData<Int>

    init {
        beatenPiecesObservable = MutableLiveData()
        beatenPiecesObservable.value = janorschke.meyer.service.model.History.getBeatenPiecesByColor(color)

        pawnDifferentOpponent = MutableLiveData()
        pawnDifferentOpponent.value = 0 // TODO
    }

    /**
     * @return the live data list for the beaten pieces by white
     */
    fun getBeatenPieces() = beatenPiecesObservable

    /**
     * @return the live data list for the beaten pieces by white
     */
    fun getPawnDifferent() = pawnDifferentOpponent
}