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
import janorschke.meyer.enums.TimeMode
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
        private const val ARG_TIME_MODE = "timeMode"
        private const val ARG_END_BY_VOTE = "endByVote"
        private const val ARG_END_BY_TIMEOUT = "endByTimeOut"

        fun newInstance(
                winningColor: PieceColor?,
                playerWhite: Player,
                playerBlack: Player,
                endByVote: Boolean,
                timeMode: TimeMode,
                endByTimeOut: Boolean
        ): GameOverDialog {
            return GameOverDialog().also { dialog ->
                dialog.arguments = Bundle().also { bundle ->
                    bundle.putSerializable(ARG_WINNING_COLOR, winningColor)
                    bundle.putSerializable(ARG_PLAYER_WHITE, playerWhite)
                    bundle.putSerializable(ARG_PLAYER_BLACK, playerBlack)
                    bundle.putSerializable(ARG_END_BY_VOTE, endByVote)
                    bundle.putSerializable(ARG_TIME_MODE, timeMode)
                    bundle.putSerializable(ARG_END_BY_TIMEOUT, endByTimeOut)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGameoverBinding.inflate(layoutInflater)

        val winningColor: PieceColor? = requireArguments().optionalSerializable(ARG_WINNING_COLOR)
        val playerWhite: Player = requireArguments().requiredSerializable(ARG_PLAYER_WHITE)
        val playerBlack: Player = requireArguments().requiredSerializable(ARG_PLAYER_BLACK)
        val endByVote: Boolean = requireArguments().requiredSerializable(ARG_END_BY_VOTE)
        val timeMode: TimeMode = requireArguments().requiredSerializable(ARG_TIME_MODE)
        val endByTimeOut: Boolean = requireArguments().requiredSerializable(ARG_END_BY_TIMEOUT)

        aiLevel = if (playerWhite is AiPlayer) playerWhite.aiLevel else (playerBlack as AiPlayer).aiLevel

        binding.textGameOverDialog.text = getDialogText(winningColor, playerWhite, playerBlack, endByVote, endByTimeOut)

        setButtonOnClickHandlers(timeMode)

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    private fun getDialogText(
            winningColor: PieceColor?,
            playerWhite: Player,
            playerBlack: Player,
            endByVote: Boolean,
            endByTimeOut: Boolean
    ): String {
        return when {
            endByVote && winningColor == null -> resources.getString(R.string.gameover_dialog_text_draw_voted)

            endByVote && winningColor != null -> {
                resources.getString(
                        R.string.gameover_dialog_text_surrender,
                        if (winningColor == PieceColor.WHITE) playerWhite.name else playerBlack.name
                )
            }

            winningColor == null -> resources.getString(R.string.gameover_dialog_text_stalemate)

            endByTimeOut -> {
                return resources.getString(
                        R.string.gameover_dialog_text_timeout,
                        if (winningColor == PieceColor.WHITE) playerWhite.name else playerBlack.name
                )
            }

            else -> {
                resources.getString(
                        R.string.gameover_dialog_text_win,
                        if (winningColor == PieceColor.WHITE) playerWhite.name else playerBlack.name
                )
            }
        }
    }

    private fun setButtonOnClickHandlers(timeMode: TimeMode) {
        binding.buttonNewGame.setOnClickListener {
            Intent(requireContext(), GameActivity::class.java).let { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtras(Bundle().also { bundle ->
                    Log.d(LOG_TAG, "Start new game with ai-level=$aiLevel and time-mode=$timeMode")

                    bundle.putString(TransferKeys.TIME_MODE.name, timeMode.name)

                    if (aiLevel != null) {
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
