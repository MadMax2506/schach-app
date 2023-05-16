package janorschke.meyer.view.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import janorschke.meyer.R
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.GameStatus
import janorschke.meyer.enums.TransferKeys
import janorschke.meyer.view.adapter.BoardAdapter
import janorschke.meyer.view.adapter.MoveHistoryAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPieceDecorator
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesAdapter
import janorschke.meyer.view.adapter.beatenPieces.BeatenPiecesLayoutManager
import janorschke.meyer.viewModel.GameViewModel

private const val LOG_TAG = "GameActivity"

/**
 * Activity for an chess game
 */
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var moveHistoryAdapter: MoveHistoryAdapter
    private lateinit var beatenPiecesByWhiteAdapter: BeatenPiecesAdapter
    private lateinit var beatenPiecesByBlackAdapter: BeatenPiecesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View Model
        val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Game Mode
        intent.extras?.getString(TransferKeys.GAME_MODE.toString()).let { gameModeStr ->
            if (gameModeStr == null) throw IllegalArgumentException("Wrong game mode")

            enumValueOf<GameMode>(gameModeStr).apply {
                when {
                    this == GameMode.AI -> aiGameMode()
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

        // Beaten Pieces By White
        beatenPiecesByWhiteAdapter = BeatenPiecesAdapter(applicationContext)
        beatenPiecesAdapter(binding.playerTwo?.beatenPieces, beatenPiecesByWhiteAdapter)

        // Beaten Pieces By Black
        beatenPiecesByBlackAdapter = BeatenPiecesAdapter(applicationContext)
        beatenPiecesAdapter(binding.playerOne?.beatenPieces, beatenPiecesByBlackAdapter)

        // Observer
        // IMPORTANT: It needs to be after all adapter initializations
        observeViewModel(gameViewModel)
    }

    /**
     * Initialize the game with an ai level
     *
     * @return a pair of the two players
     */
    private fun aiGameMode() {
        intent.extras?.getString(TransferKeys.AI_LEVEL.toString()).let { aiLevelStr ->
            if (aiLevelStr == null) throw IllegalArgumentException("Wrong ai level")

            enumValueOf<AiLevel>(aiLevelStr).apply {
                binding.playerOne?.name?.text = resources.getString(this.resourceId)
                binding.playerTwo?.name?.text = resources.getString(R.string.default_player_name)
            }
        }
    }

    /**
     * Add the adapter and all related decorator, layout manager to the view
     *
     * @param binding of the related view
     * @param color of the own pieces
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
     * Observer for the view models
     *
     * @param viewModel of the activity
     */
    private fun observeViewModel(viewModel: GameViewModel) {
        viewModel.status.observe(this) { status ->
            if (status == GameStatus.CHECKMATE) {
                Log.d(LOG_TAG, "Checkmate")
                // TODO Show Checkmate
            } else if (status == GameStatus.STALEMATE) {
                Log.d(LOG_TAG, "Stalemate")
                // TODO Show Stalemate
            }
        }

        viewModel.playerColor.observe(this) { playerColor ->
            Log.d(LOG_TAG, "Update player color")
            boardAdapter.setPlayerColor(playerColor)
        }

        viewModel.selectedPosition.observe(this) { selectedPosition ->
            Log.d(LOG_TAG, "Update selected positions")
            boardAdapter.setSelectedPosition(selectedPosition)
        }

        viewModel.possibleMoves.observe(this) { moves ->
            Log.d(LOG_TAG, "Update possible moves")
            boardAdapter.setPossibleMoves(moves)
        }

        viewModel.fields.observe(this) { fields ->
            Log.d(LOG_TAG, "Update fields")
            boardAdapter.setFields(fields)
        }

        viewModel.moves.observe(this) { moveHistory ->
            Log.d(LOG_TAG, "Update move history")
            moveHistoryAdapter.setMoveHistory(moveHistory)
        }

        viewModel.beatenPiecesByWhite.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by white")
            beatenPiecesByWhiteAdapter.setBeatenPieces(beatenPieces)
        }

        viewModel.beatenPiecesByBlack.observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces by black")
            beatenPiecesByBlackAdapter.setBeatenPieces(beatenPieces)
        }

        viewModel.pawnDifference.observe(this) { pawnDifference ->
            Log.d(LOG_TAG, "Update pawn difference")
            // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/70
        }
    }
}