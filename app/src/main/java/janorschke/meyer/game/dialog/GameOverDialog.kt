package janorschke.meyer.game.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.databinding.DialogGameoverBinding
import janorschke.meyer.game.piece.PieceColor

class GameOverDialog(private var winningPlayer: PieceColor?) : DialogFragment() {

    private lateinit var binding: DialogGameoverBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGameoverBinding.inflate(layoutInflater)

        binding.textGameOverDialog.text =
                if (winningPlayer != null) resources.getString(R.string.gameover_dialog_text_win, winningPlayer)
                else resources.getString(R.string.gameover_dialog_text_stalemate)

        setButtonOnClickHandlers()

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun setButtonOnClickHandlers() {
        binding.buttonNewGame.setOnClickListener {
            dismiss()
        }

        binding.buttonBackToMenu.setOnClickListener {
            dismiss()
        }

        binding.buttonChangeDifficulty.setOnClickListener {
            dismiss()
        }

        binding.buttonShowBoard.setOnClickListener {
            dismiss()
        }
    }
}
