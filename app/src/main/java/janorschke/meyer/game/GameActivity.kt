package janorschke.meyer.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import janorschke.meyer.R
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.global.TransferKeys
import janorschke.meyer.home.MainActivity

const val LOG_TAG = "GameActivity"

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var gameFieldAdapter: GameFieldAdapter
    private lateinit var boardViewModel: BoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardViewModel = ViewModelProvider(this)[BoardViewModel::class.java]
        gameFieldAdapter = GameFieldAdapter(boardViewModel)
        binding.board?.adapter = gameFieldAdapter
        binding.namePlayer1?.text = getString(R.string.placeholder, "Player 1")

        val gameModeStr = intent.extras?.getString(TransferKeys.GAME_MODE.value)
        if (gameModeStr == null) {
            startActivity(Intent(this, MainActivity::class.java))
            Log.e(LOG_TAG, "Invalid game mode")
        } else {
            val gameMode = GameMode.valueOf(gameModeStr)
            // TODO get game mode specific things, aiLevel ....
            binding.namePlayer2?.text = getString(R.string.placeholder, "Player 2")
        }
    }
}