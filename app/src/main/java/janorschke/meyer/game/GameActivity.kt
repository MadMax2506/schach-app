package janorschke.meyer.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import janorschke.meyer.R
import janorschke.meyer.ai.AiLevel
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.game.adapter.BoardAdapter
import janorschke.meyer.game.adapter.MoveHistoryAdapter
import janorschke.meyer.game.player.PlayerInfo
import janorschke.meyer.global.TransferKeys
import janorschke.meyer.home.MainActivity

private const val LOG_TAG = "GameActivity"

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        BoardAdapter(applicationContext, gameViewModel).apply { binding.boardWrapper?.board?.adapter = this }
        MoveHistoryAdapter(applicationContext, gameViewModel).apply { binding.moveHistoryWrapper?.moveHistory?.adapter = this }

        // TODO

        // player handling
        val aiLevelString = intent.extras?.getString(TransferKeys.AI_LEVEL.value)
        if (aiLevelString == null) {
            startActivity(Intent(this, MainActivity::class.java))
            Log.e(LOG_TAG, "Invalid ai level")
        } else {
            val aiLevel = enumValueOf<AiLevel>(aiLevelString)
            Log.d(LOG_TAG, "Start game with ai-level=${aiLevel}")

            binding.playerOne?.name?.text = resources.getString(aiLevel.resourceId)
            binding.playerTwo?.name?.text = resources.getString(R.string.default_player_name)
            //Spielerfarbe setzen
            gameViewModel.setPlayerInfo(PlayerInfo.WHITE)
        }
    }
}