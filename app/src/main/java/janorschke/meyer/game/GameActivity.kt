package janorschke.meyer.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import janorschke.meyer.ai.AiLevel
import janorschke.meyer.databinding.ActivityGameBinding
import janorschke.meyer.global.TransferKeys
import janorschke.meyer.home.MainActivity

const val LOG_TAG = "GameActivity"

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // board handling
        val boardViewModel = ViewModelProvider(this)[BoardViewModel::class.java]
        val gameFieldAdapter = GameFieldAdapter(applicationContext, boardViewModel)

        binding.board?.adapter = gameFieldAdapter

        // player handling
        // TODO set names
        // TODO set adapters for moves and geschlagene figuren

        val aiLevelString = intent.extras?.getString(TransferKeys.AI_LEVEL.value)
        if (aiLevelString == null) {
            startActivity(Intent(this, MainActivity::class.java))
            Log.e(LOG_TAG, "Invalid ai level")
        } else {
            val aiLevel = AiLevel.valueOf(aiLevelString)
            Log.d(LOG_TAG, "Start game with ai-level=${aiLevel.value}")
            // TODO get game mode specific things, aiLevel, player name ....
        }
    }
}