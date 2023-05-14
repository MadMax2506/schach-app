package janorschke.meyer.viewModel.beatenPieces

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import janorschke.meyer.service.model.piece.PieceColor

/**
 * Factory to generate a beaten pieces view model
 * @see BeatenPiecesViewModel
 */
class BeatenPiecesViewModelFactory(private val application: Application, private val color: PieceColor) : ViewModelProvider.AndroidViewModelFactory() {
    fun create(modelClass: Class<BeatenPiecesViewModel>?): BeatenPiecesViewModel = BeatenPiecesViewModel(application, color)
}