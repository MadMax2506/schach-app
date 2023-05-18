package janorschke.meyer.game.dialog

import android.R.attr.value
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.databinding.DialogGameoverBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TransferKeys
import janorschke.meyer.service.model.game.Player
import janorschke.meyer.view.ui.AiActivity
import janorschke.meyer.view.ui.GameActivity
import janorschke.meyer.view.ui.MainActivity
import java.io.Serializable


private const val LOG_TAG = "GameOverDialog"

class GameOverDialog : DialogFragment() {
    private lateinit var binding: DialogGameoverBinding
    private var aiLevel: AiLevel? = null

    companion object {
        private const val ARG_WINNINGCOLOR = "winningColor"
        private const val ARG_PLAYERWHITE = "playerWhite"
        private const val ARG_PLAYERBLACK = "playerBlack"

        fun newInstance(winningColor: PieceColor?, playerWhite: Player, playerBlack: Player): GameOverDialog {
            val dialog = GameOverDialog()
            val args = Bundle().apply {
                putSerializable(ARG_WINNINGCOLOR, winningColor)
                putSerializable(ARG_PLAYERWHITE, playerWhite)
                putSerializable(ARG_PLAYERBLACK, playerBlack)
            }
            dialog.arguments = args
            return dialog
        }
    }

    private inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGameoverBinding.inflate(layoutInflater)

        val winningColor = arguments?.serializable(ARG_WINNINGCOLOR) as PieceColor?
        val playerWhite = (arguments?.serializable(ARG_PLAYERWHITE) as Player?)!!
        val playerBlack = (arguments?.serializable(ARG_PLAYERBLACK) as Player?)!!

        aiLevel = playerWhite.aiLevel ?: playerBlack.aiLevel

        binding.textGameOverDialog?.text =
                if (winningColor != null)
                    resources.getString(R.string.gameover_dialog_text_win,
                            resources.getString(
                                    if (winningColor == PieceColor.WHITE) playerWhite.textResource
                                    else playerBlack.textResource))
                else resources.getString(R.string.gameover_dialog_text_stalemate)

        setButtonOnClickHandlers()
        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun setButtonOnClickHandlers() {
        binding.buttonNewGame?.setOnClickListener {
            Log.d(LOG_TAG, "Start new game with ai-level=$aiLevel")
            Intent(requireContext(), GameActivity::class.java).apply {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                this.putExtras(Bundle().apply {
                    if (aiLevel != null) {
                        this.putString(TransferKeys.AI_LEVEL.name, aiLevel!!.name)
                        this.putString(TransferKeys.GAME_MODE.name, GameMode.AI.name)
                    }
                })
                startActivity(this)
            }
        }

        binding.buttonBackToMenu?.setOnClickListener {
            Intent(requireContext(), MainActivity::class.java).apply {
                Log.d(LOG_TAG, "Going back to MainActivity")
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }

        binding.buttonChangeDifficulty?.setOnClickListener {
            Intent(requireContext(), AiActivity::class.java).apply {
                Log.d(LOG_TAG, "Going back to AiActivity")
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }
}
