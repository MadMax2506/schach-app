package janorschke.meyer.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityAiBinding
import janorschke.meyer.enums.AiLevel
import janorschke.meyer.enums.GameMode
import janorschke.meyer.enums.TransferKeys

private const val LOG_TAG = "AiActivtity"

class AiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAiKevinOtto?.setOnClickListener { startGame(AiLevel.KEVIN_OTTO) }
        binding.buttonAiMax?.setOnClickListener { startGame(AiLevel.MAX) }
        binding.buttonAiChris?.setOnClickListener { startGame(AiLevel.CHRIS) }
    }

    private fun startGame(aiLevel: AiLevel) {
        Intent(this, GameActivity::class.java).apply {
            Log.d(LOG_TAG, "Start new game with the ai-level $aiLevel")

            putExtras(Bundle().apply {
                putString(TransferKeys.AI_LEVEL.toString(), aiLevel.toString())
                putString(TransferKeys.GAME_MODE.toString(), GameMode.AI.toString())
            })

            startActivity(this)
        }
    }
}