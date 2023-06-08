package janorschke.meyer.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import janorschke.meyer.enums.AiLevel

class GameViewModelFactory(
        private val application: Application,
        private val playerNameWhite: String,
        private val playerNameBlack: String,
        private val aiLevelWhite: AiLevel?,
        private val aiLevelBlack: AiLevel?,
        private val time: Long?
) : ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GameViewModel(application, playerNameWhite, playerNameBlack, aiLevelWhite, aiLevelBlack, time) as T
    }
}