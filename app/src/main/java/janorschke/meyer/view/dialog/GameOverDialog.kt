package janorschke.meyer.view.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import janorschke.meyer.R
import janorschke.meyer.databinding.DialogGameoverBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.TransferKeys
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.view.ui.AiActivity
import janorschke.meyer.view.ui.GameActivity
import janorschke.meyer.view.ui.MainActivity

private const val LOG_TAG = "GameOverDialog"

class GameOverDialog : BaseDialog() {
    private lateinit var binding: DialogGameoverBinding
    private var aiLevel: AiLevel? = null

    companion object {
        private const val ARG_WINNING_COLOR = "winningColor"
        private const val ARG_PLAYER_WHITE = "playerWhite"
        private const val ARG_PLAYER_BLACK = "playerBlack"

        fun newInstance(winningColor: PieceColor?, playerWhite: Player, playerBlack: Player): GameOverDialog {
            return GameOverDialog().also { dialog ->
                dialog.arguments = Bundle().also { bundle ->
                    bundle.putSerializable(ARG_WINNING_COLOR, winningColor)
                    bundle.putSerializable(ARG_PLAYER_WHITE, playerWhite)
                    bundle.putSerializable(ARG_PLAYER_BLACK, playerBlack)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGameoverBinding.inflate(layoutInflater)

        val winningColor: PieceColor? = requireArguments().optionalSerializable(ARG_WINNING_COLOR)
        val playerWhite: Player = requireArguments().requiredSerializable(ARG_PLAYER_WHITE)
        val playerBlack: Player = requireArguments().requiredSerializable(ARG_PLAYER_BLACK)

        aiLevel = if (playerWhite is AiPlayer) playerWhite.aiLevel else (playerBlack as AiPlayer).aiLevel

        binding.textGameOverDialog.text = getDialogText(winningColor, playerWhite, playerBlack)

        setButtonOnClickHandlers()

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun getDialogText(winningColor: PieceColor?, playerWhite: Player, playerBlack: Player): String {
        if (winningColor == null) return resources.getString(R.string.gameover_dialog_text_stalemate)

        return resources.getString(
                R.string.gameover_dialog_text_win,
                resources.getString(
                        if (winningColor == PieceColor.WHITE) playerWhite.textResource
                        else playerBlack.textResource
                )
        )
    }

    private fun setButtonOnClickHandlers() {
        binding.buttonNewGame.setOnClickListener {
            Intent(requireContext(), GameActivity::class.java).let { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtras(Bundle().also { bundle ->
                    if (aiLevel != null) {
                        Log.d(LOG_TAG, "Start new game with ai-level=$aiLevel")

                        bundle.putString(TransferKeys.AI_LEVEL.name, aiLevel!!.name)
                        bundle.putString(TransferKeys.GAME_MODE.name, GameMode.AI.name)
                    }
                })
                startActivity(intent)
            }
        }

        binding.buttonBackToMenu.setOnClickListener {
            Intent(requireContext(), MainActivity::class.java).let { intent ->
                Log.d(LOG_TAG, "Going back to MainActivity")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

        binding.buttonChangeDifficulty.setOnClickListener {
            Intent(requireContext(), AiActivity::class.java).let { intent ->
                Log.d(LOG_TAG, "Going back to AiActivity")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}
