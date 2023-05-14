package janorschke.meyer.viewModel.beatenPieces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.History
import janorschke.meyer.service.model.piece.Piece

/**
 * View model for the beaten pieces
 *
 * @param application for the current activity
 * @param color of the own pieces
 */
class BeatenPiecesViewModel(application: Application, private val color: PieceColor) : AndroidViewModel(application) {
    private val beatenPiecesObservable: MutableLiveData<MutableList<Piece>> = MutableLiveData()
    private val pawnDifferentOpponent: MutableLiveData<Int> = MutableLiveData()

    init {
        beatenPiecesObservable.value = History.getBeatenPiecesByColor(color)
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