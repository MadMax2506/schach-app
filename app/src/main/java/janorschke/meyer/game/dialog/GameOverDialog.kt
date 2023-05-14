package janorschke.meyer.game.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.ai.AiActivtity
import janorschke.meyer.ai.AiLevel
import janorschke.meyer.databinding.DialogGameoverBinding
import janorschke.meyer.game.GameActivity
import janorschke.meyer.game.piece.PieceColor
import janorschke.meyer.global.TransferKeys
import janorschke.meyer.home.MainActivity


private const val KEY_WINNING_PLAYER = "WinningPlayer"
private const val KEY_AI_LEVEL = "AiLevel"
private const val LOG_TAG = "GameOverDialog"

class GameOverDialog(private var winningPlayer: PieceColor? = null) : DialogFragment() {

    private lateinit var binding: DialogGameoverBinding
    private lateinit var aiLevel: AiLevel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            winningPlayer = it.getSerializable(KEY_WINNING_PLAYER) as PieceColor?
            aiLevel = it.getSerializable(KEY_AI_LEVEL) as AiLevel
        } ?: run {
            aiLevel = enumValueOf(arguments?.getString(TransferKeys.AI_LEVEL.value)!!)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGameoverBinding.inflate(layoutInflater)
        binding.textGameOverDialog?.text =
                if (winningPlayer != null) resources.getString(R.string.gameover_dialog_text_win, winningPlayer)
                else resources.getString(R.string.gameover_dialog_text_stalemate)

        setButtonOnClickHandlers()
        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_WINNING_PLAYER, winningPlayer)
        outState.putSerializable(KEY_AI_LEVEL, aiLevel)
    }

    private fun setButtonOnClickHandlers() {
        binding.buttonNewGame?.setOnClickListener {
            Log.d(LOG_TAG, "Start new game with ai-level=$aiLevel")
            Intent(requireContext(), GameActivity::class.java).apply {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                aiLevel.let {
                    this.putExtras(Bundle().apply {
                        this.putString(TransferKeys.AI_LEVEL.value, aiLevel.toString())
                    })
                }
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
            Intent(requireContext(), AiActivtity::class.java).apply {
                Log.d(LOG_TAG, "Going back to AiActivity")
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }
}
