package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import janorschke.meyer.service.model.SelectedPiece
import janorschke.meyer.service.utils.board.PiecePosition

/**
 * View model of the selected piece
 *
 * @param application for the current activity
 */
class SelectedPieceViewModel(application: Application) : AndroidViewModel(application) {
    private val selectedPositionObservable: MutableLiveData<PiecePosition?> = MutableLiveData()
    private val possibleMovesObservable: MutableLiveData<MutableList<PiecePosition>> = MutableLiveData()

    init {
        selectedPositionObservable.value = SelectedPiece.getSelectedPosition()
        possibleMovesObservable.value = SelectedPiece.getPossibleMoves()
    }

    /**
     * Sets the selected piece and shows the possible moves through the GameFieldAdapter.
     *
     * @param selectedPosition the position of the selected piece (optional: Default = null)
     * @param possibleMoves the possible moves for the selected piece (optional: Default = emptyList())
     *
     * @see SelectedPiece.selectedPosition
     */
    fun setSelectedPiece(selectedPosition: PiecePosition? = null, possibleMoves: MutableList<PiecePosition> = mutableListOf()) {
        SelectedPiece.setSelectedPiece(selectedPosition, possibleMoves)
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