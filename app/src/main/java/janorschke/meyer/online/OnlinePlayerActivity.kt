package janorschke.meyer.online

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityOnlineplayerBinding
import janorschke.meyer.game.GameActivity
import janorschke.meyer.game.GameMode
import janorschke.meyer.global.TransferKeys

const val LOG_TAG = "OnlinePlayerActivity"

class OnlinePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineplayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEinladungVersenden?.setOnClickListener {
            // TODO
        }
    }

    // TODO
    private fun startGame() {
        val intent = Intent(this, GameActivity::class.java).apply {
            Log.d(LOG_TAG, "Change activity")
            putExtras(Bundle().apply {
                putString(TransferKeys.GAME_MODE.value, GameMode.ONLINE.value)
                // TODO player or what is needed
            })
        }

        startActivity(intent)
    }
}
