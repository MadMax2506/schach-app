package janorschke.meyer.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        val gameFieldAdapter = GameFieldAdapter(this.applicationContext, boardViewModel)

        binding.board?.adapter = gameFieldAdapter

        // player handling
        // TODO set names
        // TODO set adapters for moves and geschlagene figuren

        val gameModeStr = intent.extras?.getString(TransferKeys.GAME_MODE.value)
        if (gameModeStr == null) {
            startActivity(Intent(this, MainActivity::class.java))
            Log.e(LOG_TAG, "Invalid game mode")
        } else {
            val gameMode = GameMode.valueOf(gameModeStr)
            // TODO get game mode specific things, aiLevel, player name ....
        }
    }
}