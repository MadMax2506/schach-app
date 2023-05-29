package janorschke.meyer.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityAiBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.TransferKeys

private const val LOG_TAG = "AiActivity"

class AiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAiKevinOtto?.setOnClickListener { selectTimeMode(AiLevel.KEVIN_OTTO) }
        binding.buttonAiMax?.setOnClickListener { selectTimeMode(AiLevel.MAX) }
        binding.buttonAiChris?.setOnClickListener { selectTimeMode(AiLevel.CHRIS) }
    }

    private fun selectTimeMode(aiLevel: AiLevel) {
        Intent(this, TimeModeActivity::class.java).let { intent ->
            Log.d(LOG_TAG, "Start new game with the ai-level $aiLevel")

            intent.putExtras(Bundle().also { bundle ->
                bundle.putString(TransferKeys.AI_LEVEL.name, aiLevel.name)
                bundle.putString(TransferKeys.GAME_MODE.name, GameMode.AI.name)
            })
            startActivity(intent)
        }
    }
}