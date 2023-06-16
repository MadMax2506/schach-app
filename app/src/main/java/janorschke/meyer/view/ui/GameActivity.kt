package janorschke.meyer.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import janorschke.meyer.R
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.databinding.PlayerInfoBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.enums.SettingKeys
import janorschke.meyer.enums.TimeMode
import janorschke.meyer.enums.TransferKeys
import janorschke.meyer.service.model.game.board.move.PossibleMove
import janorschke.meyer.service.model.game.player.AiPlayer
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.service.utils.SettingsManager
import janorschke.meyer.view.adapter.BoardAdapter
import janorschke.meyer.view.adapter.MoveHistoryAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPieceDecorator
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesLayoutManager
import janorschke.meyer.view.callback.BoardRepositoryCallback
import janorschke.meyer.view.dialog.GameOverDialog
import janorschke.meyer.view.dialog.PromotionDialog
import janorschke.meyer.view.listener.GameSurrenderOnClickListener
import janorschke.meyer.view.listener.GameVoteDrawOnClickListener
import janorschke.meyer.viewModel.GameViewModel
import janorschke.meyer.viewModel.GameViewModelFactory

private const val LOG_TAG = "GameActivity"
private const val GAME_OVER_DIALOG_TAG = "GameOverDialog"
private const val PROMOTION_DIALOG_TAG = "PromotionDialog"

/**
 * Activity for a chess game
 */
class GameActivity : AppCompatActivity(), BoardRepositoryCallback {
    private lateinit var binding: ActivityGameBinding
    private lateinit var playerInfoWhite: PlayerInfoBinding
    private lateinit var playerInfoBlack: PlayerInfoBinding
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var moveHistoryAdapter: MoveHistoryAdapter
    private lateinit var beatenPiecesByWhiteAdapter: BeatenPiecesAdapter
    private lateinit var beatenPiecesByBlackAdapter: BeatenPiecesAdapter
    private lateinit var timeMode: TimeMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Player binding
        playerInfoWhite = binding.playerTwo!!
        playerInfoBlack = binding.playerOne!!

        // Time
        intent.extras?.getString(TransferKeys.TIME_MODE.name).let { timeMode = initTimeMode(it) }

        // Game Mode
        intent.extras?.getString(TransferKeys.GAME_MODE.name).let { initGameMode(it) }

        // Board
        boardAdapter = BoardAdapter(applicationContext)
        binding.boardWrapper?.board?.adapter = boardAdapter

        // Move History
        moveHistoryAdapter = MoveHistoryAdapter(applicationContext)
        binding.moveHistoryWrapper?.moveHistory?.adapter = moveHistoryAdapter

        // Bottom Bar
        binding.bottomBar
                ?.layoutVoteDraw
                ?.findViewById<LinearLayout>(R.id.layout_vote_draw)
                ?.setOnClickListener(GameVoteDrawOnClickListener(this, GameViewModel.getInstance()))

        binding.bottomBar
                ?.layoutSurrender
                ?.findViewById<LinearLayout>(R.id.layout_surrender)
                ?.setOnClickListener(GameSurrenderOnClickListener(this, GameViewModel.getInstance()))

        // Beaten Pieces By White
        beatenPiecesByWhiteAdapter = BeatenPiecesAdapter(applicationContext)
        beatenPiecesAdapter(playerInfoWhite.beatenPieces, beatenPiecesByWhiteAdapter)

        // Beaten Pieces By Black
        beatenPiecesByBlackAdapter = BeatenPiecesAdapter(applicationContext)
        beatenPiecesAdapter(playerInfoBlack.beatenPieces, beatenPiecesByBlackAdapter)

        // Observer
        // IMPORTANT: It needs to be after all adapter initializations
        observeViewModel()
    }

    /**
     * Validate and handle the time mode string from extras
     *
     * @param timeModeStr
     * @return the [TimeMode] of the game
     */
    private fun initTimeMode(timeModeStr: String?): TimeMode {
        if (timeModeStr == null) throw IllegalArgumentException("Wrong timer mode")

        val timeMode = enumValueOf<TimeMode>(timeModeStr)
        if (timeMode == TimeMode.UNLIMITED) {
            playerInfoWhite.time.visibility = View.GONE
            playerInfoBlack.time.visibility = View.GONE
        }
        return timeMode
    }

    /**
     * Validate and handle the game mode from extras
     *
     * @param gameModeStr
     */
    private fun initGameMode(gameModeStr: String?) {
        if (gameModeStr == null) throw IllegalArgumentException("Wrong game mode")

        enumValueOf<GameMode>(gameModeStr).let { gameMode ->
            when (gameMode) {
                GameMode.AI -> {
                    val aiLevelStr = intent.extras?.getString(TransferKeys.AI_LEVEL.name)
                            ?: throw IllegalArgumentException("Wrong ai level")
                    initAiGame(enumValueOf(aiLevelStr))
                }
            }
        }
    }

    /**
     * Handle the ai level for a new game
     *
     * Initialize the GameViewModel
     * - If you start a new game, the gameViewModel will be reseted,
     * otherwise you will get the instance that's already created
     *
     * @param aiLevel
     */
    private fun initAiGame(aiLevel: AiLevel) {
        val playerNameWhite = SettingsManager.loadSettings(applicationContext, SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name)
                ?.takeUnless(String::isEmpty)
                ?: getString(R.string.default_player_name)

        val playerNameBlack = getString(aiLevel.resourceId)

        // ViewModel
        if (intent.flags == Intent.FLAG_ACTIVITY_CLEAR_TOP) GameViewModel.reset()

        val gameViewModel = ViewModelProvider(
                this,
                GameViewModelFactory(application, playerNameWhite, playerNameBlack, null, aiLevel, timeMode)
        )[GameViewModel::class.java].let { GameViewModel.getInstance(it) }
        gameViewModel.setBoardRepositoryCallback(this)

        playerInfoWhite.name.text = playerNameWhite
        playerInfoBlack.name.text = playerNameBlack

        if (gameViewModel.playerWhite.value is AiPlayer) {
            playerInfoWhite.time.visibility = View.GONE
        } else if (gameViewModel.playerBlack.value is AiPlayer) {
            playerInfoBlack.time.visibility = View.GONE
        }
    }

    /**
     * Add the adapter and all related decorator, layout manager to the view
     *
     * @param binding of the related view
     * @param adapter for the beaten pieces
     */
    private fun beatenPiecesAdapter(binding: RecyclerView?, adapter: BeatenPiecesAdapter): BeatenPiecesAdapter {
        // Adapter and Layouts
        binding?.adapter = adapter
        binding?.layoutManager = BeatenPiecesLayoutManager(this)

        binding?.addItemDecoration(BeatenPieceDecorator())
        binding?.setHasFixedSize(true)

        return adapter
    }

    /**
     * Shows the GameOverDialog to display the game result.
     *
     * @param winningColor The color of the winning player. If the game is a Stalemate, the winningColor is null.
     * @param playerWhite
     * @param playerBlack
     * @see GameOverDialog.onCreateDialog
     */
    private fun showGameOverDialog(
            winningColor: PieceColor? = null,
            playerWhite: Player,
            playerBlack: Player,
            endByVote: Boolean = false
    ) {
        GameViewModel.getInstance().stopCountdownTimer()
        val endByTimeOver = (timeMode != TimeMode.UNLIMITED) && (GameViewModel.getInstance().activePlayerTime.value == 0L)
        GameOverDialog.newInstance(winningColor, playerWhite, playerBlack, endByVote, timeMode, endByTimeOver)
                .show(supportFragmentManager, GAME_OVER_DIALOG_TAG)
    }

    /**
     * @param binding of the player info
     * @param value of the difference
     */
    @SuppressLint("SetTextI18n")
    fun setPawnDifference(binding: PlayerInfoBinding, value: Int) {
        when {
            value > 0 -> binding.pawnDifference.text = "+$value"
            value < 0 -> binding.pawnDifference.text = "$value"
            else -> binding.pawnDifference.text = "0"
        }
    }

    /**
     * PromotionDialog is created and shown in this method
     *
     * @param pieceColor of the player who moves
     * @param possibleMove the move to be made
     */
    override fun openPromotionDialog(
            pieceColor: PieceColor,
            possibleMove: PossibleMove
    ) {
        val promotionDialog = PromotionDialog.newInstance(pieceColor, possibleMove)
        promotionDialog.show(supportFragmentManager, PROMOTION_DIALOG_TAG)
    }

    /**
     * Observer for the view models
     */
    private fun observeViewModel() {
        GameViewModel.getInstance().status.observe(this) { status ->
            when (status) {
                GameStatus.CHECKMATE -> {
                    Log.d(LOG_TAG, "Checkmate")
                    showGameOverDialog(
                            winningColor = GameViewModel.getInstance().activePlayerColor.value,
                            playerWhite = GameViewModel.getInstance().playerWhite.value!!,
                            playerBlack = GameViewModel.getInstance().playerBlack.value!!
                    )
                }

                GameStatus.STALEMATE -> {
                    Log.d(LOG_TAG, "Stalemate")
                    showGameOverDialog(
                            playerWhite = GameViewModel.getInstance().playerWhite.value!!,
                            playerBlack = GameViewModel.getInstance().playerBlack.value!!
                    )
                }

                GameStatus.DRAW -> {
                    Log.d(LOG_TAG, "Draw voted")
                    showGameOverDialog(
                            playerWhite = GameViewModel.getInstance().playerWhite.value!!,
                            playerBlack = GameViewModel.getInstance().playerBlack.value!!,
                            endByVote = true
                    )
                }

                GameStatus.SURRENDERED -> {
                    Log.d(LOG_TAG, "Surrendered")
                    showGameOverDialog(
                            winningColor = GameViewModel.getInstance().activePlayerColor.value?.opponent(),
                            playerWhite = GameViewModel.getInstance().playerWhite.value!!,
                            playerBlack = GameViewModel.getInstance().playerBlack.value!!,
                            endByVote = true
                    )
                }

                GameStatus.TIME_OVER -> {
                    Log.d(LOG_TAG, "TimeOver")
                    showGameOverDialog(
                            winningColor = GameViewModel.getInstance().activePlayerColor.value?.opponent(),
                            playerWhite = GameViewModel.getInstance().playerWhite.value!!,
                            playerBlack = GameViewModel.getInstance().playerBlack.value!!
                    )
                }

                GameStatus.RUNNING -> {}

                else -> throw IllegalArgumentException("Invalid status")
            }
        }

        GameViewModel.getInstance().activePlayerColor.observe(this) { color ->
            Log.d(LOG_TAG, "Update activePlayer")

            val isBlackPlayer = (color == PieceColor.BLACK)

            val activePlayerInfo = if (isBlackPlayer) playerInfoBlack else playerInfoWhite
            val inactivePlayerInfo = if (isBlackPlayer) playerInfoWhite else playerInfoBlack

            val layoutVoteDraw = binding.bottomBar?.layoutVoteDraw
            val layoutSurrender = binding.bottomBar?.layoutSurrender

            val activePlayer = if (isBlackPlayer) GameViewModel.getInstance().playerBlack.value else GameViewModel.getInstance().playerWhite.value
            val isNotAiPlayer = activePlayer !is AiPlayer

            val alphaValue = if (isNotAiPlayer) 1.0f else 0.7f

            activePlayerInfo.active.setImageResource(R.drawable.active)
            activePlayerInfo.time.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))

            inactivePlayerInfo.active.setImageResource(R.drawable.inactive)
            inactivePlayerInfo.time.setTextColor(ContextCompat.getColor(applicationContext, R.color.dark_gray))

            layoutSurrender?.isEnabled = isNotAiPlayer
            layoutVoteDraw?.isEnabled = isNotAiPlayer

            layoutSurrender?.alpha = alphaValue
            layoutVoteDraw?.alpha = alphaValue
        }

        if (timeMode != TimeMode.UNLIMITED) {
            GameViewModel.getInstance().activePlayerTime.observe(this) { time ->
                if (time == null) return@observe

                Log.d(LOG_TAG, "Update activePlayer time")

                val seconds = time / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60

                if (GameViewModel.getInstance().activePlayerColor.value == PieceColor.BLACK) {
                    playerInfoBlack.time.text = String.format("%02d:%02d", minutes, remainingSeconds)
                } else {
                    playerInfoWhite.time.text = String.format("%02d:%02d", minutes, remainingSeconds)
                }
            }
        }

        GameViewModel.getInstance().possibleMoves.observe(this) { moves ->
            Log.d(LOG_TAG, "Update possible moves")
            boardAdapter.setPossibleMoves(moves)
        }

        GameViewModel.getInstance().fields.observe(this) { fields ->
            Log.d(LOG_TAG, "Update fields")
            boardAdapter.setFields(fields)
        }

        GameViewModel.getInstance().moves.observe(this) { moveHistory ->
            Log.d(LOG_TAG, "Update move history")
            moveHistoryAdapter.setMoveHistory(moveHistory)
        }

        GameViewModel.getInstance().beatenPiecesByWhite.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by white")
            beatenPiecesByWhiteAdapter.setBeatenPieces(beatenPieces)
        }

        GameViewModel.getInstance().beatenPiecesByBlack.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by black")
            beatenPiecesByBlackAdapter.setBeatenPieces(beatenPieces)
        }

        GameViewModel.getInstance().pawnDifferenceWhite.observe(this) { pawnDifference ->
            Log.d(LOG_TAG, "Update pawn difference white")
            setPawnDifference(playerInfoWhite, pawnDifference)
        }

        GameViewModel.getInstance().pawnDifferenceBlack.observe(this) { pawnDifference ->
            Log.d(LOG_TAG, "Update pawn difference black")
            setPawnDifference(playerInfoBlack, pawnDifference)
        }
    }
}