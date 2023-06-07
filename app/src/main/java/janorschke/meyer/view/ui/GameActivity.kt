package janorschke.meyer.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
import janorschke.meyer.service.model.game.player.Player
import janorschke.meyer.service.utils.SettingsManager
import janorschke.meyer.view.adapter.BoardAdapter
import janorschke.meyer.view.adapter.MoveHistoryAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPieceDecorator
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesLayoutManager
import janorschke.meyer.view.dialog.GameOverDialog
import janorschke.meyer.view.listener.GameSurrenderOnClickListener
import janorschke.meyer.view.listener.GameVoteDrawOnClickListener
import janorschke.meyer.viewModel.GameViewModel
import janorschke.meyer.viewModel.GameViewModelFactory

private const val LOG_TAG = "GameActivity"
private const val GAME_OVER_DIALOG_TAG = "GameOverDialog"

/**
 * Activity for a chess game
 */
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var playerInfoWhite: PlayerInfoBinding
    private lateinit var playerInfoBlack: PlayerInfoBinding
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var moveHistoryAdapter: MoveHistoryAdapter
    private lateinit var beatenPiecesByWhiteAdapter: BeatenPiecesAdapter
    private lateinit var beatenPiecesByBlackAdapter: BeatenPiecesAdapter
    private lateinit var gameViewModel: GameViewModel
    private lateinit var timeMode: TimeMode
    private var countdownTimer: CountDownTimer? = null
    private var remainingTime: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Player binding
        playerInfoWhite = binding.playerTwo!!
        playerInfoBlack = binding.playerOne!!

        // Game Mode
        intent.extras?.getString(TransferKeys.GAME_MODE.name).let { gameModeStr ->
            if (gameModeStr == null) throw IllegalArgumentException("Wrong game mode")

            enumValueOf<GameMode>(gameModeStr).let { gameMode ->
                when {
                    gameMode == GameMode.AI -> {
                        aiGameMode()

                        // Time Mode
                        setTimeMode()
                    }
                    // TODO further modes
                }
            }
        }

        // Board
        boardAdapter = BoardAdapter(applicationContext, gameViewModel)
        binding.boardWrapper?.board?.adapter = boardAdapter

        // Move History
        moveHistoryAdapter = MoveHistoryAdapter(applicationContext)
        binding.moveHistoryWrapper?.moveHistory?.adapter = moveHistoryAdapter

        // Bottom Bar
        setBottomBarListener()

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
     * Sets the on click listener for the buttons on the bottom bar
     */
    private fun setBottomBarListener() {
        val layoutVoteDraw = binding.bottomBar?.layoutVoteDraw
        val layoutSurrender = binding.bottomBar?.layoutSurrender
        layoutVoteDraw?.findViewById<LinearLayout>(R.id.layout_vote_draw)?.setOnClickListener(GameVoteDrawOnClickListener(this, gameViewModel))
        layoutSurrender?.findViewById<LinearLayout>(R.id.layout_surrender)?.setOnClickListener(GameSurrenderOnClickListener(this, gameViewModel))
    }

    private fun setTimeMode() {
        val timeModeStr = intent.extras?.getString(TransferKeys.TIME_MODE.name)
                ?: throw IllegalArgumentException("Time Mode null!")
        timeMode = enumValueOf(timeModeStr)

        // TimeMode off for AI-Player
        binding.playerOne!!.time.visibility = View.GONE

        if (timeMode != TimeMode.UNLIMITED) {
            remainingTime = timeMode.time
        } else {
            binding.playerTwo!!.time.visibility = View.GONE
            countdownTimer = null
        }
    }

    private fun setCountdownTimer() {
        countdownTimer = object : CountDownTimer(remainingTime!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.playerTwo!!.time.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                remainingTime = 0
                gameViewModel.gameTimeOver()
            }
        }.start()
    }

    /**
     * Initialize the game with an ai level
     *
     * @return a pair of the two players
     */
    private fun aiGameMode() {
        intent.extras?.getString(TransferKeys.AI_LEVEL.name).let { aiLevelStr ->
            if (aiLevelStr == null) throw IllegalArgumentException("Wrong ai level")

            enumValueOf<AiLevel>(aiLevelStr).let {
                val playerNameWhite = SettingsManager.loadSettings(applicationContext, SettingKeys.SETTINGS_SAVED_PLAYER_NAME.name)
                        ?.takeUnless(String::isEmpty)
                        ?: getString(R.string.default_player_name)

                val playerNameBlack = getString(it.resourceId)

                // ViewModel
                gameViewModel = ViewModelProvider(
                        this,
                        GameViewModelFactory(application, playerNameWhite, playerNameBlack, null, it)
                )[GameViewModel::class.java]

                playerInfoWhite.name.text = playerNameWhite
                playerInfoBlack.name.text = playerNameBlack
            }
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
    private fun showGameOverDialogAndStopTimer(
            winningColor: PieceColor? = null,
            playerWhite: Player,
            playerBlack: Player,
            endByVote: Boolean = false
    ) {
        countdownTimer?.cancel()
        val endByTimeOver = (timeMode != TimeMode.UNLIMITED) && (remainingTime!! == 0L)
        GameOverDialog.newInstance(winningColor, playerWhite, playerBlack, endByVote, timeMode, endByTimeOver).show(supportFragmentManager, GAME_OVER_DIALOG_TAG)
    }

    /**
     * @param binding of the player info
     * @param value of the difference
     */
    @SuppressLint("SetTextI18n")
    private fun setPawnDifference(binding: PlayerInfoBinding, value: Int) {
        when {
            value > 0 -> binding.pawnDifference.text = "+$value"
            value < 0 -> binding.pawnDifference.text = "$value"
            else -> binding.pawnDifference.text = "0"
        }
    }

    /**
     * Observer for the view models
     */
    private fun observeViewModel() {
        gameViewModel.status.observe(this) { status ->
            when (status) {
                GameStatus.CHECKMATE -> {
                    Log.d(LOG_TAG, "Checkmate")
                    showGameOverDialogAndStopTimer(
                            winningColor = gameViewModel.activePlayer.value?.color,
                            playerWhite = gameViewModel.playerWhite.value!!,
                            playerBlack = gameViewModel.playerBlack.value!!
                    )
                }

                GameStatus.STALEMATE -> {
                    Log.d(LOG_TAG, "Stalemate")
                    showGameOverDialogAndStopTimer(
                            playerWhite = gameViewModel.playerWhite.value!!,
                            playerBlack = gameViewModel.playerBlack.value!!
                    )
                }

                GameStatus.DRAW -> {
                    Log.d(LOG_TAG, "Draw voted")
                    showGameOverDialogAndStopTimer(
                            playerWhite = gameViewModel.playerWhite.value!!,
                            playerBlack = gameViewModel.playerBlack.value!!,
                            endByVote = true
                    )
                }

                GameStatus.SURRENDERED -> {
                    Log.d(LOG_TAG, "Surrendered")
                    showGameOverDialogAndStopTimer(
                            winningColor = gameViewModel.activePlayer.value?.color?.opponent(),
                            playerWhite = gameViewModel.playerWhite.value!!,
                            playerBlack = gameViewModel.playerBlack.value!!,
                            endByVote = true
                    )
                }

                GameStatus.TIME_OVER -> {
                    Log.d(LOG_TAG, "TimeOver")
                    showGameOverDialogAndStopTimer(gameViewModel.activePlayer.value?.color?.opponent(), gameViewModel.playerWhite.value!!, gameViewModel.playerBlack.value!!)
                }

                GameStatus.RUNNING -> {}

                else -> throw IllegalArgumentException("Invalid status")
            }
        }

        gameViewModel.activePlayer.observe(this) { activePlayer ->
            if (activePlayer.color == PieceColor.BLACK) {
                playerInfoWhite.active.setImageResource(R.drawable.inactive)
                playerInfoBlack.active.setImageResource(R.drawable.active)

                if (timeMode != TimeMode.UNLIMITED) countdownTimer!!.cancel()
            } else {
                playerInfoWhite.active.setImageResource(R.drawable.active)
                playerInfoBlack.active.setImageResource(R.drawable.inactive)

                if (timeMode != TimeMode.UNLIMITED) setCountdownTimer()
            }

            Log.d(LOG_TAG, "Update activePlayer")
            boardAdapter.setPlayerColor(activePlayer.color)
        }

        gameViewModel.selectedPosition.observe(this) { selectedPosition ->
            Log.d(LOG_TAG, "Update selected positions")
            boardAdapter.setSelectedPosition(selectedPosition)
        }

        gameViewModel.possibleMoves.observe(this) { moves ->
            Log.d(LOG_TAG, "Update possible moves")
            boardAdapter.setPossibleMoves(moves)
        }

        gameViewModel.fields.observe(this) { fields ->
            Log.d(LOG_TAG, "Update fields")
            boardAdapter.setFields(fields)
        }

        gameViewModel.moves.observe(this) { moveHistory ->
            Log.d(LOG_TAG, "Update move history")
            moveHistoryAdapter.setMoveHistory(moveHistory)
        }

        gameViewModel.beatenPiecesByWhite.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by white")
            beatenPiecesByWhiteAdapter.setBeatenPieces(beatenPieces)
        }

        gameViewModel.beatenPiecesByBlack.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by black")
            beatenPiecesByBlackAdapter.setBeatenPieces(beatenPieces)
        }

        gameViewModel.pawnDifferenceWhite.observe(this) { pawnDifference ->
            Log.d(LOG_TAG, "Update pawn difference white")
            setPawnDifference(playerInfoWhite, pawnDifference)
        }

        gameViewModel.pawnDifferenceBlack.observe(this) { pawnDifference ->
            Log.d(LOG_TAG, "Update pawn difference black")
            setPawnDifference(playerInfoBlack, pawnDifference)
        }
    }
}