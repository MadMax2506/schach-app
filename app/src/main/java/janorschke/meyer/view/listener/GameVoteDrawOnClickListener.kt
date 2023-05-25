package janorschke.meyer.view.listener

import android.app.Activity
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.viewModel.GameViewModel

class GameVoteDrawOnClickListener(private val activity: Activity, private val gameViewModel: GameViewModel) : OnClickListener {
    override fun onClick(v: View?) {
        showConfirmationDialog()
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.dialog_draw_title)
                .setMessage(R.string.dialog_draw_message)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    gameViewModel.voteDraw()
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }
}