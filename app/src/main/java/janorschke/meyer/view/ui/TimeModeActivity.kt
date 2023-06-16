package janorschke.meyer.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityTimeModeBinding
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.TimeMode
import janorschke.meyer.enums.TransferKeys

private const val LOG_TAG = "TimeModeActivity"

class TimeModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTimeModeBullet?.setOnClickListener { startGame(TimeMode.BULLET) }
        binding.buttonTimeModeBlitz?.setOnClickListener { startGame(TimeMode.BLITZ) }
        binding.buttonTimeModeRapid?.setOnClickListener { startGame(TimeMode.RAPID) }
        binding.buttonTimeModeUnlimited?.setOnClickListener { startGame(TimeMode.UNLIMITED) }
    }

    private fun startGame(timeMode: TimeMode) {
        val aiLevelStr = intent.extras?.getString(TransferKeys.AI_LEVEL.name)
                ?: throw NullPointerException("AI Level is null")

        Intent(this, GameActivity::class.java).let { intent ->
            Log.d(LOG_TAG, "Start new game with the ai-level $aiLevelStr and timemode ${timeMode.name}")

            intent.putExtras(Bundle().also { bundle ->
                bundle.putString(TransferKeys.TIME_MODE.name, timeMode.name)
                bundle.putString(TransferKeys.AI_LEVEL.name, aiLevelStr)
                bundle.putString(TransferKeys.GAME_MODE.name, GameMode.AI.name)
            })

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
        }
    }
}