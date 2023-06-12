package janorschke.meyer.view.listener

import android.app.Activity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.viewModel.GameViewModel

class GameVoteDrawOnClickListener(
        private val activity: Activity,
        private val gameViewModel: GameViewModel
) : OnClickListener {
    override fun onClick(v: View?) = showConfirmationDialog()

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(activity, R.style.MyTheme_Dialog)
                .setTitle(R.string.dialog_draw_title)
                .setMessage(R.string.dialog_draw_message)
                .setPositiveButton(R.string.yes) { _, _ ->
                    val drawAccepted = gameViewModel.voteDraw()
                    if (!drawAccepted) Toast.makeText(
                            activity.applicationContext,
                            activity.getString(R.string.draw_declined),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
                .show()
    }
}