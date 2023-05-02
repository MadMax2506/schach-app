package janorschke.meyer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import janorschke.meyer.databinding.ActivityAiBinding

class AiActivtity : AppCompatActivity() {
    private lateinit var binding: ActivityAiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAiKevinOtto?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
        binding.buttonAiMax?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
        binding.buttonAiChris?.setOnClickListener {
            // TODO AI-Einstellungen mitnehmen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent, savedInstanceState)
        }
    }
}
