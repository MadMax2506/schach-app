package janorschke.meyer.view.listener

import android.app.Activity
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.viewModel.GameViewModel

class GameSurrenderOnClickListener(private val activity: Activity,
                                   private val gameViewModel: GameViewModel) : OnClickListener {
    override fun onClick(v: View?) {
        showConfirmationDialog()
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(activity, R.style.MyTheme_Dialog)
                .setTitle(R.string.dialog_surrender_title)
                .setMessage(R.string.dialog_surrender_message)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    gameViewModel.surrenderGame()
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }
}
