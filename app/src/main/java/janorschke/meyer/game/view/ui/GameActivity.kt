package janorschke.meyer.game.view.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import janorschke.meyer.R
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.TransferKeys
import janorschke.meyer.game.service.model.game.GameStatus
import janorschke.meyer.game.service.model.piece.PieceColor
import janorschke.meyer.game.service.model.player.AiPlayer
import janorschke.meyer.game.service.model.player.Player
import janorschke.meyer.game.view.adapter.BoardAdapter
import janorschke.meyer.game.view.adapter.MoveHistoryAdapter
import janorschke.meyer.game.view.adapter.beatenPieces.BeatenPieceDecorator
import janorschke.meyer.game.view.adapter.beatenPieces.BeatenPiecesAdapter
import janorschke.meyer.game.view.adapter.beatenPieces.BeatenPiecesLayoutManager
import janorschke.meyer.game.viewModel.BoardViewModel
import janorschke.meyer.game.viewModel.GameViewModel
import janorschke.meyer.game.viewModel.MoveHistoryViewModel
import janorschke.meyer.game.viewModel.beatenPieces.BeatenPiecesViewModel

private const val LOG_TAG = "GameActivity"

/**
 * Activity for an chess game
 */
class GameActivity : AppCompatActivity() {
    private val boardAdapter = BoardAdapter(applicationContext)
    private val moveHistoryAdapter = MoveHistoryAdapter(applicationContext)
    private val beatenPiecesByWhiteAdapter = BeatenPiecesAdapter(applicationContext)
    private val beatenPiecesByBlackAdapter = BeatenPiecesAdapter(applicationContext)

    private lateinit var binding: ActivityGameBinding
    private lateinit var boardViewModel: BoardViewModel
    private lateinit var moveHistoryViewModel: MoveHistoryViewModel
    private lateinit var beatenPiecesByWhiteViewModel: BeatenPiecesViewModel
    private lateinit var beatenPiecesByBlackViewModel: BeatenPiecesViewModel
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Player
        var players: Pair<Player?, Player?> = Pair(null, null)
        intent.extras?.getString(TransferKeys.GAME_MODE.toString()).let { gameModeStr ->
            if (gameModeStr == null) return

            enumValueOf<GameMode>(gameModeStr).apply {
                players = when {
                    this == GameMode.AI -> aiGameMode()
                    else -> Pair(null, null)
                    // TODO further modes
                }
            }
        }
        // TODO Handle Players

        // Board
        binding.boardWrapper?.board?.adapter = boardAdapter
        boardViewModel = ViewModelProvider(this)[BoardViewModel::class.java]

        // Move History
        binding.moveHistoryWrapper?.moveHistory?.adapter = moveHistoryAdapter
        moveHistoryViewModel = ViewModelProvider(this)[MoveHistoryViewModel::class.java]

        // Beaten Pieces
        beatenPiecesAdapter(binding.playerTwo?.beatenPieces, beatenPiecesByWhiteAdapter)
        beatenPiecesByWhiteViewModel = BeatenPiecesViewModel(application, PieceColor.WHITE)

        beatenPiecesAdapter(binding.playerOne?.beatenPieces, beatenPiecesByBlackAdapter)
        beatenPiecesByBlackViewModel = BeatenPiecesViewModel(application, PieceColor.BLACK)

        // Game
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Observer
        observeViewModels()
    }

    /**
     * Initialize the game with an ai level
     *
     * @return a pair of the two players
     */
    private fun aiGameMode(): Pair<Player?, Player?> {
        intent.extras?.getString(TransferKeys.AI_LEVEL.toString()).let { aiLevelStr ->
            if (aiLevelStr == null) return Pair(null, null)

            enumValueOf<AiLevel>(aiLevelStr).apply {
                return Pair(
                        AiPlayer(this.resourceId, PieceColor.BLACK),
                        AiPlayer(R.string.default_player_name, PieceColor.WHITE)
                )
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
     */
    private fun observeViewModels() {
        gameViewModel.getStatus().observe(this) { status ->
            if (status == GameStatus.CHECKMATE) {
                Log.d(LOG_TAG, "Checkmate")

                // TODO Handle Checkmate
            } else if (status == GameStatus.STALEMATE) {
                Log.d(LOG_TAG, "Stalemate")

                // TODO Handle Stalemate
            }
        }

        gameViewModel.getSelectedPosition().observe(this) { selectedPosition ->
            Log.d(LOG_TAG, "Update selected positions")
            // TODO
        }

        gameViewModel.getPossibleMovesObservable().observe(this) { moves ->
            Log.d(LOG_TAG, "Update possible moves")
            boardAdapter.setPossibleMoves(moves)
        }

        boardViewModel.getFieldObservable().observe(this) { fields ->
            Log.d(LOG_TAG, "Update fields")
            boardAdapter.setFields(fields)
        }

        moveHistoryViewModel.getBoardHistory().observe(this) { moveHistory ->
            Log.d(LOG_TAG, "Update move history")
            moveHistoryAdapter.setMoveHistory(moveHistory)
        }

        observeBeatenPiecesViewModel(beatenPiecesByWhiteAdapter, beatenPiecesByWhiteViewModel)
        observeBeatenPiecesViewModel(beatenPiecesByBlackAdapter, beatenPiecesByBlackViewModel)
    }

    private fun observeBeatenPiecesViewModel(adapter: BeatenPiecesAdapter, viewModel: BeatenPiecesViewModel) {
        viewModel.getBeatenPieces().observe(this) { beatenPieces ->
            Log.d(LOG_TAG, "Update beaten pieces of ${adapter::class.java}")
            adapter.setBeatenPieces(beatenPieces)
        }

        viewModel.getPawnDifferent().observe(this) { pawnDifferent ->
            Log.d(LOG_TAG, "Update pawn different of ${adapter::class.java}")
            // TODO https://github.com/MadMax2506/android-wahlmodul-project/issues/70
        }
    }
}