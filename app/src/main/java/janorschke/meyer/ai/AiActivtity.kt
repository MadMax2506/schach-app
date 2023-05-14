package janorschke.meyer.ai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityAiBinding
import janorschke.meyer.game.GameActivity
import janorschke.meyer.global.TransferKeys

private const val LOG_TAG = "AiActivtity"

class AiActivtity : AppCompatActivity() {
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
            Log.d(LOG_TAG, "Change activity")
            this.putExtras(Bundle().apply {
                this.putString(TransferKeys.AI_LEVEL.value, aiLevel.name)
            })
            startActivity(this)
        }
    }
}
